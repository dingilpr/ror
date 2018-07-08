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
<script src="https://checkout.stripe.com/checkout.js"></script>
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
<div id="jsPStart" style="display: none;">${startDate}</div>
<div id="jsPEnd" style="display: none;">${endDate}</div>
<div id="jsPFirstName" style="display: none;">${firstName}</div>
<div id="checkind" style="display: none;">${startDate}</div>
<div id="checkoutd" style="display: none;">${endDate}</div>
<div id="firstName" style="display: none;">${firstName}</div>
<div id="lastName" style="display: none;">${lastName}</div>
<div id="phone" style="display: none;">${pNumber}</div>
<div id="email" style="display: none;">${email}</div>
<div id="promo" style="display: none;">${promo}</div>
<div id="code" style="display: none;">${code}</div>
<div id="depositCheck" style="display: none;">${deposit}</div>
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
<p style="text-align: right; padding-right: 5%; color: red;">You have <span id="time" style="color: red;">05:00</span> minutes left to check out!</p>
<div style="padding-left: 5%; padding-right: 5%;">
<p style="text-align: left;">Check in: <span id="checkindt" style="float: right;"><b></b></span></p>
<p style="text-align: left;">Check out: <span id="checkoutdt" style="float: right;"><b></b></span></p>
<p style="text-align: left;">Total Nights: <span id="" style="float: right;"><b>${dayCounter}</b></span></p>
<p style="text-align: left;">Price per Night: <span id="" style="float: right;"><b>x $${pricePerDay}</b></span></p>
<p style="text-align: left;">Cleaning Fee: <span id="" style="float: right;"><b>+ $${cleaning}</b></span></p>
<p style="text-align: left;">Deposit: <span id="" style="float: right;"><b>($${deposit})</b></span></p>
<p style="text-align: left; display: none" id="showDisc">Discount: <span id="showDsc" style="float: right; display: none;"><b>- ${promo}%</b></span></p>
</div>
<hr>
<p style="text-align: left; padding-left: 20%"><b>Total Price: <b></b><span id="price" style="padding-left: 40.5%; font-size: 110%;"><b>= $${totalPrice}</b></span></p>
<hr>
<p style="text-align: left; padding-left: 1%; font-size: 80%;"><em>You will pay the property in the property's local currency (US$). The displayed amount (in USD) is indicative and based on the exchange rate at the time of booking. 
Guests are required to show a photo ID and credit card upon check-in. Please note that all Special Requests are subject to availability and additional charges may apply. A damage deposit of USD 850 is required on arrival. This will be collected by credit card. You should be reimbursed within 7 days of check-out. Your deposit will be refunded in full by credit card, subject to an inspection of the property.</em></p>
<hr>

<form action="Process" method="POST" id="toServ">
   <input type="hidden" id="stripeToken" name="stripeToken" />
   <input type="hidden" id="stripeEmail" name="stripeEmail" />
   <input type="hidden" id="amountInCents" name="amountInCents" />
   <input type="hidden" name="hiddenStartDate" id="hiddenStartDate"/>
   <input type="hidden" name="hiddenEndDate" id="hiddenEndDate"/>
   <input type="hidden" name="hiddenFirstName" id="hiddenFirstName"/>
   <input type="hidden" name="hiddenLastName" id="hiddenLastName"/>
   <input type="hidden" name="hiddenpNumber" id="hiddenpNumber"/>
   <input type="hidden" name="hiddenEmail" id="hiddenEmail"/>
   <input type="hidden" name="hiddenPromo" id="hiddenPromo"/>
   <input type="hidden" name="hiddenCode" id="hiddenCode"/>
</form>

<input type="button" class="w3-button w3-green w3-round w3-large" id="customButton" style="text-align: center; margin-bottom: 10px;" value="Pay Now!">

<br>
</div>
</div>

<br><br>

</body>
<script>
var checkin;
var checkout;
var trimmedCheckin;
var trimmedCheckout;
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
    checkin = document.getElementById("checkind").innerHTML;
	checkout = document.getElementById("checkoutd").innerHTML;
	trimmedCheckin = checkin.slice(0,11) + checkin.slice(24,29);
	trimmedCheckout = checkout.slice(0,11) + checkout.slice(24,29);
	document.getElementById("checkindt").innerHTML = "<b>" + trimmedCheckin + "</b>";
	document.getElementById("checkoutdt").innerHTML = "<b>" + trimmedCheckout + "</b>";
	document.getElementById("checkind").innerHTML = trimmedCheckin;
	document.getElementById("checkoutd").innerHTML = trimmedCheckout;
	
};

//set timer for session timeout, redirect to servlet that deletes startdate and enddate from temp dates


var price = document.getElementById("jsPPrice").innerHTML;



var jspStart = document.getElementById("checkind").innerHTML;
var jspEnd = document.getElementById("checkoutd").innerHTML;
var jspfName = document.getElementById("firstName").innerHTML;
var jsplName = document.getElementById("lastName").innerHTML;
var jspPhone = document.getElementById("phone").innerHTML;
var jspEmail = document.getElementById("email").innerHTML;
var jspPromo = document.getElementById("promo").innerHTML;
var jspCode = document.getElementById("code").innerHTML;
var jspDepositCheck = document.getElementById("depositCheck").innerHTML;

document.getElementById("hiddenCancelStartDate").value = jspStart;
document.getElementById("hiddenCancelEndDate").value = jspEnd;
document.getElementById("hiddenStartDate").value = jspStart;
document.getElementById("hiddenEndDate").value = jspEnd;
document.getElementById("hiddenFirstName").value = jspfName;
document.getElementById("hiddenLastName").value = jsplName;
document.getElementById("hiddenpNumber").value = jspPhone;
document.getElementById("hiddenEmail").value = jspEmail;
document.getElementById("hiddenPromo").value = jspPromo;
document.getElementById("hiddenCode").value = jspCode;

var sPrice = '${totalPrice}' * 100;

setTimeout(function(){document.getElementById("cancelTrip").submit();},300000);
window.onbeforeunload = cancel;
document.pagehide = cancel;


function cancel(){
	document.getElementById("cancelTrip").submit();
}

var disc = '${promo}';

if(disc != ""){
	document.getElementById("showDisc").style.display = "block";
	document.getElementById("showDsc").style.display = "block";
}

var handler = StripeCheckout.configure({
	  key: 'pk_test_fTbAto5ojlJIf7XgMTJGJA74',
	  image: 'https://stripe.com/img/documentation/checkout/marketplace.png',
	  token: function(token) {
	    $("#stripeToken").val(token.id);
	    $("#stripeEmail").val(token.email);
	    $("#amountInCents").val(sPrice);
	    $("#toServ").submit();
	  }
	});

	$('#customButton').on('click', function(e) {
	  // Open Checkout with further options
	  handler.open({
	    name: 'Ranch on the Rocks',
	    description: 'Rental',
	    amount: sPrice,
	    zipCode: true,
	  });
	  e.preventDefault();
	});

	// Close Checkout on page navigation
	$(window).on('popstate', function() {
	  handler.close();
	});


</script>
</html>