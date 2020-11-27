function doLogin() {

    let email = $('#emailLogin').val().trim();
    if (email === "") {
        email = null;
    }

    let password = $('#passwordLogin').val().trim();
    if (password === "") {
        password = null;
    }
    if(email === null || password === null){
        alert("Please complete all required fields!");
    }else {
    sendLoginRequest(email, password, getLoginSuccessHandler, getLoginErrorHandler);
    }
}

function getLoginSuccessHandler(respData) {

    goToPage('home.html')
}

function getLoginErrorHandler(message, status) {
    alert("Server: " + message); // popup on err.
}

function doRegister() {

    let firstName = $('#firstName').val().trim();
    if (firstName === "") {
        firstName = null;
    }

    let lastName = $('#lastName').val().trim();
    if (lastName === "") {
        lastName = null;
    }

    let email = $('#emailRegister').val().trim();
    if (email === "") {
        email = null;
    }

    let password = $('#passwordRegister').val().trim();
    if (password === "") {
        password = null;
    }
    let passwordConfirm = $('#passwordConfirm').val().trim();
    if (passwordConfirm === "") {
        passwordConfirm = null;
    }

    if (firstName === "" || lastName === "" || email === "" || password === "" || passwordConfirm === "") {
        alert("Please complete all required fields!");
    } else if (passwordConfirm !== password) {
        alert("Password do not matches!");
    }else if(checkTerms.checked != 1){
        alert("In order to regsietr you must accept the terms and conditions!");
    } else {

        sendRegisterRequest(firstName, lastName, email, password, getRegisterSuccessHandler, getRegisterErrorHandler);

    }

}

function getRegisterSuccessHandler(message, status) {
    alert("Successfully regsitred!");
    goToPage('index.html');

}

function getRegisterErrorHandler(message, status) {
    alert("Server: " + message); // popup on err.
}
