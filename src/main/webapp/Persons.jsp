<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 
    Document   : PersonView
    Created on : 1 oct. 2015, 14:29:15
    Author     : cdi420
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Persons</title>
    </head>
    <body>
        <h1>List of persons</h1>
        <table border="0">
            <thead>
                <tr>
                    <th colspan="4"><a href="?action=add" >Add new person</a></th>
                </tr>
                <tr>
                    <th>ID</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th>Birthdate</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${persons}" var="person">
                    <tr>
                        <td><c:out value="${person.id}"/></td>
                        <td><c:out value="${person.firstName}"/></td>
                        <td><c:out value="${person.lastName}"/></td>
                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${person.birthdate}"/></td>
                        <td><a href="?action=edit&id=${person.id}" >EDIT</a></td>
                        <td><a href="?action=delete&id=${person.id}" >DELETE</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
