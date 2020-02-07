'use strict';
const log=console.log
log('students.js - march 4 - 10 am')

//use file system
const fs = require('fs');
const addStudent = function (name,year,courses) {
    let students=[]
    try{
        const studentsFromFile = fs.readFileSync('students.json')
        students=JSON.parse(studentsFromFile)
    }catch(e){

    }

    const student={
        id:students.length+1,
        name:name,
        year:year,
        courses:courses
    }
    students.push(student)
    fs.writeFileSync('students.json',JSON.stringify(students))

}

module.exports ={
    addStudent,
}