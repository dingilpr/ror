<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type ="text/css" href="styles.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:500">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="codebase/dhtmlxcalendar.css" 
    type="text/css"> 
<script src="codebase/dhtmlxcalendar.js" 
    type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Poppins", sans-serif;}
body, html {
    height: 100%;
    color: #595959;
    line-height: 1.8;
    font-size: 106%;
}
</style>
<title>Error</title>
</head>
<body>
<div id="jspVar" style="display: none;">${errorCode}</div>
 

<!-- Navbar (sit on top) -->
<div class="w3-top">
  <div class="w3-bar" id="myNavbar" style="padding-left: 10px; padding-right: 10px; color:#777;">
    <a class="w3-bar-item w3-button w3-hover-black w3-hide-medium w3-hide-large w3-right" href="javascript:void(0);" onclick="toggleFunction()" title="Toggle Navigation Menu">
      <i class="fa fa-bars"></i>
    </a>
    <a href="#" class="w3-bar-item w3-button"><img src="AdobeStock_Overlay.png" height=30px></a>
    <a href="#" id="bbtn" class="w3-bar-item w3-button w3-hide-small"  style="float:right; text-decoration: none;">  BOOK</a>
    <a href="#" id="ebtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; text-decoration: none;">  EXPLORE</a>
    <a href="#" id="rbtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; padding-right: 5px; text-decoration: none;">RELAX</a>
  </div>

  <!-- Navbar on small screens -->
  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium">
    <a href="#about" class="w3-bar-item w3-button" onclick="toggleFunction()">ABOUT</a>
    <a href="#portfolio" class="w3-bar-item w3-button" onclick="toggleFunction()">PORTFOLIO</a>
    <a href="#contact" class="w3-bar-item w3-button" onclick="toggleFunction()">CONTACT</a>
    <a href="#" class="w3-bar-item w3-button">SEARCH</a>
  </div>
</div>

<div class="w3-content w3-container w3-padding-64" id="checkout" style="text-align:center">

<div style="border: 1px solid grey; border-radius: 10px">
<h1 id="error"></h1>


</div>
<br>
<a href="/Index"><button class="w3-center w3-button w3-large w3-large w3-blue-grey" style="text-align: center">Return home</button></a>
</div>

</body>
<script>

var errorCode = document.getElementById("jspVar").textContent;
if(errorCode == 2 || errorCode == 3){
	document.getElementById("error").innerHTML = "Invalid Dates. This could mean that someone has these dates in their cart currently. Please return home and try again.";
}

if(errorCode == 1){
	document.getElementById("error").innerHTML = "Invalid Dates. Your check in date must be before your check out date. Please return home and try again.";
}

if(errorCode == 5){
	document.getElementById("error").innerHTML = "Timeout.";
}

if(errorCode == 10){
	document.getElementById("error").innerHTML = "Your Trip Payment Code has expired. Your trip has been cancelled.";
}

if(errorCode == 11){
	document.getElementById("error").innerHTML = "Your payment failed. Please revisit the payment code page and try again. If problems persist, email info@sartopartners.com.";
}

if(errorCode == 15){
	document.getElementById("error").innerHTML = "We're sorry, the price for one or more of these days has not yet been finalized.";
}

</script>
</html>