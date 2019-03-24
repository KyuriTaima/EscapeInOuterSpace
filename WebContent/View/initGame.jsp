<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Space Escape Init </title>
</head>
<body>
<center>
	<c:if test="${joueurMax}">
    <form action="game" method="post">
      <p>Players Name:</p>
        <input name="playerName">
        <input type="submit" value="addPlayer" name="addPlayer">
    </form>
    </c:if>
    <table cellpadding="5" border="1">
        <tr>
            <td>Players</td>
        </tr>
        <c:forEach var="player" items="${players}">
            <tr>
                <td><c:out value="${player}"/></td>
            </tr>
        </c:forEach>
    </table>

    <form action="game/start" method="post">
        <input name="players" type="hidden" value="${players}">
        <input type="submit" value="Game" name="Game">
    </form>

</center>
</body>
</html>