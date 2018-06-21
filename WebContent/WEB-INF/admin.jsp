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

table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
    text-align: left;
}

@media only screen and (min-width: 845px) {
    /* For tablets: */
    #calendarHereTest {
    position: relative; height: 250px; margin-left: 40%;
    }
    
    #calendarHereTooTest {
    position: relative; height: 250px; margin-left: 0%;
    }
    
}

@media only screen and (max-width: 845px) {
    /* For tablets: */
    #calendarHereTest {
    position: relative; height: 250px; margin-left: 20%;
    }
    
    #calendarHereTooTest {
    position: relative; height: 250px; margin-left: 20%;
    }
    
}

</style>
<title>Admin Calendar</title>
</head>
<body style="padding-top: 10px;">

<div class="w3-content w3-container" style="border: 1px solid grey; border-radius: 10px; padding-top: 10px; max-width: 1500px;">
<h1 style="text-align: center">Admin Calendar</h1>
<hr>
<p style="text-align: center">Click on a day to set the price.</p>
 <div id="calendarHere" style="position:relative;height:250px; margin-left: 41%; display: inline-block; padding-top: 10px;"></div>
 <h3 id="price" style="text-align: center; padding-top: 10px;"> hover over day to see price</h1>
 <div class="w3-container w3-row">
 <div class="w3-container w3-half">
 <form name="sendArrForm" action="Pricing" method="post" style="text-align: center; padding-top: 40px;">
 	<input type="hidden" name="hiddenArrayField" id="hiddenArrayField"/>
 	<input type="submit" onclick="formatforServer(arrToServer)" value="SAVE CHANGES" class="w3-button w3-round w3-green">
 </form>
 </div>
 <div class="w3-container w3-half">
 <form name="exportCal" action="ExportCal" method="get" style="text-align: center; padding-top: 40px;">
 	<p>Calendar url: <br>
 	https://s3-us-west-2.amazonaws.com/rorcal/bookings.ics?gfix=666</p>
 	<input type="submit" value="Manual Refresh" class="w3-button w3-round w3-blue">
 </form>
 </div>
 </div>
 <br>
 
 <div class="w3-content w3-container-w3-center" style="margin-left: 60%">
 <h3>Import external iCal file</h3>
 <form action="ImportCal" method="post" enctype="multipart/form-data">
    <input type="text" name="description"/>
    <input type="file" name="file" />
    <input type="submit" />
</form>
</div>
<br>
<hr>


<div class="w3-content w3-container w3-center">
<h1 style="text-align: center">Booking Requests</h1><hr>

	<div style="padding-top: 10px;" id="bookingReqs"></div>
</div>

<div class="w3-content w3-container w3-center">
<h1 style="text-align: center">Inquiries</h1><hr>

	<div style="padding-top: 10px;" id="inquiries"></div>
</div>


<div class="w3-content w3-container w3-center">
<h1 style="text-align: center">Hold dates</h1><hr>
<div id="calendarHereThree" style="position:relative;height:350px;margin-left: 22%"></div>

	<form name="sendToHold" action="HoldDates" method="post">
		<input type="hidden" name="hiddenStartDate" id="hiddenStartDate"/>
		<input type="hidden" name="hiddenEndDate" id="hiddenEndDate"/>
		<input type="submit"  id="next" class="w3-button w3-round-large w3-green" onclick="insertDates()" value="Hold">
	</form>
	
	<div style="padding-top: 10px;" id="heldDates"></div>
</div>
 
 <hr>
 <h1 style="text-align: center">Promo Codes</h1><hr>
 <form name="promoCodeEntry" action="GeneratePromo" method="post">
 	Code: <input type="text" name="promoCode"/>
 	Percent discount: <input type="text" name="percentOff"/> %
 	Members Only? <input type="checkbox" name="mo"/>
 	<input type="submit" class="w3-button w3-round-large w3-green" value="Generate"/>
 </form>
 <hr>
 <h1 style="text-align: center">Email List</h1><hr>
 <p  style= "text-align: center"id="maillist"></p>
 <hr>
 <h1 style="text-align: center">Bookings</h1><hr>
 <div id="bookingtable"></div>
 <hr>
 <div style="text-align: center;">
 <button class="w3-button w3-round w3-blue w3-center" onclick="showCancels()">View Cancelled Trips</button>
 </div>
 <br>
 <div id="showCancel">
 <div id="cancelTable"></div>
 </div>
 <br><br>
 </div>
 <br><br><br>
 
 <br>
 
 <br><br><br><br>
 <form name="cancelFulfill" id="cancelFulfill" action="FulfillCancellation" method="post">
 <input type="hidden" name="confirmationId" id="confirmationId">
 <input type="hidden" name="email" id="email">
 </form>
 
 <form name="undo" id="undo" action="UndoCancel" method="post">
 <input type="hidden" name="confirmationIdUndo" id="confirmationIdUndo">
 <input type="hidden" name="emailUndo" id="emailUndo">
 </form>
 
  <form name="unhold" id="unhold" action="UndoHold" method="post">
 <input type="hidden" name="startUnhold" id="startUnhold">
 <input type="hidden" name="endUnhold" id="endUnhold">
 </form>
 
  <form name="accept" id="accept" action="AcceptRequest" method="post">
 <input type="hidden" name="startAccept" id="startAccept">
 <input type="hidden" name="endAccept" id="endAccept">
 </form>
 
  <form name="deny" id="deny" action="DenyRequest" method="post">
 <input type="hidden" name="startDeny" id="startDeny">
 <input type="hidden" name="endDeny" id="endDeny">
 <input type="hidden" name="reasonDeny" id="reasonDeny">
 </form>
 
 <form name="updatePrices" id="updatePrices" action="UpdatePrices" method="post">
 <input type="hidden" name="startChange" id="startChange">
 <input type="hidden" name="endChange" id="endChange">
 <input type="hidden" name="priceChange" id="priceChange">
 </form>
 
 <!-- CHECKOUT TESTING -->
 
 <div class="w3-row">
		
  			<div class = "w3-container w3-center w3-half" style="margin-bottom: 40px;"> <span id="checkki" style="margin-left: 38%;">Check in</span><div id="calendarHereTest"></div></div>
   			<div class = "w3-container w3-center w3-half" style="margin-bottom: 40px;"> <span id="checkko" style="margin-right: 40%;">Check out</span><div id="calendarHereTooTest"></div></div>
   			
   		</div>
   		
   		
	
   		
<div class="w3-row">
		<div class="w3-container w3-center w3-third">
		   <a href="#home" onclick="dimBeforeToday()" class="w3-button w3-round w3-light-gray" style="text-decoration: none; margin-top: 25px;">Clear trip</a>
		</div>
		
		
		<div class="w3-container w3-center w3-third">
		
			<p id="estimatedprice" style="text-align: center"><i class="fa fa-tag" style="float: left;"></i>Estimated price: -</p><br>
		
		</div>
		 
		
		<div class="w3-container w3-center w3-third">
			<form name="sendToBooks" action="RequestBooking" method="post" style="padding-top: 25px;">
				<input type="hidden" name="hiddenStartDateTwo" id="hiddenStartDateTest"/>
				<input type="hidden" name="hiddenEndDateTwo" id="hiddenEndDateTest"/>
			    <input type="submit"  id="next" class="w3-button w3-round-large w3-green" onclick="insertDatesTwo()" value="Reserve">
			</form>
		</div>
</div>

<div class="w3-container w3-center">
	<form name="Inquire" action="SendInquiry" method="post" style="padding: 25px;">
		<input type="hidden" name="hiddenStartDateThree" id="hiddenStartDateThree"/>
		<input type="hidden" name="hiddenEndDateThree" id="hiddenEndDateThree"/>
		<input type="button" class ="w3-button w3-blue w3-round" style="margin-bottom: 10px;" onclick="showText()" value="Inquire about these dates"/>
		<input type="email" style="display: none; margin: auto; margin-bottom: 10px;" name="email" id="emailV" value="Enter email"/>
		<input type="text" style="display: none; margin: auto; margin-bottom: 10px;" size="60" name ="message" id="message" value="Enter message"/>
		<input type="submit" style="display: none;" id="sendIn" class="w3-button w3-round-large w3-green" onclick="insertDatesThree()" value="Send inquiry">		
	</form>
</div>

<script>
var myCalendar;
var priceArr = new Array();
var arrToServer;
var arrayFromServer = new Array();

function showText(){
	document.getElementById("message").style.display = "block";
	document.getElementById("emailV").style.display = "block";
	document.getElementById("sendIn").style.display = "inline-block";
}

function insertData(email, confId){
	document.getElementById("confirmationId").value = confId;
	document.getElementById("email").value = email;
	document.getElementById("cancelFulfill").submit();
}

function undoData(email, confId){
	document.getElementById("confirmationIdUndo").value = confId;
	document.getElementById("emailUndo").value = email;
	document.getElementById("undo").submit();
}

function undoHold(start, end){
	document.getElementById("startUnhold").value = start;
	document.getElementById("endUnhold").value = end;
	document.getElementById("unhold").submit();
}

function acceptB(start, end){
	document.getElementById("startAccept").value = start;
	document.getElementById("endAccept").value = end;
	document.getElementById("accept").submit();
}

function denyB(start, end){
	document.getElementById("startDeny").value = start;
	document.getElementById("endDeny").value = end;
	document.getElementById("reasonDeny").value = document.getElementById("reason").value;
	document.getElementById("deny").submit();
}

//get pricelist sent from server
var priceList = '${list}';
//console.log("js list: " + priceList);
var emails = '${emails}';
var jsemails = JSON.parse(emails);

var dates = '${dates}';
var datesFromServer = JSON.parse(dates);

var cancelDates = '${cancelDates}';
var cancelsFromServer = JSON.parse(cancelDates);

var cancelledDates = '${cancelledDates}';
var cancelledFromServer = JSON.parse(cancelledDates);

var heldDates = '${heldDates}';
var heldFromServer = JSON.parse(heldDates);

var bookingReqs = '${bookingReqs}';
var bookingReqsFromServer = JSON.parse(bookingReqs);

var inqList = '${inquiries}';
var inqsFromServer = JSON.parse(inqList);


var heldTable = "<table align=\"center\">";
var reqTable = "<table align=\"center\">";
var inqTable = "<table align=\"center\">";

for(var i = 0; i < heldFromServer.length; i+=2){
	heldTable += ("<tr>" + "<td>" + heldFromServer[i] +"</td>"
			+ "<td>" + heldFromServer[i+1] + "</td>" + "<td>" +
			"<button id=\"unholdB\" class=\"w3-button w3-round-large w3-red\" " +
		    "onclick=\"undoHold(\'" + heldFromServer[i] + "\',\'" + heldFromServer[i+1] + "\')\" value=\"Release\">Release</button>"
		    + "</td>"
			+ "</tr>");
	
}
heldTable += "</table>";
document.getElementById("heldDates").innerHTML = heldTable;
//

for(var i = 0; i < bookingReqsFromServer.length; i+=4){
	reqTable += ("<tr>" + "<td>" + bookingReqsFromServer[i] +"</td>"
			+ "<td>" + bookingReqsFromServer[i+1] + "</td>" 
			+ "<td>" + bookingReqsFromServer[i+2] + "</td>"
			+ "<td>$" + bookingReqsFromServer[i+3] + "</td>" 
			+ "<td>" +
			"<button id=\"acceptB\" class=\"w3-button w3-round-large w3-green\" " +
		    "onclick=\"acceptB(\'" + bookingReqsFromServer[i+1] + "\',\'" + bookingReqsFromServer[i+2] + "\')\" value=\"Accept\">Accept</button>"
		   //
		    
		    + "<button id=\"changeP\" class=\"w3-button w3-round-large w3-blue\" " +
		    "onclick=\"changeP(\'" + bookingReqsFromServer[i+1] + "\',\'" + bookingReqsFromServer[i+2] + "\')\" value=\"Change Price\">Change Price</button>"
		    
		    //
		    + "<button id=\"denyB\" class=\"w3-button w3-round-large w3-red\" " +
		    "onclick=\"denyB(\'" + bookingReqsFromServer[i+1] + "\',\'" + bookingReqsFromServer[i+2] + "\')\" value=\"Deny\">Deny</button>" +
		    "<br><textarea rows=\"4\" cols=\"50\" id=\"reason\">Reason for Denying...</textarea>"
		    + "</td>"
			+ "</tr>");
	
}
reqTable += "</table>";

document.getElementById("bookingReqs").innerHTML = reqTable;

for(var q = 0; q < inqsFromServer.length; q+=4){
	inqTable += ("<tr>" + "<td>" + inqsFromServer[q] +"</td>"
			+ "<td>" + inqsFromServer[q+1] + "</td>" 
			+ "<td>" + inqsFromServer[q+2] + "</td>"
			+ "<td>" + inqsFromServer[q+3] + "</td>"
			+ "</tr>");
}

inqTable += "</table>";
document.getElementById("inquiries").innerHTML = inqTable;

for(var i = 0; i < jsemails.length; i++){
	document.getElementById("maillist").innerHTML += (jsemails[i] + "<br>");
}

function showCancels(){
	var myTable = "<table align=\"center\">";
	for(var i = 0; i < cancelledFromServer.length; i+=7){
		 email = cancelledFromServer[i+5];
		 confId = cancelledFromServer[i+6];
		myTable += ("<tr>" + "<td>" + cancelledFromServer[i] + "</td>"
			    + "<td>" + cancelledFromServer[i + 1] +  "</td>"
			    + "<td>" + cancelledFromServer[i + 2] + "</td>"
			    + "<td>" + cancelledFromServer[i + 3] + "</td>"
			    + "<td>" + cancelledFromServer[i + 4] + "</td>"
			    + "<td>" + cancelledFromServer[i + 5] + "</td>"
			    + "<td>" + cancelledFromServer[i + 6] + "<button id=\"undoB\" class=\"w3-button w3-round-large w3-red\" " +
    		    "onclick=\"undoData(\'" + email + "\',\'" + confId + "\')\" value=\"Undo\">Undo</button>" + 
    		    "</td>");
	
	}
	myTable += "</table";
	document.getElementById("cancelTable").innerHTML = myTable;
	
}


//format pricelist sent from server
var arrayFromServer = JSON.parse(priceList);
console.log(arrayFromServer);

//add pricelist sent from server to local priceArr
for(var i = 0; i < arrayFromServer.length; i += 2){
	var date = new Date((arrayFromServer[i] + "T000:00:00").replace(/-/g, '\/').replace(/T.+/, ''));
	console.log("date # " + i + ": " + date);
	var price = arrayFromServer[i + 1];
	var thisDay = new DayPrice(date, price);
	priceArr.push(thisDay);
}


//console.log("priceFromServer: " + priceArr[4].date + " and " + priceArr[4].price);


function DayPrice(date, price){
	this.date = date;
	this.newDate = date.toISOString().slice(0, 19).replace('T', ' ');
	this.price = price;
}

//display calendar to set prices
myCalendar = new dhtmlXCalendarObject("calendarHere");
myCalendar.hideTime();
myCalendar.show();



//show price on hover
myCalendar.attachEvent("onMouseOver", function(date, ev){
    for(var i = 0; i < priceArr.length; i++){
    	if(priceArr[i].date.getTime() == date.getTime()){
    		document.getElementById("price").innerHTML = priceArr[i].date.toISOString().slice(0, 19).replace('T', ' ').slice(0, -8) + ": $" + priceArr[i].price;
    	}
    }
    
});
var startDate;
var endDate;

var myDoubleCalendar;

myDoubleCalendar = new dhtmlXDoubleCalendar("calendarHereThree");

myDoubleCalendar.show();

myDoubleCalendar.leftCalendar.attachEvent("onClick", function(date){
	startDate = date;
});

myDoubleCalendar.rightCalendar.attachEvent("onClick", function(date){
	endDate = date;
});

function insertDates(){
	document.getElementById("hiddenStartDate").value = startDate;
	document.getElementById("hiddenEndDate").value = endDate;
}

function changeP(startDate, endDate){
	var price = prompt("Please enter the new price in dollars (no cents):");
	sendPriceChange(startDate, endDate, price);
}

function sendPriceChange(startDate, endDate, price){
	document.getElementById("startChange").value = startDate;
	document.getElementById("endChange").value = endDate;
	document.getElementById("priceChange").value = price;
	document.getElementById("updatePrices").submit();
}


//get input for price when a day is clicked and push to priceArr
myCalendar.attachEvent("onClick", function(date){
	var price = prompt("Please enter the price for this day:");
	var replaced = false;
	//check to see if price for this day already exists and is being replaced
	for(var i = 0; i < priceArr.length; i++){
		priceArr[i].date.setHours(0,0,0,0);
		date.setHours(0,0,0,0);
		if(priceArr[i].date.getTime() == date.getTime()){
			var thisDay = new DayPrice(date,price);
			priceArr[i] = thisDay;
			arrToServer = JSON.stringify(priceArr);
			document.getElementById("hiddenArrayField").value=arrToServer;
			replaced = true;
		}
	}
	if(!replaced){
		var thisDay = new DayPrice(date, price);
		priceArr.push(thisDay);
		console.log(priceArr);
		arrToServer = JSON.stringify(priceArr);
		document.getElementById("hiddenArrayField").value=arrToServer;
		//alert(arrToServer);
	}
});

//print out booking table
var myTable = "<table align=\"center\"><tr><th>Check In</th><th>Check Out</th><th>First Name</th><th>Last Name</th><th>Phone</th><th>Email</th><th>Confirmation Id</th></tr>";
var email;
var confId;
for(var i = 0; i < datesFromServer.length; i+=7){
	var canceled = false;
	//check to see if info is in dates and cancel_req -- if so, make row RED
	for(var j = 0; j < cancelsFromServer.length; j+=2){
		if(datesFromServer[i + 6] == cancelsFromServer[j + 1] &&
		   datesFromServer[i + 5] == cancelsFromServer[j]
		) {
		   canceled = true;	
		   email = cancelsFromServer[j];
		   confId = cancelsFromServer[j+1];
		}
	}
	    if(canceled == false){
			myTable += ("<tr>" + "<td>" + datesFromServer[i] + "</td>"
		    + "<td>" + datesFromServer[i + 1] +  "</td>"
		    + "<td>" + datesFromServer[i + 2] + "</td>"
		    + "<td>" + datesFromServer[i + 3] + "</td>"
		    + "<td>" + datesFromServer[i + 4] + "</td>"
		    + "<td>" + datesFromServer[i + 5] + "</td>"
		    + "<td>" + datesFromServer[i + 6] + "</td>"
		    + "</tr>");
	    }
	    if(canceled == true){
	    	myTable += ("<tr style=\"color: red;\">" + "<td>" + datesFromServer[i] + "</td>"
	    		    + "<td>" + datesFromServer[i + 1] +  "</td>"
	    		    + "<td>" + datesFromServer[i + 2] + "</td>"
	    		    + "<td>" + datesFromServer[i + 3] + "</td>"
	    		    + "<td>" + datesFromServer[i + 4] + "</td>"
	    		    + "<td>" + datesFromServer[i + 5] + "</td>"
	    		    + "<td>" + datesFromServer[i + 6] + "<button id=\"cancelB\" class=\"w3-button w3-round-large w3-red\" " +
	    		    "onclick=\"insertData(\'" + email + "\',\'" + confId + "\')\" value=\"Cancel\">Cancel</button>" + 
	    		    "</td>"
	    		    + "</tr>");
	    }
	
}
myTable += "</table";


document.getElementById("bookingtable").innerHTML = myTable;

//FOR TESTING CHECKOUT
var myCalendarTest;
	var myNextCalendarTest;
	var startDateTwo;
	var endDateTwo;
	var dayPrice = {date: new Date(2018,0,22), price: 500};
	var today = new Date();
	var priceArr = new Array();
	var yesterday = new Date(today.getTime());
	yesterday.setDate(today.getDate() - 1);
	
	//insert calendars
	myCalendarTest = new dhtmlXCalendarObject("calendarHereTest");
	myNextCalendarTest = new dhtmlXCalendarObject("calendarHereTooTest");
	myCalendarTest.hideTime();
	myNextCalendarTest.hideTime();
	myCalendarTest.show();
	myNextCalendarTest.show();
	today = myCalendarTest.getDate(true);
	dimBeforeToday();
	startDateTwo=myCalendarTest.getDate();
	
	//make days before today unclickable
	function dimBeforeToday() {
		myCalendarTest.setInsensitiveRange(null, yesterday);
		myNextCalendarTest.setInsensitiveRange(null, yesterday);
		
		myCalendarTest.setHolidays(null);
		myNextCalendarTest.setHolidays(null);
		//document.getElementById("estimatedprice").innerHTML = "Estimated price: -";
		document.getElementById("next").disabled = true;
	}
	
	document.getElementById("next").disabled = true;
	
	//function to get all days between two days
	function getDateArray(start, end) {
	    var arr = new Array();
	    var dt = new Date(start);
	    
	    while (dt <= end) {
	        arr.push(new Date(dt));
	        dt.setDate(dt.getDate() + 1);
	    }
	    return arr;
	}
	
	//capture start date	
	var getStartDate = myCalendarTest.attachEvent("onClick", function(){
		startDateTwo = myCalendarTest.getDate();
	});
	
	//capture end date and highlight trip and enable next button if selected trip is longer than three days
	var getEndDate = myNextCalendarTest.attachEvent("onClick", function(){
	    endDateTwo = myNextCalendarTest.getDate();
	    var outOfBounds = false;
	    //make sure end date is before next insensitive date
	    
	    //make sure three day minimum
	    var _MS_PER_DAY = 1000 * 60 * 60 * 24;
	     if(Math.floor((endDate - startDate) / _MS_PER_DAY) < 2){
	    	 outOfBounds = true;
	     }
	    
	    if(startDate!=null && outOfBounds == false){
	    	dimBeforeToday();
	    	document.getElementById("next").disabled = false;
	    	myCalendarTest.setHolidays(getDateArray(startDate, endDate));
	    	myNextCalendarTest.setHolidays(getDateArray(startDate, endDate));
	    }
	    //document.getElementById("estimatedprice").innerHTML = "Estimated price: " + calcPayment();
    });
	
	function dimDates() {
		myCalendarTest.setInsensitiveRange(startDateTwo,endDateTwo);
		myNextCalendarTest.setInsensitiveRange(startDate, endDateTwo);
	}
	
	function insertDatesTwo(){
		document.getElementById("hiddenStartDateTest").value = startDateTwo;
		document.getElementById("hiddenEndDateTest").value = endDateTwo;
	}

	function insertDatesThree(){
		document.getElementById("hiddenStartDateThree").value = startDateTwo;
		document.getElementById("hiddenEndDateThree").value = endDateTwo;
	}
</script>
</body>
</html>