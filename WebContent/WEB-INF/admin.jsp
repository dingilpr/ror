<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Poppins:500">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="codebase/dhtmlxcalendar.css"
	type="text/css">
<script src="codebase/dhtmlxcalendar.js" type="text/javascript"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style>
body, h1, h2, h3, h4, h5, h6 {
	font-family: "Poppins", sans-serif;
}

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
	 border: 1px solid #ddd;
    padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2;}
tr:hover {background-color: #ddd;}

th {
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: left;
    background-color: #4CAF50;
    color: white;
}


#customers {
    border-collapse: collapse;
    width: 100%;
}

#customers td, #customers th {
    border: 1px solid #ddd;
    padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}

#customers tr:hover {background-color: #ddd;}

#customers th {
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: left;
    background-color: #4CAF50;
    color: white;
}

@media only screen and (min-width: 845px) {
	/* For tablets: */
	#calendarHereTest {
		position: relative;
		height: 250px;
		margin-left: 40%;
	}
	#calendarHereTooTest {
		position: relative;
		height: 250px;
		margin-left: 0%;
	}
}

@media only screen and (max-width: 845px) {
	/* For tablets: */
	#calendarHereTest {
		position: relative;
		height: 250px;
		margin-left: 20%;
	}
	#calendarHereTooTest {
		position: relative;
		height: 250px;
		margin-left: 20%;
	}
}
</style>
<title>Admin Calendar</title>
</head>
<body style="padding-top: 10px;">

	<div class="w3-content w3-container"
		style="border: 1px solid grey; border-radius: 10px; padding-top: 10px; max-width: 1500px;">
		<h1 style="text-align: center">Admin Calendar</h1>
		<hr>
		<p style="text-align: center">Click on a day to set the price.</p>
		<div id="calendarHere"
			style="position: relative; height: 250px; margin-left: 41%; display: inline-block; padding-top: 10px;"></div>
		<h3 id="price" style="text-align: center; padding-top: 10px;">
			hover over day to see price
			</h1>
			<div class="w3-container w3-row">
				<div class="w3-container w3-half">
					<form name="sendArrForm" action="Pricing" method="post"
						style="text-align: center; padding-top: 40px;">
						<input type="hidden" name="hiddenArrayField" id="hiddenArrayField" />
						<input type="submit" onclick="formatforServer(arrToServer)"
							value="SAVE CHANGES" class="w3-button w3-round w3-green">
					</form>
				</div>
				<div class="w3-container w3-half">
					<form name="exportCal" action="ExportCal" method="get"
						style="text-align: center; padding-top: 40px;">
						<p>
							Calendar url: <br>
							https://s3-us-west-2.amazonaws.com/rorcal/bookings.ics?gfix=666
						</p>
						<input type="submit" value="Manual Refresh"
							class="w3-button w3-round w3-blue">
					</form>
				</div>
			</div>
			<br>

			<div class="w3-container w3-half">
				<p>Set price by month</p>
				<form action="BulkPrice" method="post">
					<input type="month" name="month" /> <input type="text"
						name="amount" value="Amount in dollars(no cents)" size="50" /> <input
						type="submit" value="Submit Price" />
				</form>
			</div>

			<div class="w3-container w3-half">
				<h3 style="margin-left: 25%;">Import external iCal file</h3>
				<form action="ImportCal" method="post" enctype="multipart/form-data"
					style="margin-left: 25%;">
					<input type="text" name="description" /> <input type="file"
						name="file" /> <input type="submit" />
				</form>
			</div>
			<br>

			<div class="w3-container w3-center">
				<h1 style="text-align: center">
					Prices <span style="float: right;"><i
						class="fa fa-sort-down" id="changeClassP" onclick="showPrices()"></i></span>
				</h1>
				<hr>
				
					<select onchange="datesHideFunc()"id="mySelect" size="8" style="display: none; margin-left: 44%;">
						
					</select>
				
				<div style="padding-top: 10px; display: none" id="priceL"></div>
			</div>


			<div class="w3-container w3-center">
				<h1 style="text-align: center">
					Booking Requests <span style="float: right;"><i
						class="fa fa-sort-down" id="changeClassB" onclick="showReqs()"></i></span>
				</h1>
				<hr>

				<div style="padding-top: 10px; display: none" id="bookingReqs"></div>
			</div>

			<div class="w3-container w3-center">
				<h1 style="text-align: center">
					Inquiries<span style="float: right;"><i
						class="fa fa-sort-down" id="changeClass" onclick="showInqs()"></i></span>
				</h1>
				<hr>

				<div style="padding-top: 10px; display: none" id="inquiries"></div>
			</div>


			<div class="w3-container w3-center">
				<h1 style="text-align: center">
					Hold dates<span style="float: right;"><i
						class="fa fa-sort-down" id="changeClassF" onclick="showHold()"></i></span>
				</h1>
				<hr>
				<div id="holdHS" style="visibility: hidden;">
					<div id="calendarHereThree"
						style="position: relative; height: 0px; margin-left: 32%"></div>

					<form name="sendToHold" action="HoldDates" method="post">
						<input type="hidden" name="hiddenStartDate" id="hiddenStartDate" />
						<input type="hidden" name="hiddenEndDate" id="hiddenEndDate" /> <input
							type="submit" id="next" class="w3-button w3-round-large w3-green"
							onclick="insertDates()" value="Hold">
					</form>

					<div style="padding-top: 10px;" id="heldDates"></div>
				</div>
			</div>

			<hr>
			<h1 style="text-align: center">
				Promo Codes<span style="float: right;"><i
					class="fa fa-sort-down" id="changeClassE" onclick="showProm()"></i></span>
			</h1>
			<hr>
			<div id="promoHS" style="display: none">
				<form name="promoCodeEntry" action="GeneratePromo" method="post"
					style="text-align: center; padding: 5px;">
					Code: <input type="text" name="promoCode" /> Percent discount: <input
						type="text" name="percentOff" /> % Members Only? <input
						type="checkbox" name="mo" /> <input type="submit"
						class="w3-button w3-round-large w3-green" value="Generate" />
				</form>
				<br>
				<div id="promotable" style="display: none;"></div>
			</div>
			<hr>
			<h1 style="text-align: center">
				Email List<span style="float: right;"><i
					class="fa fa-sort-down" id="changeClassD" onclick="showMail()"></i></span>
			</h1>
			<hr>
			<p style="text-align: center; display: none;" id="maillist"></p>
			<hr>
			<h1 style="text-align: center">
				Bookings<span style="float: right;"><i
					class="fa fa-sort-down" id="changeClassC" onclick="showBooks()"></i></span>
			</h1>
			<hr>
			<div id="bookingtable" style="display: none;"></div>
			<hr>
			<div style="text-align: center;">
				<button class="w3-button w3-round w3-blue w3-center"
					onclick="showCancels()">View Cancelled Trips</button>
			</div>
			<br>
			<div id="showCancel">
				<div id="cancelTable"></div>
			</div>
			<br>
			<br>
	</div>
	<br>
	<br>
	<br>

	<br>

	<br>
	<br>
	<br>
	<br>
	<form name="cancelFulfill" id="cancelFulfill"
		action="FulfillCancellation" method="post">
		<input type="hidden" name="confirmationId" id="confirmationId">
		<input type="hidden" name="email" id="email">
	</form>

	<form name="undo" id="undo" action="UndoCancel" method="post">
		<input type="hidden" name="confirmationIdUndo" id="confirmationIdUndo">
		<input type="hidden" name="emailUndo" id="emailUndo">
	</form>
	
	<form name="refundRD" id="refundRD" action="RefundDeposit" method="post">
		<input type="hidden" name="confirmationRD" id="confirmationRD">
		<input type="hidden" name="emailRD" id="emailRD">
	</form>

	<form name="unhold" id="unhold" action="UndoHold" method="post">
		<input type="hidden" name="startUnhold" id="startUnhold"> <input
			type="hidden" name="endUnhold" id="endUnhold">
	</form>

	<form name="accept" id="accept" action="AcceptRequest" method="post">
		<input type="hidden" name="startAccept" id="startAccept"> <input
			type="hidden" name="endAccept" id="endAccept">

	</form>

	<form name="deny" id="deny" action="DenyRequest" method="post">
		<input type="hidden" name="startDeny" id="startDeny"> <input
			type="hidden" name="endDeny" id="endDeny"> <input
			type="hidden" name="reasonDeny" id="reasonDeny">

	</form>
	
	<form name="inqR" id="inqR" action="InquiryResponse" method="post">
		<input type="hidden" name="startInq" id="startInq"> <input
			type="hidden" name="endInq" id="endInq"> <input
			type="hidden" name="responseInq" id="responseInq">
	</form>

	<form name="updatePrices" id="updatePrices" action="UpdatePrices"
		method="post">
		<input type="hidden" name="startChange" id="startChange"> <input
			type="hidden" name="endChange" id="endChange"> <input
			type="hidden" name="priceChange" id="priceChange">
	</form>

	<form name="deletePromo" id="deletePromo" action="DeletePromo"
		method="post">
		<input type="hidden" name="pName" id="pName"> <input
			type="hidden" name="pPercent" id="pPercent">
	</form>

	<!-- CHECKOUT TESTING -->

	<div class="w3-row">

		<div class="w3-container w3-center w3-half"
			style="margin-bottom: 40px;">
			<span id="checkki" style="margin-left: 38%;">Check in</span>
			<div id="calendarHereTest"></div>
		</div>
		<div class="w3-container w3-center w3-half"
			style="margin-bottom: 40px;">
			<span id="checkko" style="margin-right: 40%;">Check out</span>
			<div id="calendarHereTooTest"></div>
		</div>

	</div>




	<div class="w3-row">
		<div class="w3-container w3-center w3-third">
			<a href="#home" onclick="dimBeforeToday()"
				class="w3-button w3-round w3-light-gray"
				style="text-decoration: none; margin-top: 25px;">Clear trip</a>
		</div>


		<div class="w3-container w3-center w3-third">

			<p id="estimatedprice" style="text-align: center">
				<i class="fa fa-tag" style="float: left;"></i>Estimated price: -
			</p>
			<br>

		</div>


		<div class="w3-container w3-center w3-third">
			<form name="sendToBooks" action="RequestBooking" method="post"
				style="padding-top: 25px;">
				<input type="hidden" name="hiddenStartDateTwo"
					id="hiddenStartDateTest" /> <input type="hidden"
					name="hiddenEndDateTwo" id="hiddenEndDateTest" /> <input
					type="submit" id="next" class="w3-button w3-round-large w3-green"
					onclick="insertDatesTwo()" value="Reserve">
			</form>
		</div>
	</div>

	<div class="w3-container w3-center">
		<form name="Inquire" action="InquireRedirect" id="inqForm" method="post"
			style="padding: 25px;">
			<input type="hidden" name="hiddenStartDateThree"
				id="hiddenStartDateThree" /> <input type="hidden"
				name="hiddenEndDateThree" id="hiddenEndDateThree" /> <input
				type="button" class="w3-button w3-blue w3-round"
				style="margin-bottom: 10px;" onclick="insertDatesThree()"
				value="Inquire about these dates" /> 
		</form>
	</div>

	<script>
		var myCalendar;
		var priceArr = new Array();
		var arrToServer;
		var arrayFromServer = new Array();

		function showInqs() {
			document.getElementById("inquiries").style.display = "block";
			document.getElementById("changeClass").className = "fa fa-sort-up";
			document.getElementById("changeClass").onclick = hideInqs;
		}

		function hideInqs() {
			document.getElementById("inquiries").style.display = "none";
			document.getElementById("changeClass").className = "fa fa-sort-down";
			document.getElementById("changeClass").onclick = showInqs;
		}

		function showReqs() {
			document.getElementById("bookingReqs").style.display = "block";
			document.getElementById("changeClassB").className = "fa fa-sort-up";
			document.getElementById("changeClassB").onclick = hideReqs;
		}

		function hideReqs() {
			document.getElementById("bookingReqs").style.display = "none";
			document.getElementById("changeClassB").className = "fa fa-sort-down";
			document.getElementById("changeClassB").onclick = showReqs;
		}

		function showBooks() {
			document.getElementById("bookingtable").style.display = "block";
			document.getElementById("changeClassC").className = "fa fa-sort-up";
			document.getElementById("changeClassC").onclick = hideBooks;
		}

		function hideBooks() {
			document.getElementById("bookingtable").style.display = "none";
			document.getElementById("changeClassC").className = "fa fa-sort-down";
			document.getElementById("changeClassC").onclick = showBooks;
		}

		function showMail() {
			document.getElementById("maillist").style.display = "block";
			document.getElementById("changeClassD").className = "fa fa-sort-up";
			document.getElementById("changeClassD").onclick = hideMail;
		}

		function hideMail() {
			document.getElementById("maillist").style.display = "none";
			document.getElementById("changeClassD").className = "fa fa-sort-down";
			document.getElementById("changeClassD").onclick = showMail;
		}

		function showProm() {
			document.getElementById("promoHS").style.display = "block";
			document.getElementById("changeClassE").className = "fa fa-sort-up";
			document.getElementById("changeClassE").onclick = hideProm;
			document.getElementById("promotable").style.display = "block";
		}

		function hideProm() {
			document.getElementById("promoHS").style.display = "none";
			document.getElementById("changeClassE").className = "fa fa-sort-down";
			document.getElementById("changeClassE").onclick = showProm;
		}

		function showHold() {
			document.getElementById("holdHS").style.visibility = "visible";
			document.getElementById("changeClassF").className = "fa fa-sort-up";
			document.getElementById("changeClassF").onclick = hideHold;
			myDoubleCalendar.show();
			document.getElementById("calendarHereThree").style.height = "350px";
		}

		function hideHold() {
			document.getElementById("holdHS").style.visibility = "hidden";
			document.getElementById("changeClassF").className = "fa fa-sort-down";
			document.getElementById("changeClassF").onclick = showHold;
			myDoubleCalendar.hide();
			document.getElementById("calendarHereThree").style.height = "0px";
		}

		function showPrices() {
			document.getElementById("priceL").style.display = "block";
			document.getElementById("mySelect").style.display = "block";
			document.getElementById("changeClassP").className = "fa fa-sort-up";
			document.getElementById("changeClassP").onclick = hidePrices;

		}

		function hidePrices() {
			document.getElementById("priceL").style.display = "none";
			document.getElementById("mySelect").style.display = "none";
			document.getElementById("changeClassP").className = "fa fa-sort-down";
			document.getElementById("changeClassP").onclick = showPrices;
		}

		function showText() {
			document.getElementById("message").style.display = "block";
			document.getElementById("emailV").style.display = "block";
			document.getElementById("sendIn").style.display = "inline-block";
		}

		function insertData(email, confId) {
			document.getElementById("confirmationId").value = confId;
			document.getElementById("email").value = email;
			document.getElementById("cancelFulfill").submit();
		}
		
		function releaseDeposit(email, confId) {
			document.getElementById("confirmationRD").value = confId;
			document.getElementById("emailRD").value = email;
			document.getElementById("refundRD").submit();
		}

		function undoData(email, confId) {
			document.getElementById("confirmationIdUndo").value = confId;
			document.getElementById("emailUndo").value = email;
			document.getElementById("undo").submit();
		}

		function undoHold(start, end) {
			document.getElementById("startUnhold").value = start;
			document.getElementById("endUnhold").value = end;
			document.getElementById("unhold").submit();
		}

		function acceptB(start, end) {
			document.getElementById("startAccept").value = start;
			document.getElementById("endAccept").value = end;

			document.getElementById("accept").submit();
		}

		function denyB(start, end) {
			document.getElementById("startDeny").value = start;
			document.getElementById("endDeny").value = end;
			document.getElementById("reasonDeny").value = document
					.getElementById("reason").value;

			document.getElementById("deny").submit();
		}
		

		function acceptIn(start, end) {
			document.getElementById("startInq").value = start;
			document.getElementById("endInq").value = end;
			document.getElementById("responseInq").value = document
					.getElementById("response").value;

			document.getElementById("inqR").submit();
		}

		//get pricelist sent from server
		var priceList = '${list}';
		var priceListFS = JSON.parse(priceList);

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

		var promos = '${promos}';
		var promosFromServer = JSON.parse(promos);

		var months = '${months}';
		var monthsFromServer = JSON.parse(months);
		
		//month dictionary
		var objM = {
   			 
		};
		
		objM["2/2018"] = "March 2018";
		objM["3/2018"] = "April 2018";
		objM["5/2018"] = "June 2018";
		objM["6/2018"] = "July 2018";
		objM["8/2018"] = "September 2018";
		objM["9/2018"] = "October 2018";
		objM["10/2018"] = "November 2018";
		objM["11/2018"] = "December 2018";
		objM["0/2019"] = "January 2019";
		objM["1/2019"] = "February 2019";
		objM["2/2019"] = "March 2019";
		objM["3/2019"] = "April 2019";
		objM["4/2019"] = "May 2019";
		objM["5/2019"] = "June 2019";
		objM["6/2019"] = "July 2019";
		objM["8/2019"] = "September 2019";
		objM["9/2019"] = "October 2019";
		objM["10/2019"] = "November 2019";
		objM["1/2020"] = "February 2020";
		objM["2/2020"] = "March 2020";
		
		for(var propt in objM){
			var x = document.getElementById("mySelect");
		    var option = document.createElement("option");
		    option.text = objM[propt];
		    x.add(option);
		}
		
		function datesHideFunc(){
			var selector = mySelect.options[mySelect.selectedIndex].value;
			var tbl = document.getElementById("dynamicTable");
			for (var i = 0, row; row = tbl.rows[i]; i++) {
				row.style.display = "none";
				for(var propt in objM){
				    if ((selector == objM[propt]) && (row.id == propt)){
				    	row.style.display = "";
				    }
				}
			}
			
		}
		
		var heldTable = "<table align=\"center\">";
		var reqTable = "<table align=\"center\">";
		var inqTable = "<table align=\"center\">";
		var promoTable = "<table align=\"center\">";
		promoTable += "<tr><td><b>Name</b></td><td><b>Discount</b></td><td><b>Members</b></td><td><b>Delete</b></td></tr>"
		var priceTable = "<table id=\"dynamicTable\" align=\"center\">";

		for (var i = 0; i < priceListFS.length; i += 3) {
			priceTable += ("<tr id=\""+ priceListFS[i] + "\">" + "<td>"
					+ priceListFS[i + 1] + "</td>" + "<td>"
					+ priceListFS[i + 2] + "</td>" + "</tr>");
		}
		priceTable += "</table>";
		document.getElementById("priceL").innerHTML = priceTable;

		for (var i = 0; i < heldFromServer.length; i += 2) {
			heldTable += ("<tr>"
					+ "<td>"
					+ heldFromServer[i]
					+ "</td>"
					+ "<td>"
					+ heldFromServer[i + 1]
					+ "</td>"
					+ "<td>"
					+ "<button id=\"unholdB\" class=\"w3-button w3-round-large w3-red\" "
					+ "onclick=\"undoHold(\'" + heldFromServer[i] + "\',\'"
					+ heldFromServer[i + 1]
					+ "\')\" value=\"Release\">Release</button>" + "</td>" + "</tr>");

		}
		heldTable += "</table>";
		document.getElementById("heldDates").innerHTML = heldTable;
		//

		for (var i = 0; i < bookingReqsFromServer.length; i += 4) {
			reqTable += ("<tr>" + "<td>"
					+ bookingReqsFromServer[i]
					+ "</td>"
					+ "<td>"
					+ bookingReqsFromServer[i + 1]
					+ "</td>"
					+ "<td>"
					+ bookingReqsFromServer[i + 2]
					+ "</td>"
					+ "<td>$"
					+ bookingReqsFromServer[i + 3]
					+ "</td>"
					+ "<td>"
					+ "<button id=\"acceptB\" class=\"w3-button w3-round-large w3-green\" "
					+ "onclick=\"acceptB(\'"
					+ bookingReqsFromServer[i + 1]
					+ "\',\'"
					+ bookingReqsFromServer[i + 2]
					+ "\')\" value=\"Accept\">Accept</button>"

					//

					+ "<button id=\"changeP\" class=\"w3-button w3-round-large w3-blue\" "
					+ "onclick=\"changeP(\'"
					+ bookingReqsFromServer[i + 1]
					+ "\',\'"
					+ bookingReqsFromServer[i + 2]
					+ "\')\" value=\"Change Price\">Change Price</button>"

					//
					+ "<button id=\"denyB\" class=\"w3-button w3-round-large w3-red\" "
					+ "onclick=\"denyB(\'"
					+ bookingReqsFromServer[i + 1]
					+ "\',\'"
					+ bookingReqsFromServer[i + 2]
					+ "\')\" value=\"Deny\">Deny</button>"
					+ "<br><textarea rows=\"4\" cols=\"50\" id=\"reason\">Reason for Denying...</textarea>"
					+ "</td>" + "</tr>");

		}
		reqTable += "</table>";

		document.getElementById("bookingReqs").innerHTML = reqTable;

		for (var q = 0; q < inqsFromServer.length; q += 4) {
			inqTable += ("<tr>" + "<td>"
					+ inqsFromServer[i]
					+ "</td>"
					+ "<td>"
					+ inqsFromServer[i + 1]
					+ "</td>"
					+ "<td>"
					+ inqsFromServer[i + 2]
					+ "</td>"
					+ "<td>$"
					+ inqsFromServer[i + 3]
					+ "</td>"
					+ "<td>"
					

					//

					+ "<button id=\"changePTwo\" class=\"w3-button w3-round-large w3-blue\" "
					+ "onclick=\"changeP(\'"
					+ inqsFromServer[i + 1]
					+ "\',\'"
					+ inqsFromServer[i + 2]
					+ "\')\" value=\"Change Price\">Change Price</button>"

					//
					+ "<button id=\"acceptIn\" class=\"w3-button w3-round-large w3-green\" "
					+ "onclick=\"acceptIn(\'"
					+ inqsFromServer[i + 1]
					+ "\',\'"
					+ inqsFromServer[i + 2]
					+ "\')\" value=\"Offer\">Reply with Offer</button>"
					+ "<br><textarea rows=\"4\" cols=\"50\" id=\"response\">Response...</textarea>"
					+ "</td>" + "</tr>");
		}

		inqTable += "</table>";
		document.getElementById("inquiries").innerHTML = inqTable;

		for (var y = 0; y < promosFromServer.length; y += 3) {
			promoTable += ("<tr>"
					+ "<td>"
					+ promosFromServer[y]
					+ "</td>"
					+ "<td>"
					+ promosFromServer[y + 1]
					+ "</td>"
					+ "<td>"
					+ promosFromServer[y + 2]
					+ "</td>"
					+ "<td>"
					+ "<button id=\"deleteP\" class=\"w3-button w3-round-large w3-red\" "
					+ "onclick=\"deletePromoFunc(\'" + promosFromServer[y]
					+ "\',\'" + promosFromServer[y + 1]
					+ "\')\" value=\"Delete\">Delete</button></td>" + "</tr>");
		}

		promoTable += "</table>";
		document.getElementById("promotable").innerHTML = promoTable;

		function deletePromoFunc(name, discount) {
			document.getElementById("pName").value = name;
			document.getElementById("pPercent").value = discount;
			document.getElementById("deletePromo").submit();
		}

		for (var i = 0; i < jsemails.length; i++) {
			document.getElementById("maillist").innerHTML += (jsemails[i] + "<br>");
		}

		function showCancels() {
			var myTable = "<table align=\"center\">";
			for (var i = 0; i < cancelledFromServer.length; i += 7) {
				email = cancelledFromServer[i + 5];
				confId = cancelledFromServer[i + 6];
				myTable += ("<tr>" + "<td>"
						+ cancelledFromServer[i]
						+ "</td>"
						+ "<td>"
						+ cancelledFromServer[i + 1]
						+ "</td>"
						+ "<td>"
						+ cancelledFromServer[i + 2]
						+ "</td>"
						+ "<td>"
						+ cancelledFromServer[i + 3]
						+ "</td>"
						+ "<td>"
						+ cancelledFromServer[i + 4]
						+ "</td>"
						+ "<td>"
						+ cancelledFromServer[i + 5]
						+ "</td>"
						+ "<td>"
						+ cancelledFromServer[i + 6]
						+ "<button id=\"undoB\" class=\"w3-button w3-round-large w3-red\" "
						+ "onclick=\"undoData(\'" + email + "\',\'" + confId
						+ "\')\" value=\"Undo\">Undo</button>" + "</td>");

			}
			myTable += "</table";
			document.getElementById("cancelTable").innerHTML = myTable;

		}

		//format pricelist sent from server
		var arrayFromServer = JSON.parse(priceList);
		console.log(arrayFromServer);

		//add pricelist sent from server to local priceArr
		for (var i = 0; i < arrayFromServer.length; i += 3) {
			var date = new Date((arrayFromServer[i + 1] + "T000:00:00")
					.replace(/-/g, '\/').replace(/T.+/, ''));
			console.log("date # " + i + ": " + date);
			var price = arrayFromServer[i + 2];
			var thisDay = new DayPrice(date, price);
			priceArr.push(thisDay);
		}

		//console.log("priceFromServer: " + priceArr[4].date + " and " + priceArr[4].price);

		function DayPrice(date, price) {
			this.date = date;
			this.newDate = date.toISOString().slice(0, 19).replace('T', ' ');
			this.price = price;
		}

		//display calendar to set prices
		myCalendar = new dhtmlXCalendarObject("calendarHere");
		myCalendar.hideTime();
		myCalendar.show();

		//show price on hover
		myCalendar
				.attachEvent(
						"onMouseOver",
						function(date, ev) {
							for (var i = 0; i < priceArr.length; i++) {
								if (priceArr[i].date.getTime() == date
										.getTime()) {
									document.getElementById("price").innerHTML = priceArr[i].date
											.toISOString().slice(0, 19)
											.replace('T', ' ').slice(0, -8)
											+ ": $" + priceArr[i].price;
								}
							}

						});
		var startDate;
		var endDate;

		var myDoubleCalendar;

		myDoubleCalendar = new dhtmlXDoubleCalendar("calendarHereThree");

		//myDoubleCalendar.show();

		myDoubleCalendar.leftCalendar.attachEvent("onClick", function(date) {
			startDate = date;
		});

		myDoubleCalendar.rightCalendar.attachEvent("onClick", function(date) {
			endDate = date;
		});

		function insertDates() {
			document.getElementById("hiddenStartDate").value = startDate;
			document.getElementById("hiddenEndDate").value = endDate;
		}

		function changeP(startDate, endDate) {
			var price = prompt("Please enter the new price in dollars (no cents):");
			sendPriceChange(startDate, endDate, price);
		}

		function sendPriceChange(startDate, endDate, price) {
			document.getElementById("startChange").value = startDate;
			document.getElementById("endChange").value = endDate;
			document.getElementById("priceChange").value = price;
			document.getElementById("updatePrices").submit();
		}

		//get input for price when a day is clicked and push to priceArr
		myCalendar
				.attachEvent(
						"onClick",
						function(date) {
							var price = prompt("Please enter the price for this day:");
							var replaced = false;
							//check to see if price for this day already exists and is being replaced
							for (var i = 0; i < priceArr.length; i++) {
								priceArr[i].date.setHours(0, 0, 0, 0);
								date.setHours(0, 0, 0, 0);
								if (priceArr[i].date.getTime() == date
										.getTime()) {
									var thisDay = new DayPrice(date, price);
									priceArr[i] = thisDay;
									arrToServer = JSON.stringify(priceArr);
									document.getElementById("hiddenArrayField").value = arrToServer;
									replaced = true;
								}
							}
							if (!replaced) {
								var thisDay = new DayPrice(date, price);
								priceArr.push(thisDay);
								console.log(priceArr);
								arrToServer = JSON.stringify(priceArr);
								document.getElementById("hiddenArrayField").value = arrToServer;
								//alert(arrToServer);
							}
						});

		//print out booking table
		var myTable = "<table id=\"customers\" align=\"center\"><tr><th>Check In</th><th>Check Out</th><th>First Name</th><th>Last Name</th><th>Phone</th><th>Email</th><th>Confirmation Id</th></tr>";
		var email;
		var confId;
		for (var i = 0; i < datesFromServer.length; i += 8) {
			var canceled = false;
			//check to see if info is in dates and cancel_req -- if so, make row RED
			for (var j = 0; j < cancelsFromServer.length; j += 2) {
				if (datesFromServer[i + 6] == cancelsFromServer[j + 1]
						&& datesFromServer[i + 5] == cancelsFromServer[j]) {
					canceled = true;
					email = cancelsFromServer[j];
					confId = cancelsFromServer[j + 1];
				}
			}
			if (canceled == false) {
				var dp = false;
				if(datesFromServer[i + 7] == 1){
					dp = true;
				}
				if(dp){
					myTable += "<tr style=\"background-color: PeachPuff\">";
				}
				else{
					myTable += "<tr>";
				}
				myTable += ("<td>"
						+ datesFromServer[i]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 1]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 2]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 3]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 4]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 5]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 6]);
				
						if(!dp){
							myTable += ("<button id=\"cancelB\" class=\"w3-button w3-right w3-round-large w3-blue\" "
							+ "onclick=\"insertData(\'" + datesFromServer[i + 5]
							+ "\',\'" + datesFromServer[i + 6]
							+ "\')\" value=\"Cancel\">Cancel</button>" + "</td>" + "</tr>");
						}
						else if(dp){
							myTable += ("<button id=\"releaseD\" class=\"w3-button w3-right w3-round-large w3-green\" "
									+ "onclick=\"releaseDeposit(\'" + datesFromServer[i + 5]
									+ "\',\'" + datesFromServer[i + 6]
									+ "\')\" value=\"Refund Deposit\">Refund Deposit</button>" + "</td>" +
									
									"<button id=\"cancelB\" class=\"w3-button w3-right w3-round-large w3-blue\" "
									+ "onclick=\"insertData(\'" + datesFromServer[i + 5]
									+ "\',\'" + datesFromServer[i + 6]
									+ "\')\" value=\"Cancel\">Cancel</button>" + "</td>" + "</tr>");
						}
			}
			if (canceled == true) {
				myTable += ("<tr style=\"color: red;\">" + "<td>"
						+ datesFromServer[i]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 1]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 2]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 3]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 4]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 5]
						+ "</td>"
						+ "<td>"
						+ datesFromServer[i + 6]
						+ "<button id=\"cancelB\" class=\"w3-button w3-round-large w3-red\" "
						+ "onclick=\"insertData(\'" + email + "\',\'" + confId
						+ "\')\" value=\"Cancel\">Cancel</button>" + "</td>" + "</tr>");
			}

		}
		myTable += "</table";

		document.getElementById("bookingtable").innerHTML = myTable;

		//FOR TESTING CHECKOUT
		var myCalendarTest;
		var myNextCalendarTest;
		var startDateTwo;
		var endDateTwo;
		var dayPrice = {
			date : new Date(2018, 0, 22),
			price : 500
		};
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
		startDateTwo = myCalendarTest.getDate();

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
		var getStartDate = myCalendarTest.attachEvent("onClick", function() {
			startDateTwo = myCalendarTest.getDate();
		});

		//capture end date and highlight trip and enable next button if selected trip is longer than three days
		var getEndDate = myNextCalendarTest.attachEvent("onClick", function() {
			endDateTwo = myNextCalendarTest.getDate();
			var outOfBounds = false;
			//make sure end date is before next insensitive date

			//make sure three day minimum
			var _MS_PER_DAY = 1000 * 60 * 60 * 24;
			if (Math.floor((endDate - startDate) / _MS_PER_DAY) < 2) {
				outOfBounds = true;
			}

			if (startDate != null && outOfBounds == false) {
				dimBeforeToday();
				document.getElementById("next").disabled = false;
				myCalendarTest.setHolidays(getDateArray(startDate, endDate));
				myNextCalendarTest
						.setHolidays(getDateArray(startDate, endDate));
			}
			//document.getElementById("estimatedprice").innerHTML = "Estimated price: " + calcPayment();
		});

		function dimDates() {
			myCalendarTest.setInsensitiveRange(startDateTwo, endDateTwo);
			myNextCalendarTest.setInsensitiveRange(startDate, endDateTwo);
		}

		function insertDatesTwo() {
			document.getElementById("hiddenStartDateTest").value = startDateTwo;
			document.getElementById("hiddenEndDateTest").value = endDateTwo;
		}

		function insertDatesThree() {
			document.getElementById("hiddenStartDateThree").value = startDateTwo;
			document.getElementById("hiddenEndDateThree").value = endDateTwo;
			document.getElementById("inqForm").submit();
		}
	</script>
</body>
</html>