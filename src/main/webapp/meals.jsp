<%--
  Created by IntelliJ IDEA.
  User: Zooba
  Date: 06.07.2018
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>

<table>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr style="color: ${meal.exceed ? "red" : "green"}">
            <td><c:out value="${f:formatLocalDateTime(meal.dateTime)}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="<c:url value="meals?action=edit&id=${meal.id}"/>">Edit</a></td>
            <td><a href="<c:url value="meals?action=remove&id=${meal.id}"/>">Remove</a></td>
        </tr>
    </c:forEach>
</table>


<form method="post" action="meals">

    <label for="id" ${meal.id == null? "hidden" : ""}>
        Id: <input id="id" type="text" value="${meal.id}" readonly name="id">
    </label>
    <label for="dateTime">dateTime: </label>
    <input id="dateTime" type="datetime-local" value="${meal.dateTime}" name="dateTime">

    <label for="description">description: </label>
    <input id="description" type="text" value="${meal.description}" name="description">

    <label for="calories">calories: </label>
    <input id="calories" type="text" value="${meal.calories}" name="calories">
    <input type="submit" value="${meal.id == null ? "Add" : "Update"}"/>
</body>
</html>
