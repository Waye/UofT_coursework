/* E4 server.js */
'use strict';
const log = console.log;

const express = require('express')
const bodyParser = require('body-parser')
const { ObjectID } = require('mongodb')

// Mongoose
const { mongoose } = require('./db/mongoose');
const { Restaurant } = require('./models/restaurant')

// Express
const port = process.env.PORT || 3000
const app = express();
app.use(bodyParser.json());

/// Route for adding restaurant, with *no* reservations (an empty array).
/* 
Request body expects:
{
	"name": <restaurant name>
	"description": <restaurant description>
}
Returned JSON should be the database document added.
*/
// POST /restaurants
app.post('/restaurants', (req, res) => {
	// Add code here
    const restaurant = new Restaurant({
        name: req.body.name,
        description: req.body.description,
        reservations: []
    });
    // save restaurant to database
    restaurant.save().then(
        result => {
            // Save and send object that was save
            res.send({ result });
        },
        error => {
            res.status(400).send(error); // 400 for bad request
        }
    );
})


/// Route for getting all restaurant information.
// GET /restaurants
app.get('/restaurants', (req, res) => {
	// Add code here
        Restaurant.find().then(
            restaurant => {
                if (!restaurant) {
                    res.status(404).send()
                } else {
                    res.send(restaurant)
                }
            },
            error => {
                res.status(500).send(error);
            }
        );
})


/// Route for getting information for one restaurant.
// GET /restaurants/id
app.get('/restaurants/:id', (req, res) => {
	// Add code here
    const objectId = req.params.id;
    if (ObjectID.isValid(objectId)) {
        Restaurant.findById(objectId).then(restaurant => {
            if (!restaurant) {
                res.status(404).send();
            } else {
                res.send({ restaurant });
            }
        }).catch(error => {
            res.status(500).send()
        })
    } else {
        return res.status(404).send();
    }
})


/// Route for adding reservation to a particular restaurant.
/* 
Request body expects:
{
	"time": <time>
	"people": <number of people>
}
*/
// Returned JSON should have the restaurant database 
//   document that the reservation was added to, AND the reservation subdocument:
//   { "reservation": <reservation subdocument>, "restaurant": <entire restaurant document>}
// POST /restaurants/id
app.post('/restaurants/:id', (req, res) => {
	// Add code here
    const objectId = req.params.id;
    const reservation = {
        time: req.body.time,
        people: req.body.people
    };
    if (!ObjectID.isValid(objectId)) {
        return res.status(404).send();
    }
    //mongoose.set("useFindAndModify", false);
    Restaurant.findById(objectId).then(restaurant => {
        if (!restaurant) {
            return res.status(404).send();
        } else {
            restaurant.reservations.push({
                reservation
            })
        }
        restaurant.save().then((result) => {
            res.send(result)
        }, (error) => {
            res.status(400).send(error)
        }).catch((error) => {
        res.status(500).send()
    })

})
})


/// Route for getting information for one reservation of a restaurant (subdocument)
// GET /restaurants/id
app.get('/restaurants/:id/:resv_id', (req, res) => {
	// Add code here
    const restaurantId = req.params.id;
    const reservationId = req.params.resv_id;
    if (!ObjectID.isValid(restaurantId) || !ObjectID.isValid(reservationId)) {
        return res.status(404).send();
    } else {
        Restaurant.findById(restaurantId)
            .then(restaurant => {
                if (!restaurant) {
                    res.status(404).send();
                } else {
                    if (restaurant.reservations.id(reservationId) !== null) {
                        const reservation = restaurant.reservations.id(reservationId);
                        res.send({ reservation });
                    } else {
                        res.status(404).send();
                    }
                }
            })
            .catch(error => {
                res.status(500).send(error);
            });
    }
})


/// Route for deleting reservation
// Returned JSON should have the restaurant database
//   document from which the reservation was deleted, AND the reservation subdocument deleted:
//   { "reservation": <reservation subdocument>, "restaurant": <entire restaurant document>}
// DELETE restaurant/<restaurant_id>/<reservation_id>
app.delete('/restaurants/:id/:resv_id', (req, res) => {
	// Add code here
    const restaurantId = req.params.id;
    const reservationId = req.params.resv_id;
    if (!ObjectID.isValid(restaurantId) || !ObjectID.isValid(reservationId)) {
        return res.status(404).send();
    } else {
        Restaurant.findById(restaurantId).then((restaurant) => {
            if (!restaurant) {
                res.status(404).send()
            } else {
                restaurant.reservations.pull(reservationId );
            }

            restaurant.save().then((result) => {
                res.send(result)
            }, (error) => {
                res.status(400).send(error)
            })


        }).catch((error) => {
            res.status(500).send()
        })
    }
})


/// Route for changing reservation information
/* 
Request body expects:
{
	"time": <time>
	"people": <number of people>
}
*/
// Returned JSON should have the restaurant database
//   document in which the reservation was changed, AND the reservation subdocument changed:
//   { "reservation": <reservation subdocument>, "restaurant": <entire restaurant document>}
// PATCH restaurant/<restaurant_id>/<reservation_id>
app.patch('/restaurants/:id/:resv_id', (req, res) => {
	// Add code here
    const id = req.params.id;
    const resv_id = req.params.resv_id;
    if (!ObjectID.isValid(id) || !ObjectID.isValid(resv_id)) {
        res.status(404).send()
    }
    Restaurant.findById(id).then((restaurant) => {
        if (!restaurant) {
            res.status(404).send()
        } else {
            restaurant.reservations.id(resv_id).time = req.body.time;
            restaurant.reservations.id(resv_id).people = req.body.people;
        }

        restaurant.save().then((result) => {
            res.send(result)
        }, (error) => {
            res.status(400).send(error)
        })
    })
})


//////////

app.listen(port, () => {
	log(`Listening on port ${port}...`)
})
