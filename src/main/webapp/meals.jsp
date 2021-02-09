<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <link rel="stylesheet" type="text/css" href="mealStyle.css" />
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<jsp:useBean id="mealsUtil" class="ru.javawebinar.topjava.util.MealsUtil"/>
<c:set var="meals" value="${mealsUtil.findAll()}" />

<table class="table">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <c:forEach items="${meals}" var="meal">
    <tr>
        <td><c:out value="${meal.getDateTime()}" /></td>
        <td><c:out value="${meal.getDescription()}" /></td>
        <td><c:out value="${meal.getCalories()}" /></td>
    </tr>
    </c:forEach>

</table>

</body>
</html>