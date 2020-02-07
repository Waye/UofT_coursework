/* server.js - mar11 - 6pm - mongoose*/
'use strict'
const log = console.log

const express = require('express')
const port = process.env.PORT || 3000
const bodyParser = require('body-parser')
const { ObjectID } = require('mongodb')

const { mongoose } = require('./db/mongoose')

// import the model
const { Student } = require('./models/student')
const { User } = require('./models/user')

// express
const app = express();
// body-parser middleware - will parse the JSON and convert to object
app.use(bodyParser.json())


/// User routes below
app.post('/users', (req, res) => {

	const user = new User({
		email: req.body.email,
		password: req.body.password
	})

	// save user to the database
	user.save().then((user) => {
		res.send(user)
	}, (error) => {
		res.status(400).send(error) // 400 for bad request
	})


})





// setup a POST route to create a student
app.post('/students', (req, res) => {
	log(req.body)

	const student = new Student({
		name: req.body.name,
		year: req.body.year
	})

	// save student to the database
	student.save().then((result) => {
		// send as a response to the client
		// the object that was saved
		res.send(result)
	}, (error) => {
		res.status(400).send(error) // 400 for bad request
	})


})

app.get('/students/:id', (req, res) => {
	// parameters from the url
	//log(req.params)
	const id = req.params.id

	// Good practise: Validate the id
	if (!ObjectID.isValid(id)) {
		res.status(404).send()
	}

	// Otherwise, findById
	Student.findById(id).then((student) => {
		if (!student) {
			res.status(404).send()
		} else {
			/// sometimes will wrap object in object
			/// to specify what the object is
			//res.send({ student })
			res.send(student)

		}
	}).catch((error) => {
		res.status(500).send()
	})
})


app.delete('/students/:id', (req, res) => {
	const id = req.params.id

	// Good practise: Validate the id
	if (!ObjectID.isValid(id)) {
		res.status(404).send()
	}

	Student.findByIdAndRemove(id).then((student) => {
		if (!student) {
			res.status(404).send()
		} else {
			res.send(student)
		}
	}).catch((error) => {
		res.status(500).send()
	})
})

// PATCH for changing certain properties of elements
// PUT which is more for replacing entire elements
app.patch('/students/:id', (req, res) => {
	const id = req.params.id

	// grab the properties we want to change
	const { name, year } = req.body
	const body = { name, year }

	// Good practise: Validate the id
	if (!ObjectID.isValid(id)) {
		res.status(404).send()
	}

	//patch it
	Student.findByIdAndUpdate(id, {$set: body}, {new: true}).then((student) => {
		if (!student) {
			res.status(404).send()
		} else {
			res.send(student)
		}
	}).catch((error) => {
		res.status(500).send()
	})
})



app.listen(port, () => {
	log(`Listening on port ${port}...`)
}) 