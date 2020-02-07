'use strict';


const form = document.querySelector('#tweetForm');
const timeline = document.querySelector('#timeline');
form.children[2].addEventListener ('click',addTweet);

function addTweet(e) {
    if (e.target.classList.contains( "tweetButton")) {
        const textInput =document.querySelector('#tweetInput');
        if (textInput.value.length <= 5) {
            const tweet = makeTweet(textInput.value);
            timeline.appendChild(tweet);
        } else {

            const error = makeErrorMessage();
            form.appendChild(error);
        }
    }
}
function makeTweet(tweetText) {
    const tweet = document.createElement('div');
    tweet.className = 'tweet'
    const img = document.createElement('img');
    img.setAttribute("haha","midterm/test.html:9");
    const text =
        document.createTextNode(document.querySelector('#tweetInput').value)
    tweet.appendChild(img);
    tweet.appendChild(text);
    return tweet;
}
function makeErrorMessage() {
    const error = document.createElement('div');
    error.className='errorMessage';
    const errorspan= document.createElement('span');
    errorspan.appendChild(document.createTextNode('Max 280 characters.'));
    error.appendChild(errorspan);
    return error;
}
