function doPosition() {

    let startDate = $('#startdate').val().trim();
    if(startDate === ""){
        startDate = null;
    }

    let endDate = $('#enddate').val().trim();
    if(endDate === ""){
        endDate = null;
    }

    let terminalId = $('#terminalid').val().trim();
    if(terminalId === ""){
        terminalId = null;
    }



    sendPositionRequest(terminalId,startDate,endDate,positionSuccessHandler,positionErrorHandler);

    
}

function positionSuccessHandler(responseData) {
    localStorage.removeItem('markers');
    localStorage.setItem('markers',JSON.stringify(responseData));
}

function positionErrorHandler(message, status) {
    localStorage.removeItem('markers');
    alert('Server: ' + message);
}

