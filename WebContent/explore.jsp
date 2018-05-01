<!DOCTYPE html>
<html>
<title>Ranch on the Rocks</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type ="text/css" href="styles.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:500">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Facebook Pixel Code -->
<script>
!function(f,b,e,v,n,t,s)
{if(f.fbq)return;n=f.fbq=function(){n.callMethod?
n.callMethod.apply(n,arguments):n.queue.push(arguments)};
if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
n.queue=[];t=b.createElement(e);t.async=!0;
t.src=v;s=b.getElementsByTagName(e)[0];
s.parentNode.insertBefore(t,s)}(window,document,'script',
'https://connect.facebook.net/en_US/fbevents.js');
 fbq('init', '900972550076057'); 
fbq('track', 'PageView');
</script>
<noscript>
 <img height="1" width="1" 
src="https://www.facebook.com/tr?id=900972550076057&ev=PageView
&noscript=1"/>
</noscript>
<!-- End Facebook Pixel Code -->
<script async src="https://www.googletagmanager.com/gtag/js?id=GA_TRACKING_ID"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-114725707-1');
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Poppins", sans-serif;}
body, html {
    height: 100%;
    color: #595959;
    line-height: 1.8;
    font-size: 108%;
}

/* Create a Parallax Effect */
.bgimg-1, .bgimg-2, .bgimg-3 {
    background-attachment: fixed;
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
}

/* First image (Logo. Full height) */
.bgimg-1 {
    background-image: url('ranchfarair.jpg');
    min-height: 92%;
}

/* Second image (Portfolio) */
.bgimg-2 {
    background-image: url("exteriorback.jpg");
    min-height: 350px;
}

/* Third image (Contact) */
.bgimg-3 {
    background-image: url("exteriorfront.jpg");
    min-height: 350px;
}

.w3-wide {letter-spacing: 10px;}
.w3-hover-opacity {cursor: pointer;}

/* Turn off parallax scrolling for tablets and phones */
@media only screen and (max-device-width: 1024px) {
    .bgimg-1, .bgimg-2, .bgimg-3 {
        background-attachment: scroll;
    }
}
</style>
<body>

<!-- Navbar (sit on top) -->
<div class="w3-top">
  <div class="w3-bar" id="myNavbar" style="padding-left: 10px; padding-right: 10px; color:white;">
    <a class="w3-bar-item w3-button w3-hover-black w3-hide-medium w3-hide-large w3-right" href="javascript:void(0);" onclick="toggleFunction()" title="Toggle Navigation Menu">
      <i class="fa fa-bars"></i>
    </a>
    <a href="index.jsp" class="w3-bar-item w3-button"><img src="ROR.Logo_Final-02.png" height=60px></a>
    <a href="#" id="bbtn" class="w3-bar-item w3-button w3-hide-small"  style="float:right; text-decoration: none; padding-right: 5px;"><i class="fa fa-cutlery" aria-hidden="true"></i>  DINING</a>
    <a href="#" id="ebtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; text-decoration: none; padding-right: 5px;"><i class="fa fa-paint-brush" aria-hidden="true"></i>  CULTURE</a>
    <a href="#" id="rbtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; padding-right: 5px; text-decoration: none;"><i class="fa fa-heartbeat" aria-hidden="true"></i>SPORTS</a>
    <a href="#" id="rbtn" class="w3-bar-item w3-button w3-hide-small" style="float:right; padding-right: 5px; text-decoration: none;"><i class="fa fa-tree" aria-hidden="true"></i>  NATURE</a>
  </div>

  <!-- Navbar on small screens -->
  <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium">
    <a href="#about" class="w3-bar-item w3-button" onclick="toggleFunction()">ABOUT</a>
    <a href="#portfolio" class="w3-bar-item w3-button" onclick="toggleFunction()">PORTFOLIO</a>
    <a href="#contact" class="w3-bar-item w3-button" onclick="toggleFunction()">CONTACT</a>
    <a href="#" class="w3-bar-item w3-button">SEARCH</a>
  </div>
</div>

<div class="w3-content w3-container w3-padding-64" id="explore">
  <h3 class="w3-center w3-wide w3-xxxlarge" style="border-bottom: 2px solid #777"><b>EXPLORE...</b></h3>
  
  <div class="w3-row w3-center w3-padding-16 w3-animate-right">
  <a href="#" id="naturebtn" style="color: #595959;">
  <div class="w3-quarter w3-section">
  <i class="fa fa-tree fa-2x" aria-hidden="true"></i>
    NATURE
  </div>
  </a>
  <a href="#" id="sportsbtn" style="color: #595959;">
  <div class="w3-quarter w3-section">
  <i class="fa fa-heartbeat fa-2x" aria-hidden="true" style="margin-right: 10px"></i>
    SPORTS
  </div>
  </a>
  <a href="#" id="culturebtn" style="color: #595959;">
  <div class="w3-quarter w3-section">
  <i class="fa fa-paint-brush fa-2x" aria-hidden="true" style="padding-right: 5px"></i>
    CULTURE
  </div>
  </a>
  <a href="#" id="diningbtn" style="color: #595959;">
  <div class="w3-quarter w3-section">
  <i class="fa fa-cutlery fa-2x" aria-hidden="true" style="padding-right: 5px"></i>
    DINING
  </div>
  </a>
</div>
</div>


<div class = "w3-container" style="padding-bottom: 10px; text-align: right; padding-right: 15%"> 
<div class="w3-dropdown-hover w3-right" style="margin-left: 5px">
  <button class="w3-button">Order By:</button>
  <div class="w3-dropdown-content w3-bar-block w3-border" style="right:0">
    <a href="#" onclick="window.location.reload()" class="w3-bar-item w3-button">Category</a>
    <a href="#" onclick="orderByDistance()"class="w3-bar-item w3-button">Proximity</a>
    
  </div>
</div>
</div>
 

<ul id="ulName" style="list-style:none;">

<li data-sortby="1">
<div class="w3-card-4 w3-animate-bottom" id="nature" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-tree" aria-hidden="true" style="padding-right: 5px"></i> Red Rocks Park</h3>
</header>

<div class="w3-container">
  <p><em>0.8 miles away</em></p>
  <hr>
  
  <p>Large park with many trails for hiking and biking.</p><br>
</div>

<a href="http://www.redrocksonline.com/the-park" target="_blank"><button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button></a>

</div>
<br>
</li>

<li data-sortby="4">
<div class="w3-card-4 w3-animate-bottom" id="d2.5" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-tree" aria-hidden="true" style="padding-right: 5px"></i> Dinosaur Ridge</h3>
</header>

<div class="w3-container">
  <p><em>2.5 miles away</em></p>
  <hr>
  
  <p>National Natural Landmark ranked as one of the top dinosaur tracksites in the world.</p><br>
</div>

<a href="http://www.dinoridge.org/" target="_blank"><button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button></a>

</div>
<br>
</li>

<li data-sortby="6">
<div class="w3-card-4 w3-animate-bottom" id ="f2.8" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-tree" aria-hidden="true" style="padding-right: 5px"></i> Mount Falcon Park</h3>
</header>

<div class="w3-container">
  <p><em>2.8 miles away</em></p>
  <hr>
  
  <p>Trails with opportunities to see wildlife up close.</p><br>
</div>
<a href="https://www.jeffco.us/1332/Mount-Falcon-Park/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="8">
<div class="w3-card-4 w3-animate-bottom" id ="h5.9" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-tree" aria-hidden="true" style="padding-right: 5px"></i> Lair o' the Bear Park</h3>
</header>

<div class="w3-container">
  <p><em>5.9 miles away</em></p>
  <hr>
  
  <p>Park with open space for picnics, fishing and hiking.</p><br>
</div>

<a href="https://www.jeffco.us/1254/Lair-o-the-Bear-Park/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="7">
<div class="w3-card-4 w3-animate-bottom" id="g5.2" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-tree" aria-hidden="true" style="padding-right: 5px"></i> Bear Creek Lake Regional Park</h3>
</header>

<div class="w3-container">
  <p><em>5.2 miles away</em></p>
  <hr>
  
  <p>Massive park for activities such as kayaking, swimming, horseback riding or hiking.</p><br>
</div>

<a href="http://www.lakewood.org/Community_Resources/Parks,_Forestry_and_Open_Space/Bear_Creek_Lake_Park/Bear_Creek_Lake_Park.aspx" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="14">
<div class="w3-card-4 animate-bottom" id="n8.6" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-tree" aria-hidden="true" style="padding-right: 5px"></i> Lookout Mountain</h3>
</header>

<div class="w3-container">
  <p><em>8.6 miles away</em></p>
  <hr>
  
  <p>Scenic mountain near Denver with vast, sweeping views.</p><br>
</div>

<a href="https://www.tripadvisor.com/Attraction_Review-g33447-d142973-Reviews-Lookout_Mountain-Golden_Colorado.html" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="13">
<div class="w3-card-4 animate-bottom" id="sports" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-heartbeat" aria-hidden="true" style="padding-right: 5px"></i> Rafting on Clear Creek</h3>
</header>

<div class="w3-container">
  <p><em>8.6 miles away</em></p>
  <hr>
  
  <p>Whitewater rafting on Clear Creek with difficulty levels ranging from beginner to advanced</p><br>
</div>

<a href="http://milehirafting.com/local/atv-tours/morrison-colorado/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="12">
<div class="w3-card-4 animate-bottom" id="l8.6" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-heartbeat" aria-hidden="true" style="padding-right: 5px"></i> ATV Tours</h3>
</header>

<div class="w3-container">
  <p><em>8.6 miles away</em></p>
  <hr>
  
  <p>Take in the scenery while riding an ATV.</p><br>
</div>

<a href="http://milehirafting.com/local/atv-tours/morrison-colorado/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="5">
<div class="w3-card-4 animate-bottom" id="e2.7" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-heartbeat" aria-hidden="true" style="padding-right: 5px"></i> Go Karting/Car Racing</h3>
</header>

<div class="w3-container">
  <p><em>2.7 miles away</em></p>
  <hr>
  
  <p>Experience the thrill of racing at the Bandimere Speedway!</p><br>
</div>

<a href="http://www.bandimere.com/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="16">
<div class="w3-card-4 animate-bottom" id="p41.8" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-heartbeat" aria-hidden="true" style="padding-right: 5px"></i> Eldora Mountain Resort</h3>
</header>

<div class="w3-container">
  <p><em>41.8 miles away</em></p>
  <hr>
  
  <p>Nearby mountain resort for mountain sports, shopping and dining.</p><br>
</div>

<a href="http://www.eldora.com/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="17">
<div class="w3-card-4 animate-bottom" id="q55.8" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-heartbeat" aria-hidden="true" style="padding-right: 5px"></i> Winter Park Mountain Resort</h3>
</header>

<div class="w3-container">
  <p><em>55.8 miles away</em></p>
  <hr>
  
  <p>Nearby mountain resort for mountain sports, shopping and dining.</p><br>
</div>

<a href="https://www.winterparkresort.com/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="4">
<div class="w3-card-4 animate-bottom" id="culture" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-paint-brush" aria-hidden="true" style="padding-right: 5px"></i> Morrison Natural History Museum</h3>
</header>

<div class="w3-container">
  <p><em>1.9 miles away</em></p>
  <hr>
  
  <p>Natural History Museum housing several dinosaur fossils that were found nearby.</p><br>
</div>


<a href="http://www.mnhm.org/246/Morrison-Natural-History-Museum" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="9">
<div class="w3-card-4 animate-bottom" id="i7.1" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-paint-brush" aria-hidden="true" style="padding-right: 5px"></i> Tiny Town</h3>
</header>

<div class="w3-container">
  <p><em>7.1 miles away</em></p>
  <hr>
  
  <p>A miniature village complete with a miniature railroad.</p><br>
</div>

<a href="http://tinytownrailroad.com/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="15">
<div class="w3-card-4 animate-bottom" id="o12.3" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-paint-brush" aria-hidden="true" style="padding-right: 5px"></i> Colorado Railroad Museum</h3>
</header>

<div class="w3-container">
  <p><em>12.3 miles away</em></p>
  <hr>
  
  <p>Home of over 100 narrow and standard gauge steam and diesel locomotives,
   passenger cars, cabooses HO Model Railroad and G-scale garden railway on a 15-acre railyard.</p><br>
</div>

<a href="http://coloradorailroadmuseum.org/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="8">
<div class="w3-card-4 animate-bottom" id="i8.2" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-paint-brush" aria-hidden="true" style="padding-right: 5px"></i> Golden History Park</h3>
</header>

<div class="w3-container">
  <p><em>8.2 miles away</em></p>
  <hr>
  
  <p>Parkland & wooden cabins with a living history museum showcasing life during the early 1800s.</p><br>
</div>

<a href="https://www.goldenhistory.org/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="11">
<div class="w3-card-4 animate-bottom" id="k8.6" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-paint-brush" aria-hidden="true" style="padding-right: 5px"></i> Buffalo Bill Museum and Grave</h3>
</header>

<div class="w3-container">
  <p><em>8.6 miles away</em></p>
  <hr>
  
  <p>Park-based tribute to cowboy & Indian days with exhibits, events, gift shop & observation deck.</p><br>
</div>

<a href="http://www.buffalobill.org/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="10">
<div class="w3-card-4 animate-bottom" id="dining" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-cutlery" aria-hidden="true" style="padding-right: 5px"></i> Twin Forks Tavern</h3>
</header>

<div class="w3-container">
  <p><em>8.1 miles away</em></p>
  <hr>
  
  <p>A fine dining experience nestled in the foothills of the Rocky Mountains with farm fresh food. </p><br>
</div>

<a href="http://www.twinforkstavern.com/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

<li data-sortby="2">
<div class="w3-card-4 animate-bottom" id="b1.7" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-cutlery" aria-hidden="true" style="padding-right: 5px"></i> Cafe Prague</h3>
</header>

<div class="w3-container">
  <p><em>1.7 miles away</em></p>
  <hr>
  
  <p>Homestyle European standards like schnitzel in the woody, art-lined dining room or out on the patio. </p><br>
</div>

<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>

</div>
<br>
</li>

<li data-sortby="3">
<div class="w3-card-4 animate-bottom" id="c1.8" style="width: 70%; margin: auto;">

<header class="w3-container w3-light-grey">
  <h3><i class="fa fa-cutlery" aria-hidden="true" style="padding-right: 5px"></i> Willy's Wings</h3>
</header>

<div class="w3-container">
  <p><em>1.8 miles away</em></p>
  <hr>
  
  <p>Humble spot for boneless & bone-in fried chicken served with many sauces & popular corn nuggets. </p><br>
</div>

<a href="http://www.willyswings.com/" target="_blank">
<button class="w3-button w3-block w3-blue-grey">Visit Site<i class="fa fa-external-link" aria-hidden="true" style="padding-left: 5px;"></i></button>
</a>
</div>
<br>
</li>

</ul>


<!-- Footer -->
<footer class="w3-center w3-padding-64" style="background: linear-gradient(white,#ebf2f9);">
  <a href="#home" class="w3-button w3-round w3-blue-grey"><i class="fa fa-arrow-up w3-margin-right"></i>To the top</a>
  <div class="w3-xlarge w3-section">
    
  </div>
 
</footer>
 
<!-- Add Google Maps -->
<script>
function orderByDistance(){
	var ul = $('ul#ulName'),
    li = ul.children('li');
    
    li.detach().sort(function(a,b) {
        return $(a).data('sortby') - $(b).data('sortby');  
    });
    
    ul.append(li);
}


function myMap()
{
  myCenter=new google.maps.LatLng(41.878114, -87.629798);
  var mapOptions= {
    center:myCenter,
    zoom:12, scrollwheel: false, draggable: false,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  var map=new google.maps.Map(document.getElementById("googleMap"),mapOptions);

  var marker = new google.maps.Marker({
    position: myCenter,
  });
  marker.setMap(map);
}

// Modal Image Gallery
function onClick(element) {
  document.getElementById("img01").src = element.src;
  document.getElementById("modal01").style.display = "block";
  var captionText = document.getElementById("caption");
  captionText.innerHTML = element.alt;
}

// Change style of navbar on scroll
window.onscroll = function() {myFunction()};
function myFunction() {
    var navbar = document.getElementById("myNavbar");
    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
        navbar.className = "w3-bar" + " w3-card" + " w3-animate-top" + " w3-white";
    } else {
        navbar.className = navbar.className.replace(" w3-card w3-animate-top w3-white", "");
    }
}

// Used to toggle the menu on small screens when clicking on the menu button
function toggleFunction() {
    var x = document.getElementById("navDemo");
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
    } else {
        x.className = x.className.replace(" w3-show", "");
    }
}

(function($) {

	  /**
	   * Copyright 2012, Digital Fusion
	   * Licensed under the MIT license.
	   * http://teamdf.com/jquery-plugins/license/
	   *
	   * @author Sam Sehnert
	   * @desc A small plugin that checks whether elements are within
	   *     the user visible viewport of a web browser.
	   *     only accounts for vertical position, not horizontal.
	   */

	  $.fn.visible = function(partial) {
	    
	      var $t            = $(this),
	          $w            = $(window),
	          viewTop       = $w.scrollTop(),
	          viewBottom    = viewTop + $w.height(),
	          _top          = $t.offset().top,
	          _bottom       = _top + $t.height(),
	          compareTop    = partial === true ? _bottom : _top,
	          compareBottom = partial === true ? _top : _bottom;
	    
	    return ((compareBottom <= viewBottom) && (compareTop >= viewTop));

	  };
	    
	})(jQuery);

	var win = $(window);

	var allMods = $(".ani");

	allMods.each(function(i, el) {
	  var el = $(el);
	  if (el.visible(true)) {
	    el.addClass("already-visible"); 
	  } 
	});

	win.scroll(function(event) {
	  
	  allMods.each(function(i, el) {
	    var el = $(el);
	    if (el.visible(true)) {
	      el.addClass("come-in"); 
	    } 
	  });
	  
	});
	
	
	
	
	document.addEventListener('DOMContentLoaded',function(event){
		  // array with texts to type in typewriter
		  var dataText = [ "RELAX...", "EXPLORE...", "RANCH ON THE ROCKS"];
		  
		  // type one text in the typwriter
		  // keeps calling itself until the text is finished
		  function typeWriter(text, i, fnCallback) {
		    // chekc if text isn't finished yet
		    if (i < (text.length)) {
		      // add next character to h1
		     document.querySelector("span").innerHTML = text.substring(0, i+1) +'<span aria-hidden="true"></span>';

		      // wait for a while and call this function again for next character
		      setTimeout(function() {
		        typeWriter(text, i + 1, fnCallback)
		      }, 100);
		    }
		    // text finished, call callback if there is a callback function
		    else if (typeof fnCallback == 'function') {
		      // call callback after timeout
		      setTimeout(fnCallback, 700);
		    }
		  }
		  // start a typewriter animation for a text in the dataText array
		   function StartTextAnimation(i) {
		     if (typeof dataText[i] == 'undefined'){
		        setTimeout(function() {
		          StartTextAnimation(0);
		        }, 20000);
		     }
		     // check if dataText[i] exists
		    if (i < dataText[i].length) {
		      // text exists! start typewriter animation
		     typeWriter(dataText[i], 0, function(){
		       // after callback (and whole text has been animated), start next text
		       StartTextAnimation(i + 1);
		     });
		    }
		  }
		  // start the text animation
		  StartTextAnimation(0);
		});
	
	$("#naturebtn").click(function() {
	    $('html, body').animate({
	        scrollTop: $("#sports").offset().top
	    }, 1000);
	});
	
	$("#sportsbtn").click(function() {
	    $('html, body').animate({
	        scrollTop: $("#sports").offset().top
	    }, 1000);
	});
	
	$("#culturebtn").click(function() {
	    $('html, body').animate({
	        scrollTop: $("#culture").offset().top
	    }, 1000);
	}); 
	
	$("#diningbtn").click(function() {
	    $('html, body').animate({
	        scrollTop: $("#dining").offset().top
	    }, 1000);
	}); 
	
	
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBu-916DdpKAjTmJNIgngS6HL_kDIKU0aU&callback=myMap"></script>
<!--
To use this code on your website, get a free API key from Google.
Read more at: https://www.w3schools.com/graphics/google_maps_basic.asp
-->

</body>
</html>
