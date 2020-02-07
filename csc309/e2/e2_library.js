/* E2 Library - JS */

/*-----------------------------------------------------------*/
/* Starter code - DO NOT edit the code below. */
/*-----------------------------------------------------------*/

// global counts
let numberOfBooks = 0; // total number of books
let numberOfPatrons = 0; // total number of patrons

// global arrays
const libraryBooks = [] // Array of books owned by the library (whether they are loaned or not)
const patrons = [] // Array of library patrons.

// Book 'class'
class Book {
	constructor(title, author, genre) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.patron = null; // will be the patron objet

		// set book ID
		this.bookId = numberOfBooks;
		numberOfBooks++;
	}

	setLoanTime() {
		// Create a setTimeout that waits 3 seconds before indicating a book is overdue

		const self = this; // keep book in scope of anon function (why? the call-site for 'this' in the anon function is the DOM window)
		setTimeout(function() {
			
			console.log('overdue book!', self.title)
			changeToOverdue(self);

		}, 3000)

	}
}

// Patron constructor
const Patron = function(name) {
	this.name = name;
	this.cardNumber = numberOfPatrons;

	numberOfPatrons++;
}


// Adding these books does not change the DOM - we are simply setting up the 
// book and patron arrays as they appear initially in the DOM.
libraryBooks.push(new Book('Harry Potter', 'J.K. Rowling', 'Fantasy'));
libraryBooks.push(new Book('1984', 'G. Orwell', 'Dystopian Fiction'));
libraryBooks.push(new Book('A Brief History of Time', 'S. Hawking', 'Cosmology'));

patrons.push(new Patron('Jim John'))
patrons.push(new Patron('Kelly Jones'))

// Patron 0 loans book 0
libraryBooks[0].patron = patrons[0]
// Set the overdue timeout
libraryBooks[0].setLoanTime()  // check console to see a log after 3 seconds


/* Select all DOM form elements you'll need. */ 
const bookAddForm = document.querySelector('#bookAddForm');
const bookInfoForm = document.querySelector('#bookInfoForm');
const bookLoanForm = document.querySelector('#bookLoanForm');
const patronAddForm = document.querySelector('#patronAddForm');

/* bookTable element */
const bookTable = document.querySelector('#bookTable')
/* bookInfo element */
const bookInfo = document.querySelector('#bookInfo')
/* Full patrons entries element */
const patronEntries = document.querySelector('#patrons')

/* Event listeners for button submit and button click */

bookAddForm.addEventListener('submit', addNewBookToBookList);
bookLoanForm.addEventListener('submit', loanBookToPatron);
patronAddForm.addEventListener('submit', addNewPatron)
bookInfoForm.addEventListener('submit', getBookInfo);

/* Listen for click patron entries - will have to check if it is a return button in returnBookToLibrary */
patronEntries.addEventListener('click', returnBookToLibrary)

/*-----------------------------------------------------------*/
/* End of starter code - do *not* edit the code above. */
/*-----------------------------------------------------------*/
const log = console.log;

/** ADD your code to the functions below. DO NOT change the function signatures. **/


/*** Functions that don't edit DOM themselves, but can call DOM functions 
     Use the book and patron arrays appropriately in these functions.
 ***/

// Adds a new book to the global book list and calls addBookToLibraryTable()
function addNewBookToBookList(e) {
	e.preventDefault();

	// Add book book to global array
		const bookname = document.querySelector('#newBookName').value;
		const bookauthor = document.querySelector('#newBookAuthor').value;
		const bookgenre = document.querySelector('#newBookGenre').value;
		let b = new Book(bookname, bookauthor, bookgenre);
		libraryBooks.push(b);
	// Call addBookToLibraryTable properly to add book to the DOM
		addBookToLibraryTable(b);

	
}

// Changes book patron information, and calls 
function loanBookToPatron(e) {
	e.preventDefault();

	// Get correct book and patron
	const bookid = parseInt(document.querySelector('#loanBookId').value);
	const cardnum = parseInt(document.querySelector('#loanCardNum').value);

	// Add patron to the book's patron property
	libraryBooks[bookid].patron=patrons[cardnum];


	// Add book to the patron's book table in the DOM by calling addBookToPatronLoans()
	addBookToPatronLoans(libraryBooks[bookid]);
	// Start the book loan timer.
	libraryBooks[bookid].setLoanTime();



}

// Changes book patron information and calls returnBookToLibraryTable()
function returnBookToLibrary(e){
	e.preventDefault();

	console.log(e.type);
	// check if return button was clicked, otherwise do nothing.
	if(e.target.classList.contains("return")){

		// Call removeBookFromPatronTable()

		if(e.target.parentElement.parentElement.children[2].innerText === "Overdue"){
				const bookid=parseInt(e.target.parentElement.parentElement.children[0].innerText);
				removeBookFromPatronTable(libraryBooks[bookid]);
			// Change the book object to have a patron of 'null'
			libraryBooks[bookid].patron=null;
		}
	}






}

// Creates and adds a new patron
function addNewPatron(e) {
	e.preventDefault();

	// Add a new patron to global array
		const name=document.querySelector('#newPatronName').value;
		let p = new Patron(name)
		patrons.push(p);

	// Call addNewPatronEntry() to add patron to the DOM
		addNewPatronEntry(p)
}

// Gets book info and then displays
function getBookInfo(e) {
	e.preventDefault();

	// Get correct book
	const bookid=document.querySelector('#bookInfoId').value;
	// Call displayBookInfo()	
	displayBookInfo(libraryBooks[bookid]);
}


/*-----------------------------------------------------------*/
/*** DOM functions below - use these to create and edit DOM objects ***/

// Adds a book to the library table.
function addBookToLibraryTable(book) {
	// Add code here
	const rowelement  = document.createElement('tr');
	const bookidelement = document.createElement('td');
	const titleelement=document.createElement('td');
	const patronelement= document.createElement('td');

	bookidelement.appendChild(document.createTextNode((book.bookId).toString()));

	const strongText = document.createElement("strong");
	strongText.innerText=book.title;
	titleelement.appendChild(strongText);
	if(book.patron != null)
	{
		patronelement.appendChild(document.createTextNode((book.patron).toString()));
	}


	rowelement.append(bookidelement,titleelement,patronelement);
	bookTable.append(rowelement);


}


// Displays detailed info on the book in the Book Info Section
function displayBookInfo(book) {
	// Add code here
	bookInfo.children[0].children[0].innerText = book.bookId;
	bookInfo.children[1].children[0].innerText = book.title;
	bookInfo.children[2].children[0].innerText = book.author;
	bookInfo.children[3].children[0].innerText = book.genre;
	const patronofbook = book.patron;
	if (patronofbook != null) {
		bookInfo.children[4].children[0].innerText = book.patron.name;
	} else {
		bookInfo.children[4].children[0].innerText = "N/A";
	}


}

// Adds a book to a patron's book list with a status of 'Within due date'.
// (don't forget to add a 'return' button).
function addBookToPatronLoans(book) {
	// Add code here

	// update books table
	const patron_Num = book.patron.cardNumber;
	for (let j = 1; j < bookTable.rows.length; j++) {
		if (parseInt(bookTable.rows[j].children[0].innerText) == book.bookId) {
			bookTable.rows[j].children[2].innerText = patron_Num;
		}
	}


	const row = document.createElement("tr");
	const id = document.createElement("td");
	const title = document.createElement("td");
	const status = document.createElement("td");
	const green = document.createElement("span");
	const return_button = document.createElement("td");
	const strong = document.createElement("strong");
	green.className = "green";
	green.innerText = "Within due date";
	id.innerText = book.bookId;
	strong.innerText = book.title;
	const button = document.createElement("button");
	button.className = "return";
	button.innerText = "return";


	title.appendChild(strong);
	status.append(green);
	row.append(id);
	row.append(strong);
	row.append(status);
	return_button.append(button);
	row.append(return_button);
	const list = document.querySelectorAll(".patron");

	for (let i = 0; i < list.length; i++) {
		if (
			parseInt(list[i].children[1].children[0].innerText) === book.patron.cardNumber
		) {
			list[i].children[3].append(row);
		}
	}
}

// Adds a new patron with no books in their table to the DOM, including name, card number,
// and blank book list (with only the <th> headers: BookID, Title, Status).
function addNewPatronEntry(patron) {
	// Add code here


	let patrodiv = document.createElement("div");
	patrodiv.className = "patron";


	//create name
	let namep = document.createElement("p");
	let namespan = document.createElement("span");
	namep.innerText = "Name: ";
	namespan.innerText = patron.name;
	namespan.className = "bold";
	namep.append(namespan);

	patrodiv.append(namep);

	//create card number
	let cardP = document.createElement("p");
	let boldCard = document.createElement("span");
	cardP.innerText = "Card Number: ";
	boldCard.innerText = patron.cardNumber;
	boldCard.className = "bold";
	cardP.append(boldCard);

	patrodiv.append(cardP);

	//create books on loan
	let book = document.createElement("h4");
	book.innerText = "Books on loan:";

	patrodiv.append(book);

	//create those titles lists
	let Table = document.createElement("table");
	let row = document.createElement("tr");
	let th1 = document.createElement("th");
	let th2 = document.createElement("th");
	let th3 = document.createElement("th");
	let th4 = document.createElement("th");

	Table.className = "patronLoansTable";
	th1.innerText = "BookID";
	th2.innerText = "Title";
	th3.innerText = "Status";
	th4.innerText = "Return";

	row.append(th1);
	row.append(th2);
	row.append(th3);
	row.append(th4);

	Table.append(row);
	patrodiv.append(Table);

	let patronsTable = document.querySelector("#patrons");
	patronsTable.append(patrodiv);
}

// Removes book from patron's book table and remove patron card number from library book table
function removeBookFromPatronTable(book) {
	// Add code here
	//remove PatronLoansTable
	const patronlist = document.querySelectorAll(".patron");
	for (let i = 0; i < patronlist.length; i++)
	{
			if (patronlist[i].children[0].children[0].innerText === book.patron.name)
			{
				let booklist= patronlist[i].children[3];
				for (let j = 1; j < booklist.rows.length; j++)
				{
					if(parseInt(booklist.rows[j].children[0].innerText) === book.bookId)
					{
						booklist.rows[j].remove();
					}
				}
			}

	}
	//remove patron card number from booktable and set value of patron to be null
	for (let j = 1; j < bookTable.rows.length; j++)
	{
		if(parseInt(bookTable.rows[j].children[0].innerText) == book.bookId)
		{
			bookTable.rows[j].children[2].innerText = null;
			book.patron=null;

		}
	}
}

// Set status to red 'Overdue' in the book's patron's book table.
function changeToOverdue(book) {
	// Add code here
	//fetch all the patrons
	const patronlist = document.querySelectorAll(".patron");

	//find who owns the book
	for ( let i = 0; i < patronlist.length; i++)
	{
			if(patronlist[i].children[0].children[0].innerText === book.patron.name)
			{
				//find which book he owns
				let booklist = patronlist[i].children[3];
				for (let j = 1; j < booklist.rows.length; j++)
				{
					if(parseInt(booklist.rows[j].children[0].innerText) === book.bookId)
					{
						booklist.rows[j].children[2].innerText = "Overdue";
						booklist.rows[j].children[2].className = "red ";

					}
				}
			}

	}
}


