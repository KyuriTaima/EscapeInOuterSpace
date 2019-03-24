<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style><%@include file="/css/style.css"%></style>
<title>Map displayer</title>
</head>
<body>
<p>C'est au tour de ${playerName} de jouer !</p>
<p>Tu es un ${playerType}</p>
<table>
<tr>
<c:forEach items="${line1}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line2}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line3}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line4}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line5}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line6}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line7}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line8}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line9}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line10}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line11}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line12}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line13}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line14}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line15}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
<tr>
<c:forEach items="${line16}" var="item">
<td>${item} </td>
</c:forEach>
</tr>
</table>
<br />
<form action="interaction" method="post" class="choiceForm">
<c:forEach items="${possibleMoves}" var="item">
<button type="submit" value="${item}" name="playerChoice">${item}</button>
</c:forEach>
</form>
<form action="interaction/action" method="post" class="choiceForm">
<c:forEach items="${actions}" var="item">
<button type="submit" value="${item}" name="playerChoice">${item}</button>
</c:forEach>
</form>
<c:forEach items="${messages}" var="item">
<p>${item}</p>
</c:forEach>
<c:set value="${finDuTour}" var="endGame"/>
<c:if test="${endGame }">
<form action="../map" method="post">
<button type="submit">Fin du Tour</button>
</form>
</c:if>
<c:forEach items="${historic}" var="item">
<p>${item}</p>
</c:forEach>

</body>
</html>