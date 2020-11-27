let map;
let myLatLn = {
    lat: parseFloat(46.782),
    lng: parseFloat(23.4587)
};

function initMap() {

    map = new google.maps.Map(document.getElementById('map'), {
        center: {
            lat: 46.782,
            lng: 23
        },
        zoom: 11
    });
    let marker = new google.maps.Marker({
        position: myLatLn,
        map: map,
        title: "Default marker"
    });
}

function addMarkers() {
    initMap();
    doPosition();
    setTimeout(() => {
        let markers = JSON.parse(localStorage.getItem('markers'));

        for (let i = 0; i < markers.length; i++) {
            let pos = {
                lat: parseFloat(markers[i].latitude),
                lng: parseFloat(markers[i].longitude)
            };
            console.log(markers[i].latitude + " :: " + markers[i].longitude + markers[i].creationTime.substring(0, 16));
            let marker = new google.maps.Marker({
                position: pos,
                map: map,
                label: {
                    color: 'white',
                    fontWeight: 'bold',
                    text: "Bus with id:" + markers[i].terminalId + "was here in date: " + markers[i].creationTime.substring(0, 10)+ " at: " + markers[i].creationTime.substring(11,16),
                }

            });
        }
    }, 2000);
}

function seeFromToday(checkToday) {
    let today = new Date();
    let todayDay = today.getDate();
    let todayMonth = today.getMonth() + 1;
    let todayYear = today.getFullYear();

    let tommorow = new Date(today);
    tommorow.setDate(tommorow.getDate() + 1);

    if (checkToday.checked == 1) {
        document.getElementById('startdate').value = todayYear + '-' + todayMonth + '-' + todayDay;
        document.getElementById('enddate').value = tommorow.getFullYear() + '-' + (tommorow.getMonth() + 1) + '-' + tommorow.getDate();
    } else {
        document.getElementById('startdate').value = '';
        document.getElementById('enddate').value = '';
    }
}