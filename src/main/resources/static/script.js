/**
 *  declare the url which is our detination for the fetch request
 *  Note: we're calling the origin of the url so that we can capture the host whether it is localhost or
 *  AWS EC2 instance hosting our app.
 */
const url = window.location.origin + ('/api');

let btn = document.getElementById('btn');
btn.addEventListener("click", addUser);

console.log(url); // check that our url is http://<host-server>:5000/api

// send the POST request with fetch() inside a method

function addUser() {
	
	// Concatenate the end point we want to hit with POST request to base URL
	const postUrl = url + "/users/add";

    // Build a user object from the input fields by querying the DOM
    let ifirstName = document.getElementById('firstName').value
    let ilastName = document.getElementById('lastName').value
    let iusername = document.getElementById('username').value
    let ipassword = document.getElementById('password').value
    let iemail = document.getElementById('email').value

    // ideally we validate all of the values before including them in our request to the server

    // build the user obj literal to send JSON obj
    let user = {
        firstName: ifirstName,
        lastName: ilastName,
        username: iusername,
        password: ipassword,
        email: iemail
    };

    // we need to build our request OPTIONS (set up the method, body, headers of the request)
    const options = {
        method: 'POST',
        body: JSON.stringify(user), // stringify JSON to send to server endpoint
        headers: {
            'Content-Type': 'application/json'
        }
    }

    // this is just another way of doing the above using some Fetch API Interfaces
    const options2 = new Request(url, { // note the Use of Request Obj
        method: 'POST',
        body: JSON.stringify(user),
        headers: new Headers({ // note the use of Headers obj (interface from Fetch API)
            'Content-Type': 'application/json'
        })
    })

    
    fetch(postUrl, options)
        .then(res => res.json())
        .then(res => console.log(res))
}
