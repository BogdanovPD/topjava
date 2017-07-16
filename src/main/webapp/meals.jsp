<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table border="1">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach items="${requestScope.get('meals')}" var="meal">
        <c:choose>
            <c:when test="${meal.isExceed()}">
                <tr style="color: red">
            </c:when>
            <c:otherwise>
                <tr style="color: green">
            </c:otherwise>
        </c:choose>
        <th>${meal.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</th>
        <th>${meal.getDescription()}</th>
        <th>${meal.getCalories()}</th>
        </tr>
    </c:forEach>
</table>
</body>
</html>
