<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Game</title>
</head>
<body>
<div class="headdiv">
	<p>Player name: </p><br/>
	<p>${playerName}</p>
</div>
<div class="dislplayButonDiv">
<form action="map" method="post">
<button type="submit">Show map</button>
</form>
</div>

</body>
</html>