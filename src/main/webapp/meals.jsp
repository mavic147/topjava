<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="mealStyle.css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table class="table">

    <tr>
        <th>Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Actions</th>
    </tr>

    <c:forEach var="meals" items="${mealsTo}">
    <tr style="color: ${meals.excess ? 'red' : 'green'}">
        <td><c:out value="${meals.id}" /></td>
        <td><c:out value="${localDateTimeFormatter.format(meals.dateTime)}" /></td>
        <td><c:out value="${meals.description}" /></td>
        <td><c:out value="${meals.calories}" /></td>
        <td style="color: ${'blue'}"><a href="/update&id=<c:out value="${meals.id}"/>">Update</a></td>
        <td style="color: ${'blue'}"><a href="/delete&id=<c:out value="${meals.id}"/>">Delete</a></td>
    </tr>
    </c:forEach>

</table>

<hr>
<a href="/new">Add Meal</a>

</body>
</html>