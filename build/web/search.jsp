<%-- 
    Document   : search
    Created on : Feb 7, 2022, 8:20:33 AM
    Author     : reymy
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello ${sessionScope.FULLNAME}!</h1>
        <form action="SearchController" name="txtSearch">
           Search: <input type="text" name="txtSearch" /> <br/>
           <input type="submit" value="Search" />
        </form>
    <c:if test="${not empty requestScope.INFO}">
        <table border="i">
            <thead>
                <tr>
                    <th>No</th>
                    <th>ID</th>
                    <th>Class</th>
                    <th>Fullname</th>
                    <th>Address</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.INFO}" var="dto" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>${dto.id}</td>
                    <td>${dto.aClass}</td>
                    <td>${dto.lastname} ${dto.middlename} ${dto.firstname}</td>
                    <td>${dto.address}</td>
                    <td>
                <c:url var="deleteLink" value="DeleteController">
                    <c:param name="txtID" value="${dto.id}"/>
                </c:url>
                    <a href="${deleteLink}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    </body>
</html>
