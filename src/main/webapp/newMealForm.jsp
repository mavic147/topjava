<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<div align="center">
    <c:if test="${meal != null}">
    <form action="meals?action=update&id=${meals.id}" method="post">
        </c:if>
        <c:if test="${meal == null}">
        <form action="meals?action=create" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${meal != null}">
                            Edit Meal
                        </c:if>
                        <c:if test="${meal == null}">
                            Add Meal
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${meal != null}">
                    <input type="hidden" name="id" value="<c:out value='${meal.id}' />" />
                </c:if>
                <tr>
                    <th>DateTime: </th>
                    <td>
                        <input type="text" name="dateTime" size="45"
                               value="<c:out value='${meal.dateTime}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Description: </th>
                    <td>
                        <input type="text" name="description" size="45"
                               value="<c:out value='${meal.description}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Calories: </th>
                    <td>
                        <input type="text" name="calories" size="5"
                               value="<c:out value='${meal.calories}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <input type="submit" value="Save" />
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <input type="reset" value="Cancel" />
                    </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>
