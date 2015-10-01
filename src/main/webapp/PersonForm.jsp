<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 
    Document   : PersonForm
    Created on : 1 oct. 2015, 09:49:53
    Author     : cdi420
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Add person</h1>
        <form action="Person" method="POST">
            <table border="0">
                <tbody>
                    <tr>
                        <td><label for="firstName">First name</label></td>
                        <td><input type="text" name="firstName" value="${person.firstName}" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><label for="lastName">Last name</label></td>
                        <td><input type="text" name="lastName" value="${person.lastName}" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><label for="birthdate">Birthdate</label></td>
                        <td><input type="text" name="birthdate" value="<fmt:formatDate value="${person.birthdate}" pattern="dd/MM/yyyy"/>" /></td>
                        <td><p>(dd/mm/yyyy)</p></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="id" value="${person.id}" /></td>
                        <td><input type="submit" name="action" value="Save" /></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>

        </form>
    </body>
</html>
