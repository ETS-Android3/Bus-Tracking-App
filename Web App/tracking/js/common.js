function getURL() {
    let url = "http://192.168.0.115:8082/";
    return url;

}

function sendLoginRequest(email, password, successHandler, errHandler) {

    localStorage.removeItem('auth');
    localStorage.setItem('auth', btoa(email + ":" + password));

    let data = {
        email: email,
        password: password
    };

    jQuery.ajax({
        type: 'POST',

        url: getURL() + "user/login",
        contentType: "application/json",
        headers: {
            'Authorization': 'Basic ' + getAuth()

        },

        data: JSON.stringify(data),
        dataType: "json",
        accepts: "application/json",
        success: function (data, status, jqXHR) {
            successHandler(data);

        },

        error: function (jqXHR, message) {
            errHandler(jqXHR.responseText.message);
        }

    });

}

function getAuth() {
    let auth = localStorage.getItem('auth');
    return auth;
}

function goToPage(url) {
    $(location).attr('href', url);
}

function sendRegisterRequest(firstName, lastName, email, password, successHandler, errHandler) {



    let data = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password
    };

    jQuery.ajax({
        type: 'POST',

        url: getURL() + "user/register",
        contentType: "application/json",


        data: JSON.stringify(data),
        dataType: "json",
        accepts: "application/json",
        success: function (data, status, jqXHR) {
            successHandler(data);

        },

        error: function (jqXHR, message) {
            errHandler(jqXHR.responseText.message);
        }

    });

}

function sendPositionRequest(terminalId, startDate, endDate, successHandler, errHandler) {

    let data = {
        terminalId: terminalId,
        startDate: startDate,
        endDate: endDate
    };

    jQuery.ajax({
        type: "POST",
        url: getURL() + "position/getAllByDate",
        contentType: "application/json",
        headers: {
            'Authorization': 'Basic ' + getAuth()

        },

        data: JSON.stringify(data),
        dataType: "json",
        accepts: "application/json",
        success: function (data, status, jqXHR) {
            successHandler(data);
            console.log(data);

        },
        error: function (jqXHR, message) {
            errHandler(jqXHR.responseText.message);

        }
    });


}

function doLogout() {

    sessionStorage.clear('auth');
    goToPage('index.html')


}


function sendCommentRequest(successHandler, errHandler) {
    data = null
    jQuery.ajax({
        type: "GET",
        url: getURL() + "comment/getAllComments",
        contentType: "application/json; charset=UTF-8",
        headers: { 'Authorization' : 'Basic ' + getAuth(),
         },
        accepts: "application/json",
        success: function (data, status, jqXHR) {
            successHandler (data);
        },

        error: function (error, jqXHR) {
            console.log(error + jqXHR.responseText);
        }
    });

}