<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>MealById</title>
    <link rel="stylesheet" type="text/css" href="mealStyle.css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>MealById</h2>

<table class="table">
    <tr>
        <th>Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <tr>
        <td><c:out value="${meal.id}" /></td>
        <td><c:out value="${localDateTimeFormatter.format(meal.dateTime)}" /></td>
        <td><c:out value="${meal.description}" /></td>
        <td><c:out value="${meal.calories}" /></td>
    </tr>

</table>

</body>
</html>
