/* E3 app.js */
'use strict';

const log = console.log
const yargs = require('yargs').option('addRest', {
    type: 'array' // Allows you to have an array of arguments for particular command
  }).option('addResv', {
    type: 'array' 
  }).option('addDelay', {
    type: 'array' 
  })

const reservations = require('./reservations');

// datetime available if needed
const datetime = require('date-and-time') 

const yargs_argv = yargs.argv
//log(yargs_argv) // uncomment to see what is in the argument array

if ('addRest' in yargs_argv) {
	const args = yargs_argv['addRest']
	const rest = reservations.addRestaurant(args[0], args[1]);	
	if (rest.length > 0) {
		/* complete */
        log("Added restaurant " + rest[0].name + ".")
	} else {
		/* complete */ 
		log("Duplicate restaurant not added.");
	}
}

if ('addResv' in yargs_argv) {
	const args = yargs_argv['addResv']
	const resv = reservations.addReservation(args[0], args[1], args[2]);

	// Produce output below
    if (resv !== undefined) {
        let date = new Date(resv.time)
        log("Added reservation at " + resv.restaurant +
            datetime.format(date, " on MMMM D Y at h:mm A", true) + " for " + resv.people + " people.")
    }
}

if ('allRest' in yargs_argv) {
	const restaurants = reservations.getAllRestaurants(); // get the array
	
	// Produce output below
    restaurants.forEach((rest) => {
        log(rest.name + ": " + rest.description + " - " + rest.numReservations + " active reservations")
    });
}

if ('restInfo' in yargs_argv) {
	const restaurants = reservations.getRestaurantByName(yargs_argv['restInfo']);

	// Produce output below
    if (restaurants !== undefined) {
        log(restaurants.name + ": " + restaurants.description + " - " + restaurants.numReservations + " active reservations")
    }
}

if ('allResv' in yargs_argv) {
	const restaurantName = yargs_argv['allResv']
	const reservationsForRestaurant = reservations.getAllReservationsForRestaurant(restaurantName);
	// Produce output below
    if (reservationsForRestaurant.length > 0) {
        // Produce output below
        log("Reservations for " + restaurantName + ":")
        reservationsForRestaurant.forEach((resv) => {
            let date = new Date(resv.time)
            resv.time = datetime.format(date, "- MMM. D Y, h:mm A,", true)
            log(resv.time + " table for " + resv.people)
        })
    }
}

if ('hourResv' in yargs_argv) {
	const time = yargs_argv['hourResv']
	const reservationsForRestaurant = reservations.getReservationsForHour(time);
	
	// Produce output below
    if (reservationsForRestaurant.length > 0) {
        log("Reservations in the next hour:")
        reservationsForRestaurant.forEach((resv) => {
            let date = new Date(resv.time)
            resv.time = datetime.format(date, "MMM. D Y, h:mm A,", true)
            log("- " + resv.restaurant + ": " + resv.time + " table for " + resv.people)
        })
    }
}

if ('checkOff' in yargs_argv) {
	const restaurantName = yargs_argv['checkOff']
	const earliestReservation = reservations.checkOffEarliestReservation(restaurantName); 
	
	// Produce output below
    if (earliestReservation !== null) {
        let date = new Date(earliestReservation.time)
        earliestReservation.time = datetime.format(date, "on MMM. D Y, h:mm A,", true)
        log("Checked off reservation: " + earliestReservation.time + " table for " + earliestReservation.people)
    }
}

if ('addDelay' in yargs_argv) {
	const args = yargs_argv['addDelay']
	const resv = reservations.addDelayToReservations(args[0], args[1]);	

	// Produce output below
    if (resv !== null) {
        log("Reservations for " + args[0] + ":")
        resv.forEach((reservation) => {
            if (reservation.restaurant === args[0]) {
                let date = new Date(reservation.time)
                reservation.time = datetime(date, "- MMM. D Y, h:mm A,", true)
                log(reservation.time + " table for " + reservation.people)
            }
        })
    }
	
}

if ('status' in yargs_argv) {
	const status = reservations.getSystemStatus()

	// Produce output below
    log("Number of restaurants: " + status.numRestaurants)
    log("Number of total reservations: " + status.totalReservations)
    log("Busiest restaurant: " + status.currentBusiestRestaurantName)
    let date = new Date(status.systemStartTime)
    log("System started at: " + datetime.format(date, "MMM D, Y, h:mm A", true))
}

