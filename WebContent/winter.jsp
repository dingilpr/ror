<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type ="text/css" href="styles.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:500">
<link href="https://fonts.googleapis.com/css?family=Righteous" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="codebase/dhtmlxcalendar.css"> 
<title>Insert title here</title>
<script type="text/javascript" src="snowstorm.js"></script>
</head>
<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Poppins", sans-serif;}
body, html {
    height: 100%;
    color: #595959;
    line-height: 1.8;
    font-size: 106%;
}
</style>
<body>
<!-- Navbar (sit on top) -->
<div class="w3-top">
  <div class="w3-bar" id="myNavbar" style="padding-left: 10px; padding-right: 10px; color:#777;">
    <a class="w3-bar-item w3-button w3-hover-black w3-hide-medium w3-hide-large w3-right" href="javascript:void(0);" onclick="toggleFunction()" title="Toggle Navigation Menu">
      <i class="fa fa-bars"></i>
    </a>
    <a href="#" class="w3-bar-item w3-button"><img src="ROR.Logo_Final-02.png" height=60px></a>
    <a href='#' id="sbtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; text-decoration: none;" onclick="document.getElementById('id01').style.display='block'">SIGN UP</a>
    <a href="#" id="bbtn" class="w3-bar-item w3-button w3-hide-small"  style="float:right; text-decoration: none;">  BOOK</a>
    <a href="#" id="ebtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; text-decoration: none;">  EXPLORE</a>
    <a href="#" id="rbtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; padding-right: 5px; text-decoration: none;">DETAILS</a>
  </div>

<div id="id01" class="w3-modal">
    <div class="w3-modal-content">
      <div class="w3-container">
        <p onclick="document.getElementById('id01').style.display='none'" class="w3-button w3-display-topright">x</p>
        <h2 style="text-align:center">Join our newsletter!</h2>
        <form class="w3-container" action ="AddEmail" method="post">
			<label>Email: </label>
			<input class="w3-input" type="email" id="email" name="email"><br>
			<input type="submit" value="Register!" class="w3-button w3-center w3-blue-grey" style="text-align:center">
		</form>
		<br>
      </div>
    </div>
  </div>
  <!-- Navbar on small screens -->
  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium">
    <a href="#about" class="w3-bar-item w3-button" onclick="toggleFunction()">BOOK</a>
    <a href="#portfolio" class="w3-bar-item w3-button" onclick="toggleFunction()">EXPLORE</a>
    <a href="#contact" class="w3-bar-item w3-button" onclick="toggleFunction()">DETAILS</a>
    
  </div>
</div>

<div class="w3-row-padding w3-padding-16 w3-center w3-animate-bottom" id="list" style="padding-top: 100px!important;">
<h1 style="text-align: center;"><b>Resorts</b></h1>
<br>
	<div class="w3-third">
		<div class="w3-card w3-hover-shadow" style="padding-bottom: 10px;">
		<br><p><b>Winter Park</b></p>
		<hr>
		<p>Distance: 56 miles</p>
		<p>Base depth: </p>
		<button class="w3-button w3-round w3-blue-grey">View Deals</button>
		<br>
		</div>
	</div>
	
	<div class="w3-third">
		<div class="w3-card w3-hover-shadow" style="padding-bottom: 10px;">
		<br><p><b>Eldora</b></p>
		<hr>
		<p>Distance: 40.5 miles</p>
		<p>Base depth: </p>
		<button class="w3-button w3-round w3-blue-grey">View Deals</button>
		<br>
		</div>
	</div>
	
	<div class="w3-third">
		<div class="w3-card w3-hover-shadow" style="padding-bottom: 10px;">
		<br><p><b>Copper Mountain</b></p>
		<hr>
		<p>Distance: 67.4 miles</p>
		<p>Base depth: </p>
		<button class="w3-button w3-round w3-blue-grey">View Deals</button>
		<br>
		</div>
	</div>
</div>

 <hr id="maps">

  <!-- About Section -->
  <div class="w3-container w3-padding-32 w3-center">  
   <div id="googleMap" class="w3-round-large w3-greyscale" style="width:80%;height:400px; margin-left: 10%;"></div>
  </div>
  
<script>
function myMap(){
	myCenter=new google.maps.LatLng(39.664970, -105.196543); //rr
	  var mapOptions= {
	    center:myCenter,
	    styles:[
	        {
	            "stylers": [
	                {
	                    "hue": "blue"
	                },
	                {
	                    "saturation": 250
	                }
	            ]
	        },
	        {
	            "featureType": "road",
	            "elementType": "geometry",
	            "stylers": [
	                {
	                    "lightness": 50
	                },
	                {
	                    "visibility": "simplified"
	                }
	            ]
	        },
	        {
	            "featureType": "road",
	            "elementType": "labels",
	            "stylers": [
	                {
	                    "visibility": "off"
	                }
	            ]
	        }
	    ],
	    zoom:8, scrollwheel: false, draggable: true,
	    mapTypeId:google.maps.MapTypeId.ROADMAP
	  };
	  var map=new google.maps.Map(document.getElementById("googleMap"),mapOptions);
	  var marker = new google.maps.Marker({
		    position: myCenter,
		    map: map
		  });
		  var infowindow =  new google.maps.InfoWindow({
				content: 'Ranch on the Rocks',
				map: map,
				position: myCenter
			});
		  marker.setMap(map);
		  infowindow.open(map, marker);
		  
		  var markerTwo = new google.maps.Marker({
			    position: new google.maps.LatLng(39.891414, -105.765389),
			    map: map
			  });
		  var infowindowTwo = new google.maps.InfoWindow({
			  content: 'Winter Park',
			  map: map,
			  position: new google.maps.LatLng(39.891414, -105.765389)
		  });
		  markerTwo.setMap(map);
		  infowindowTwo.open(map, markerTwo);
		  
		  var markerThree = new google.maps.Marker({
			    position: new google.maps.LatLng(39.937516, -105.581970),
			    map: map
			  });
		  var infowindowThree = new google.maps.InfoWindow({
			  content: 'Eldora',
			  map: map,
			  position: new google.maps.LatLng(39.937516, -105.581970)
		  });
		  markerThree.setMap(map);
		  infowindowThree.open(map, markerThree);
		  
		  var markerFour = new google.maps.Marker({
			    position: new google.maps.LatLng(39.501327, -106.152507),
			    map: map
			  });
		  var infowindowFour = new google.maps.InfoWindow({
			  content: 'Copper Mountain',
			  map: map,
			  position: new google.maps.LatLng(39.501327, -106.152507)
		  });
		  markerFour.setMap(map);
		  infowindowFour.open(map, markerFour);
		  
	
}
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDobWRfsfe66MoFpQ6axmYgi4y40xP0zSw&callback=myMap"></script>
</body>
</html>