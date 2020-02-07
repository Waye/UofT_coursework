'use strict'
let a = 4;
let b = 1;

//1.
// (function () {
//     if (a > 2) {
//         let b = 3;
//         a = 0;
//     }
//     console.log(a + b);
// })();
//output:1

// const c = 0;
// for (let i = 0; i < a; i++) {
//     if (i > 2) { b = 0; }
//     c = c + b;
//     console.log(c);
// }
//output:error, since constant variable cannot assign value

// function foo() {
//     function bar() {
//         return function () { console.log(a); }
//     }
//     a = 3;
//     return bar();
// }
// const baz = foo();baz();
//output:4

// function f1() {
//     const n = b;
//     return function (p) {
//         console.log(p);
//         return n + p; }
// }
// const f2 = f1();
// console.log(f2(a));
//output:5
