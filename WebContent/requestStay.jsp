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
<title>Request Stay</title>
</head>
<body>


<!-- Navbar (sit on top) -->
<div class="w3-top">
  <div class="w3-bar" id="myNavbar" style="padding-left: 10px; padding-right: 10px; color:#777;">
    <a class="w3-bar-item w3-button w3-hover-black w3-hide-medium w3-hide-large w3-right" href="javascript:void(0);" onclick="toggleFunction()" title="Toggle Navigation Menu">
      <i class="fa fa-bars"></i>
    </a>
    <a href="/CancelTrip" class="w3-bar-item w3-button"><img src="AdobeStock_Overlay.png" height=30px></a>
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

<div style="text-align: center" class="w3-panel w3-red w3-round">Please use the 'Cancel Trip' button rather than navigating away from the page.</div>
<br>
<div id="jsPPrice" style="display: none;">${totalPrice}</div>
<div id="formdiv" style="text-align: center">

<form action="CancelTrip" name="cancelTrip" id="cancelTrip" method="POST">
<input type="hidden" name="hiddenCancelStartDate" id="hiddenCancelStartDate"/>
<input type="hidden" name="hiddenCancelEndDate" id="hiddenCancelEndDate"/>
<input type="submit" value="Cancel Trip" class="w3-button w3-round w3-center w3-red">
</form>

</div>
<br>
<div style="border: 1px solid grey; border-radius: 10px; box-shadow: 1px 2px #888888;">
<form action="Calculate"  style="margin-left: 20%; margin-right: 20%; text-align: left;"method="POST">
<label>First Name: </label>
<input type="text" class="w3-input" name="fname" id="fname" required><br>

<label>Last Name: </label>
<input type="text" class="w3-input" name="lname" id="lname" required><br>

<label>Phone Number: </label>
<input type="text" class="w3-input" name="phone" id="phone" required><br>


<label>Enter Email: </label>
<input type="email" class="w3-input" name="email" id="email" required><br>

<label>Number of People (Max 8): </label>
<input type="text" class="w3-input" name="ppl" id="ppl" required><br>

<label>Enter Promo Code: (Optional) </label>
<input type="text" class="w3-input" name="promo" id="promo"><br>
  
  <input type="hidden" name="hiddenStartDate" id="hiddenStartDate"/>
  <input type="hidden" name="hiddenEndDate" id="hiddenEndDate"/>
  <input type="hidden" name="hiddenPrice" id="hiddenPrice"/>
  

  
  <input type="submit" value="Request Stay" class="w3-button w3-round w3-center w3-blue-grey" style="margin-left: 40%; margin-bottom: 10px;">
</form>
<br>
</div>
</div>

<br><br>
<div style="display: none;">
<p style="text-align: left;">Check in: <span id="checkind" style="float: right;"><b>${startDate}</b></span></p>
<p style="text-align: left;">Check out: <span id="checkoutd" style="float: right;"><b>${endDate}</b></span></p>
</div>
</body>
<script>
//checkout timer
function startTimer(duration, display) {
    var timer = duration, minutes, seconds;
    setInterval(function () {
        minutes = parseInt(timer / 60, 10)
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            timer = duration;
        }
    }, 1000);
}

window.onload = function () {
    var fiveMinutes = 60 * 5,
        display = document.querySelector('#time');
    startTimer(fiveMinutes, display);
};

//set timer for session timeout, redirect to servlet that deletes startdate and enddate from temp dates
var checkin = document.getElementById("checkind").innerHTML;
var checkout = document.getElementById("checkoutd").innerHTML;

document.getElementById("hiddenStartDate").value=checkin.slice(3,31);
document.getElementById("hiddenEndDate").value=checkout.slice(3,31);
document.getElementById("hiddenCancelStartDate").value=checkin.slice(3,31);
document.getElementById("hiddenCancelEndDate").value=checkout.slice(3,31);
document.getElementById("hiddenPrice").value = price;

setTimeout(function(){document.getElementById("cancelTrip").submit();},300000);
window.onbeforeunload = cancel;
document.pagehide = cancel;


function cancel(){
	document.getElementById("cancelTrip").submit();
}

</script>
</html>