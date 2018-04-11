<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type ="text/css" href="styles.css">
</head>
<body>
<div style="margin-left: 100px; margin-right: 500px; margin-top: 10px;">
	<form id ="login" action="Login" method="post">
	    <img src="ROR.Logo_Final-02.png" style="height: 50px;" >
	    <br>
		Username: <input class="w3-input w3-section" type="text" name="uname"><br>
		Password: <input class="w3-input w3-section" type="password" name="password"><br>
		<input class="w3-btn w3-round w3-blue-grey w3-section" type="submit" value="Login">
	</form>
</div>
</body>
</html>