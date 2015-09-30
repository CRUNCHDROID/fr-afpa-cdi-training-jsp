/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cdi420
 */
public class PageUtil {

    public static StringBuilder getLoginForm(boolean error) {
        StringBuilder form = new StringBuilder();
        form.append("<h1>Authentication</h1>");
        form.append("<form action=\"Login\" method=\"POST\">");
        form.append("<table border=\"0\">");
        form.append("<thead>");
        form.append("<tbody>");
        if (error) {
            form.append("<tr>");
            form.append("<td>Username or password error</td>");
            form.append("<td></td>");
            form.append("</tr>");

        }
        form.append("<tr>");
        form.append("<td><label for=\"username\">Email</label></td>");
        form.append("<td><input type=\"text\" name=\"username\" id=\"username\" /></td>");
        form.append("</tr>");
        form.append("<tr>");
        form.append("<td><label for=\"password\">Password</label></td>");
        form.append("<td><input type=\"password\" name=\"password\" id=\"password\" /></td>");
        form.append("</tr>");
        form.append("<tr>");
        form.append("<td></td>");
        form.append("<td><input type=\"submit\" value=\"Login\" name=\"auth\" /></td>");
        form.append("</tr>");
        form.append("</tbody>");
        form.append("</table>");
        form.append("</form>");
        return form;
    }

    public static StringBuilder getWelcomePage() {
        StringBuilder form = new StringBuilder();
        form.append("<h1>Welcome Admin</h1>");
        form.append("<form action=\"Login\" method=\"POST\">");
        form.append("<input type=\"submit\" value=\"Logout\" name=\"auth\" />");
        form.append("</form>");
        return form;
    }

    public static StringBuilder getErrorPage(String loginAttempt) {
        StringBuilder form = new StringBuilder();
        form.append("<h1>");
        form.append(loginAttempt);
        form.append(" Connection Attempt Fails");
        form.append("</h1>");
        return form;
    }

    public static void buildPage(HttpServletResponse response, StringBuilder page) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(page);
            out.println("</body>");
            out.println("</html>");
        }
    }
}
