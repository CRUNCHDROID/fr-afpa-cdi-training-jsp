/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.bean.helper;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cdi420
 */
public class PageHelper {

    public static void getLoginForm(HttpServletResponse response, boolean error) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Authentication</h1>");
        html.append("<form action=\"Login\" method=\"POST\">");
        html.append("<table border=\"0\">");
        html.append("<tbody>");
        if (error) {
            html.append("<tr>");
            html.append("<td colspan=\"3\"><font color=\"red\">Invalid username or password</font></td>");
            html.append("</tr>");
        }
        html.append("<tr>");
        html.append("<td><label for=\"username\">Email</label></td>");
        html.append("<td><input type=\"text\" name=\"username\" id=\"username\" /></td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td><label for=\"password\">Password</label></td>");
        html.append("<td><input type=\"password\" name=\"password\" id=\"password\" /></td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td></td>");
        html.append("<td><input type=\"submit\" value=\"Login\" name=\"submit\" /></td>");
        html.append("</tr>");
        html.append("</tbody>");
        html.append("</table>");
        html.append("</form>");
        buildPage(response, html);
    }

    public static void getWelcomePage(HttpServletResponse response) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Welcome Admin</h1>");
        html.append("<form action=\"Login\" method=\"POST\">");
        html.append("<input type=\"submit\" value=\"Logout\" name=\"submit\" />");
        html.append("</form>");
        buildPage(response, html);
    }

    public static void getErrorPage(HttpServletResponse response, String loginAttempt) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<h1><font color=\"red\">");
        html.append(loginAttempt);
        html.append(" Connection Attempt Fails");
        html.append("</font></h1>");
        buildPage(response, html);
    }

    private static void buildPage(HttpServletResponse response, StringBuilder page) throws IOException {
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
