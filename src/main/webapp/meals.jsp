<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="mealsTo" type="java.util.List<ru.javawebinar.topjava.model.MealTo>" scope="request"/>
<html lang="ru">
<head>
    <link rel="stylesheet" type="text/css" href="meals.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsTo}" var="mealTo">
        <c:if test="${mealTo.excess}">
            <c:set var="rowClass" value="excess" />
        </c:if>
        <tr class="<c:out value="${rowClass}" />">
            <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" /></td>
            <td><c:out value="${mealTo.description}" /></td>
            <td><c:out value="${mealTo.calories}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>