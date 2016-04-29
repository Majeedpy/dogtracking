var map;

//This function is called at the document ready action in order to get the number of dogs and assign it to the
//maximum possible value of K-means index so that a user doesn't
$(document).ready(function() {
    jQuery.ajax({
        url: "http://dogtracking-majeedapps.rhcloud.com/dogsCount",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function(resultData) {
            var result = JSON.stringify(resultData);

            var input = document.getElementById("kIndex");
            input.setAttribute("max", result); // set a new value;
        },
        error: function(jqXHR, status) {
            // error handler
        }
    });
});


//This function is used to initialize the map on the map viewer. Latitude and longitude are read from result of the rest
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {
            lat: 51.0486,
            lng: -114.0708
        },
        zoom: 16
    });
    jQuery.ajax({
        url: "http://dogtracking-majeedapps.rhcloud.com/dogs",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function(resultData) {
            var result = JSON.stringify(resultData);
            var data = $.parseJSON(result);
            $.each(data, function(key, value) {
                var lati = parseFloat(value.latitude);
                var longi = parseFloat(value.longitude);
                var myLatLng = {
                    lat: lati,
                    lng: longi
                };
                var marker = new google.maps.Marker({
                    position: myLatLng,
                    map: map
                });

                //Infow window
                var dogId = value.id;
                var contentString = "<b>" + 'Name: ' + "</b>" + value.name + "<br />" +
                    "<b>" + 'Weight: ' + "</b>" + value.weight + "<br />" +
                    "<b>" + 'Heartbeat: ' + "</b>" + value.heartBeat + "<br />" +
                    "<b>" + 'Temperature: ' + "</b>" + value.temperature + "<br /><br/>" +
                    '<button class="btn btn-primary" onclick="deleteDog(\'' + dogId + '\')">Delete</button>';
                var infowindow = new google.maps.InfoWindow({
                    content: contentString
                });
                marker.addListener('click', function() {
                    infowindow.open(map, marker);
                });
                //


            });

        },
        error: function(jqXHR, textStatus, errorThrown) {},

        timeout: 120000,
    });
}

//This function is used to get the clusters from KM data
function cluster(k) {

    var k = $('#kIndex').val();
    if (!k) {
        alert('Please select a value for the index');
    }

    jQuery.ajax({

        url: "http://dogtracking-majeedapps.rhcloud.com/dogClusters",
        type: "GET",
		data:{k:k},
        contentType: 'application/json; charset=utf-8',
        success: function(resultData) {
            var table = document.getElementById("table");
            while (table.rows.length > 1) {
                table.deleteRow(1);
            }
            var result = JSON.stringify(resultData);
            var parsedJSON = JSON.parse(result);
            for (var i = 0; i < parsedJSON.length; i++) {
                var result2 = JSON.stringify(parsedJSON[i]);
                var data = $.parseJSON(result2);

                var table = $("#table");
                $.each(data, function(key, value)

                    {
                        table.append("<tr><td>" + value.id + "</td><td>" + value.weight + "</td>   <td>" + value.clusterId + "</td></tr>");
                    });

            }

        },
        error: function(jqXHR, textStatus, errorThrown) {

        },

        timeout: 120000,
    });
}


function deleteDog(id) {
    var id = id;
    jQuery.ajax({
        type: "POST",

        url: "http://dogtracking-majeedapps.rhcloud.com/dogs/delete/" + id,
        contentType: "application/json; charset=utf-8",
        success: function(data, status, jqXHR) {
            window.location.href = "map.html";
        },

        error: function(jqXHR, status) {
            // error handler
        }
    });
}

function createDog() {

    var weight = $('#weight').val();
    var name = $('#name').val();
    var heartBeat = $('#heartBeat').val();
    var temperature = $('#temperature').val();
    var latitude = $('#lat').val();
    var longitude = $('#lng').val();
    jQuery.ajax({
        type: "POST",
        url: "http://dogtracking-majeedapps.rhcloud.com/dogs/insert/" + weight + '/' + name + '/' + heartBeat + '/' + temperature + '/' + latitude + '/' + longitude + '/',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data, status, jqXHR) {
            window.location.href = "index.html";
        },

        error: function(jqXHR, status) {
            alert("Creation unsuccessful");
        }
    });
}