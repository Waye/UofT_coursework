// // // /* Jan 21 js - 6pm */
// // // "use strict";
// // //
// // //
// // // // We can declare and define a variable
// // // var a = 3;
// // // //console.log(a);
// // //
// // // //console.log(b);
// // //
// // // //console.log(c);
// // // var c = 12;
// // //
// // // //console.log( )
// // //
// // // function f1() {
// // //     var a = 4;
// // //     //console.log(a);
// // // }
// // //
// // // f1();
// // //
// // // var g = 5;
// // //
// // // f2(); // called before definition
// // // function f2() {
// // //     //var g = 4;
// // //    // console.log(g);
// // // }
// // //
// // // function f3() {
// // //     var r = 4;
// // //     //console.log(r)
// // // }
// // //
// // // //f3();
// // // //console.log('Outside f3', r) //error
// // // /*
// // // function f4() {
// // // 	k = 37;
// // // 	console.log(k)
// // // }
// // //
// // // f4();
// // // console.log('Outside f3', k)
// // //
// // //
// // // function f5() {
// // // 	console.log('in f5', k)
// // // }
// // //
// // // f5();
// // // */
// // //
// // //
// // // // For loops with var
// // //
// // // var i = 8;
// // // function forFunc() {
// // //     for (var i = 0; i < 5; i++) {
// // //         console.log(i)
// // //     }
// // //    // console.log(i);
// // // }
// // //
// // // //forFunc();
// // // //console.log(i);
// // //
// // // // For loops with let
// // // var j = 10;
// // // function forFuncWithLet() {
// // //     for (let j = 0; j < 5; j++) {
// // //         console.log(j)
// // //     }
// // //     //console.log(j);
// // // }
// // //
// // // //forFuncWithLet();
// // // //console.log(i);
// // //
// // //
// // //
// // // /* Jan 21 part 2 js - 6pm */
// // // "use strict";
// // // const log = console.log;
// // // //log('jan 21 part 2');
// // //
// // // function createCounter() {
// // //     let count = 0;
// // //     return function () {
// // //         count += 1;
// // //         console.log (count);
// // //     }
// // // }
// // //
// // // const myCounter = createCounter();
// // // //myCounter();
// // //
// // // // another way to define createCounter:
// // // const createCounter2 = function () {
// // //     let count = 0;
// // //     return function () {
// // //         count += 1;
// // //         return count;
// // //     }
// // // }
// // //
// // //
// // // // setTimeout closure
// // // // setTimeout(f, interval)
// // //
// // // // let's start with var
// // // // for (var i = 1; i <= 5; i++) {
// // // // 	setTimeout(function () {
// // // // 		log(i);
// // // // 	}, i * 1000);
// // // // }
// // //
// // // // for (var i = 1; i <= 5; i++) {
// // // // 	(function () {
// // // // 		var j = i; // j is function scoped in the anon function
// // // // 		setTimeout(function () {
// // // // 			log(j);
// // // // 		}, i * 1000);
// // // // 	})();
// // // // }
// // //
// // //
// // // // let creates a variable with
// // // // a new (block) scope within each iteration
// // // // for (let i = 1; i <= 5; i++) {
// // // // 	setTimeout(function () {
// // // // 		log(i);
// // // // 	}, i * 1000);
// // // // }
// // //
// // //
// // //
// // // //// Objects
// // //
// // // const student = {
// // //     name: 'Jimmy',
// // //     year: 2,
// // //     sayName: function() {
// // //         log('My name is ' + this.name + '.')
// // //         // Q: what is the context of this?
// // //         // A: we don't know, until we call it
// // //     }
// // // }
// // //
// // // //student.sayName();
// // // //let mySayName = student.sayName();
// // // //mySayName; // undefined
// // //
// // // // we can get this to work without having
// // // // to explicitly call student.sayName()
// // // // Binding
// // // //mySayName = student.sayName;
// // // //const boundSayName = mySayName.bind(student)
// // // //boundSayName();
// // //
// // // // const whatYearAmI = function() {
// // // //     log(this.year)
// // // // }
// // //
// // // // const student2 = {
// // // //     name: 'Saul',
// // // //     year: 3,
// // // //     myYear: whatYearAmI,
// // // //     nested: {
// // // //         name: 'Jane',
// // // //         year: 7,
// // // //         //myYear: whatYearAmI
// // // //     }
// // // // }
// // //
// // // //student2.myYear();
// // //
// // // // const student3 = {
// // // //     name: 'Jane',
// // // //     year: 7
// // // //     myYear: student2.myYear
// // // // }
// // //
// // // //student3.myYear()
// // // //student3.myYear.bind(student2)();
// // // //student3.myYear.call(student2)
// // //
// // // //student2.nested.myYear();
// // //
// // // // var sayHello = function (name) {
// // // //     var text = 'Hello, ' + name;
// // // //     return function () {
// // // //         console.log(text);
// // // //     };
// // // // };
// // // // //sayHello('Todd')();
// // // //
// // // // var b = 1;
// // // // var outer=function (){
// // // //     var b = 2
// // // //     function inner(){
// // // //         b++;
// // // //         var b = 3;
// // // //         console.log(b)
// // // //     }
// // // //     inner();
// // // // }
// // // // //outer();
// // //
// // //
// // // (function(a) {
// // //     return (function (b) {
// // //         console.log(a);
// // //     })(2)
// // // })(1);
// //
// //
// // /* Lab 3 JS */
// // 'use strict';
// //
// // // We are a Toyota factory
// // const toyota = {
// //     brand: 'Toyota',
// //     color: 'red',
// //     type: 'sedan',
// //     seats: 5,
// // };
// //
// // // We will now make a car object that will act as a prototype
// //
// // const car = {
// //     type: 'car', // a default car type
// //     describe: function() {
// //         console.log(`This is a ${this.color} ${this.brand} ${this.type} that has ${this.seats} seats.`);
// //     }
// // }
// //
// // Object.setPrototypeOf(toyota, car)
// // toyota.describe()
// //
// // console.log(toyota.__proto__ === car) // true
// //
// // // 'constructor'
// // function Toyota(color, type, seats) {
// //     this.brand = 'Toyota'
// //     this.color = color
// //     this.type = type
// //     this.seats = seats
// // }
// //
// // // We are setting the prototype of the constructor function
// // Toyota.prototype = car
// //
// // const myToyota = new Toyota('black', 'SUV', 7)
// // myToyota.describe()
// //
// // const mySecondToyota = new Toyota('lime green', 'convertible', 2)
// // mySecondToyota.describe()
// //
// // // Add to the prototype a function for car
// // car.seatsFive = function() {
// //     return this.seats >= 5
// // }
// //
// // console.log(myToyota.seatsFive())
// // console.log(mySecondToyota.seatsFive())
// //
// // myToyota.__proto__.describe()
// // Toyota.prototype.describe()
// // Toyota.prototype.describe.bind(mySecondToyota)()
// //
// // // Attach a delegate prototype using Object.create
// // const myThirdToyota = Object.create(car) // add the car prototype to the object
// // Toyota.bind(myThirdToyota)('red', 'minivan', 7)
// // myThirdToyota.describe()
// //
// // class CarClass {}
// // console.log(typeof(CarClass)) // it's a function - the constructor
// // console.dir(CarClass)
// //
// // class Auto {
// //     constructor() {
// //         this.type = 'car'
// //     }
// //
// //     describe() {
// //         console.log(`This is a ${this.color} ${this.brand} ${this.type} that has ${this.seats} seats.`);
// //     }
// // }
// //
// // console.dir(Auto)
// //
// // class Tesla extends Auto {
// //     constructor(color, type, seats) {
// //         super()
// //         this.brand = 'Tesla'
// //         this.color = color
// //         this.type = type
// //         this.seats = seats
// //     }
// // }
// //
// // console.dir(Tesla) // observe the prototype
// // const myTesla = new Tesla('grey', 'crossover', 5)
// // myTesla.describe()
//
//
// /* Jan 28 js - 6pm*/
// "use strict";
// const log = console.log;
// log('jan 28 - 6pm');
//
// function sayName() {
//     log('My name is ' + this.firstName)
// }
//
// //sayName(); // undefined
//
// const person = {
//     sayName: sayName
// }
//
// person.sayName();
//
// const student = {
//     firstName: 'James'
// }
//
// //student.sayName();
//
// Object.setPrototypeOf(student, person)
// student.sayName();
//
// const teacher = {
//     firstName: 'Paul'
// }
//
// Object.setPrototypeOf(teacher, person)
// teacher.sayName()
//
// // chaining prototypes
// const partTimeStudent = {
//     numCourses: 2
// }
//
// Object.setPrototypeOf(partTimeStudent, student)
// partTimeStudent.sayName()
//
// person.sayName = function () {
//     log('MY NAME IS ' + this.firstName)
// }
//
// partTimeStudent.sayName()
//
// //////////////////
//
// // a constructor function
// function Student(firstName, lastName) {
//     this.firstName = firstName
//     this.lastName = lastName
// }
//
// Student.prototype.sayLastName = function () {
//     log('My last name is ' + this.lastName)
// }
//
// const student2 = new Student('Jimmy', 'Parker')
// student2.sayLastName()
//
// // Object.create()
// const student3 = Object.create(student)
// student3.sayName()
//
// // class keyword
//
// class Instructor {
//     constructor(firstName, course) {
//         this.firstName = firstName
//         this.course = course
//     }
//
//     whatsMyCourse() {
//         return this.course;
//     }
// }
//
// /// Equivalent to above class
// /*
// function Instructor(firstName, course) {
// 	this.firstName = firstName
// 	this.course = course
// }
//
// Instructor.prototype.whatsMyCourse = function () {
// 	return this.course
// }
// */
//
// const jen = new Instructor('Jen', 'CSC108')
// log(jen.whatsMyCourse())
//
// class Person {
//     constructor(firstName) {
//         this.firstName = firstName
//     }
// }
//
// /// More Object-oriented syntactic sugar below.
// // Under the hood, this is still prototypal delegation
// class Instructor2 extends Person {
//     constructor(firstName, course) {
//         super(firstName)
//         this.course = course
//     }
//
//     whatsMyCourse() {
//         return this.course;
//     }
// }
//
// const jen2 = new Instructor2('Jen2', 'CSC108')
// log(jen2.whatsMyCourse())
//
//
//

//  function sayHello(name) {
//     var text = 'Hello, ' + name;
//     console.log('hi');
//     return function () {
//         console.log(text);
//     };
// };
//
// sayHello('Todd');
// sayHello('Todd')();

var a =1
function c(){

   if (true){
       console.log(a);
   }

}
c();