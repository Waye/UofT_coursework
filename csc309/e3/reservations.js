/* Reservations.js */ 
'use strict';

const log = console.log
const fs = require('fs');
const datetime = require('date-and-time')

const startSystem = () => {

	let status = {};

	try {
		status = getSystemStatus();
	} catch(e) {
		status = {
			numRestaurants: 0,
			totalReservations: 0,
			currentBusiestRestaurantName: null,
			systemStartTime: new Date(),
		}

		fs.writeFileSync('status.json', JSON.stringify(status))
	}

	return status;
}

/*********/


// You may edit getSystemStatus below.  You will need to call updateSystemStatus() here, which will write to the json file
const getSystemStatus = () => {
    updateSystemStatus()
	const status = fs.readFileSync('status.json')

	return JSON.parse(status)
}

/* Helper functions to save JSON */
const updateSystemStatus = () => {

	
	/* Add your code below */
    const allRestaurants = getAllRestaurants()
    const allReservations = getAllReservations()
	let result = null
    let numberReservations = 0
    allRestaurants.forEach((rest) => {
        if (rest.numReservations > numberReservations) {
            numberReservations = rest.numReservations
            result = rest.name
        }
    })

    let status = JSON.parse(fs.readFileSync('status.json'))
    status.currentBusiestRestaurantName = result
    status.numRestaurants = allRestaurants.length
    status.totalReservations = allReservations.length


    fs.writeFileSync('status.json', JSON.stringify(status))
}

const saveRestaurantsToJSONFile = (restaurants) => {
	/* Add your code below */
    fs.writeFileSync('restaurants.json', JSON.stringify(restaurants))
};

const saveReservationsToJSONFile = (reservations) => {
	/* Add your code below */
    fs.writeFileSync('reservations.json', JSON.stringify(reservations))
};

/*********/

// Should return an array of length 0 or 1.
const addRestaurant = (name, description) => {
	// Check for duplicate names
    const existedRestaurant = getRestaurantByName(name)
    if (!(existedRestaurant === undefined)) {
        return [];
    } else {
        // if no duplicate names:
        const allRestaurants = getAllRestaurants()
        const restaurant = {
            name: name,
            description: description,
            numReservations: 0,
        }
        allRestaurants.push(restaurant)
        saveRestaurantsToJSONFile(allRestaurants)
        return [restaurant];
    }


}

// should return the added reservation object
const addReservation = (restaurant, time, people) => {

    /* Add your code below */
    let reservation;
    //exist this restaurant
    if ( getRestaurantByName(restaurant)!==undefined) {
        const allReservations = getAllReservations()
        const newReservation = {
            restaurant: restaurant,
            time: new Date(datetime.parse(time, 'MMM DD YYYY hh:mm:ss', true)),
            people: people,
        }
        allReservations.push(newReservation)

        const allRestaurants = getAllRestaurants()
		allRestaurants.forEach((rest) => {
                if (rest.name === restaurant) {
                    rest.numReservations++
                }
            });

        saveReservationsToJSONFile(allReservations)
        saveRestaurantsToJSONFile(allRestaurants)
        reservation = newReservation

}
    return reservation

}


/// Getters - use functional array methods when possible! ///

// Should return an array - check to make sure restaurants.json exists
const getAllRestaurants = () => {
	/* Add your code below */
    try {
        const allRestaurantFromFile = fs.readFileSync('restaurants.json')
        return JSON.parse(allRestaurantFromFile)
    } catch (e) {
        return []
    }

};

// Should return the restaurant object if found, or an empty object if the restaurant is not found.
const getRestaurantByName = (name) => {
	/* Add your code below */
    const restaurants = getAllRestaurants()
    const restaurant= restaurants.filter((restaurant) => restaurant.name === name)
    return restaurant[0]

};

// Should return an array - check to make sure reservations.json exists
const getAllReservations = () => {
  /* Add your code below */
    try {
        const allReservationFromFile = fs.readFileSync('reservations.json')
        return JSON.parse(allReservationFromFile)
    } catch (e) {
        return []
    }


};

// Should return an array
const getAllReservationsForRestaurant = (name) => {
	/* Add your code below */
    const reservations = getAllReservations()
    let reservation= reservations.filter((reservation) => reservation.restaurant=== name)

    reservation.sort((a, b) => {
        let dateA = new Date(a.time)
        let dateB = new Date(b.time)
        return dateA - dateB
    })

    return reservation;


};


// Should return an array
const getReservationsForHour = (time) => {
	/* Add your code below */
    const reservations = getAllReservations()
    const lowerTime = new Date(datetime.parse(time, 'MMM DD YYYY hh:mm:ss', true))
    const upperTime = new Date(datetime.parse(time, 'MMM DD YYYY hh:mm:ss', true)).getHours()+1
    const reservation= reservations.filter((reservation) => lowerTime<=reservation.time<=upperTime)
    return reservation.sort((a, b) => {
        let dateA = new Date(a.time)
        let dateB = new Date(b.time)
        return dateA - dateB
    })

}

// should return a reservation object
const checkOffEarliestReservation = (restaurantName) => {

    let checkedOffReservation = null;
	let allReservationsOfRest = getAllReservationsForRestaurant(restaurantName)
	let allRestaurants = getAllRestaurants()
	let allReservations = getAllReservations()

    
	if (allReservationsOfRest.length > 0) {
            allReservationsOfRest.sort((a, b) => {
                let dateA = new Date(a.time)
                let dateB = new Date(b.time)
                return dateA - dateB
            })
            checkedOffReservation = allReservationsOfRest[0]


            const newAllReservations = allReservations.filter((reser) => {
                return JSON.stringify(reser) !== JSON.stringify(checkedOffReservation)
            })

            allRestaurants.forEach((rest) => {
                if (rest.name === restaurantName) {
                    rest.numReservations--
                }
            })
            saveReservationsToJSONFile(newAllReservations)
            saveRestaurantsToJSONFile(allRestaurants)
        }

    return checkedOffReservation
}


const addDelayToReservations = (restaurant, minutes) => {
	// Hint: try to use a functional array method
    let delayedResrvations = null
	if (getRestaurantByName(restaurant)!==undefined) {

            const allReservations = getAllReservations()
            const newAllReservations = allReservations.map((reser) => {
                if (reser.restaurant === restaurant) {
                    const time = new Date(reser.time)
                    time.setMinutes(time.getMinutes() + minutes)
                    reser.time = time
                }
                return reser
            })
        delayedResrvations = newAllReservations

        saveReservationsToJSONFile(delayedResrvations)
        //sort reservation array by date
        delayedResrvations.sort((a, b) => {
            let dateA = new Date(a.time)
            let dateB = new Date(b.time)
            return dateA - dateB
        })
    }
    return delayedResrvations
}

startSystem(); // start the system to create status.json (should not be called in app.js)

// May not need all of these in app.js..but they're here.
module.exports = {
	addRestaurant,
	getSystemStatus,
	getRestaurantByName,
	getAllRestaurants,
	getAllReservations,
	getAllReservationsForRestaurant,
	addReservation,
	checkOffEarliestReservation,
	getReservationsForHour,
	addDelayToReservations
}

