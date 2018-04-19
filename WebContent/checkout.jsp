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
<title>Checkout</title>
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
<h2>Please review the following details for accuracy: </h2>
<hr>
<p style="text-align: right; padding-right: 5%">You have <span id="time">05:00</span> minutes left to check out!</p>
<p style="text-align: left; padding-left: 20%">Check in: <span id="checkind" style="padding-left: 43%"><b>${startDate}</b></span></p>
<p style="text-align: left; padding-left: 20%">Check out: <span id="checkoutd" style="padding-left: 41%"><b>${endDate}</b></span></p>
<p style="text-align: left; padding-left: 20%">Total Days: <span id="" style="padding-left: 40.5%"><b>${dayCounter}</b></span></p>
<p style="text-align: left; padding-left: 20%">Price per Day: <span id="" style="padding-left: 37.5%"><b>x $${pricePerDay}</b></span></p>
<p style="text-align: left; padding-left: 20%">Deposit: <span id="" style="padding-left: 44%"><b>+ $${deposit}</b></span></p>
<p style="text-align: left; padding-left: 20%">Cleaning Fee: <span id="" style="padding-left: 37.5%"><b>+ $${cleaning}</b></span></p>
<hr>
<p style="text-align: left; padding-left: 20%"><b>Total Price: <b></b><span id="price" style="padding-left: 40.5%; font-size: 110%;"><b>= $${totalPrice}</b></span></p>
<hr>
<p style="text-align: left; padding-left: 1%; font-size: 80%;"><em>You will pay the property in the property's local currency (US$). The displayed amount (in USD) is indicative and based on the exchange rate at the time of booking. 
Guests are required to show a photo ID and credit card upon check-in. Please note that all Special Requests are subject to availability and additional charges may apply. A damage deposit of USD 850 is required on arrival. This will be collected by credit card. You should be reimbursed within 7 days of check-out. Your deposit will be refunded in full by credit card, subject to an inspection of the property.</em></p>
<hr>
<br>

<form action="PreparePayment"  style="margin-left: 20%; margin-right: 20%; text-align: left;"method="POST">
<label>First Name: </label>
<input type="text" class="w3-input" name="fname" id="fname" required><br>

<label>Last Name: </label>
<input type="text" class="w3-input" name="lname" id="lname" required><br>

<label>Phone Number: </label>
<input type="text" class="w3-input" name="phone" id="phone" required><br>


<label>Enter Email: </label>
<input type="email" class="w3-input" name="email" id="email" required><br>

<label>Enter Promo Code: (Optional) </label>
<input type="text" class="w3-input" name="promo" id="promo"><br>
  
 
  
  <input type="hidden" name="hiddenStartDate" id="hiddenStartDate"/>
  <input type="hidden" name="hiddenEndDate" id="hiddenEndDate"/>
  <input type="hidden" name="hiddenPrice" id="hiddenPrice"/>
  
  <input type="submit" value="Continue to Checkout" class="w3-button w3-round w3-center w3-blue-grey">
</form>
<br>
</div>
</div>

<br><br>

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
var trimmedCheckin = checkin.slice(0,14) + checkin.slice(31,35);
var trimmedCheckout = checkout.slice(0,14) + checkout.slice(31,35);
document.getElementById("checkind").innerHTML = trimmedCheckin;
document.getElementById("checkoutd").innerHTML = trimmedCheckout;

var price = document.getElementById("jsPPrice").innerHTML;

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