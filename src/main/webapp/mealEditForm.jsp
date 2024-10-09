<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add meal</title>
    <link href="<c:url value="/styles/style.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${param.action == 'add' ? 'Add meal' : 'Edit meal'}</h2>
<form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt><label for="dateTime">Date/Time</label></dt>
        <dd><input type="datetime-local" name="dateTime" id="dateTime" required value="${meal.dateTime}"></dd>
    </dl>
    <dl>
        <dt><label for="description">Description</label></dt>
        <dd><input type="text" name="description" id="description" required value="${meal.description}"></dd>
    </dl>
    <dl>
        <dt><label for="calories">Calories</label></dt>
        <dd><input type="number" name="calories" id="calories" required value="${meal.calories}"></dd>
    </dl>
    <div>
        <button type="submit">${empty meal.id ? "Add" : "Update"}</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </div>
</form>

</body>
</html>