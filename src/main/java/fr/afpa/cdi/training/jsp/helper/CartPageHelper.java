/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cdi420
 */
public class CartPageHelper {

    public static void getCartForm(HttpServletResponse response, HttpSession session, boolean error) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Exercice 3</h1>");
        html.append("<form action='Cart' method='POST'>");
        html.append("<table border='0'>");
        html.append("<tbody>");
        html.append("<tr>");
        html.append("<td><label for='reference'>Ref:</label></td>");
        html.append("<td><input type='text' name='reference' id='reference' /></td>");
        html.append("<td></td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td><label for='quantity'>Quantity:</label></td>");
        html.append("<td><input type='text' name='quantity' id='quantity' /></td>");
        html.append("<td>");
        if (error) {
            html.append("<font color='red'>The quantity must be a positive number</font>");
        }
        html.append("</td>");
        html.append("</tr>");
        html.append("<tr>");
        html.append("<td></td>");
        html.append("<td><input type=\"submit\" value=\"Add\" name=\"submit\" /></td>");
        html.append("<td></td>");
        html.append("</tr>");
        html.append("</tbody>");
        html.append("</table>");
        html.append("</form>");

        Enumeration<String> cart = session.getAttributeNames();
        if (cart.hasMoreElements()) {
            html.append("<table border='0'>");
            html.append("<tbody>");
            while (cart.hasMoreElements()) {
                String reference = cart.nextElement();
                Integer quantity = (Integer) session.getAttribute(reference);

                html.append("<tr>");
                html.append("<td>").append(reference).append("</td>");
                html.append("<td>").append(quantity).append("</td>");
                html.append("<td>").append("<a href='?action=add&ref=").append(reference).append("&qty=").append(quantity).append("'>+</a>").append("</td>");
                html.append("<td>").append("<a href='?action=remove&ref=").append(reference).append("&qty=").append(quantity).append("'>-</a>").append("</td>");
                html.append("<td>").append("<a href='?action=empty&ref=").append(reference).append("&qty=").append(quantity).append("'>x</a>").append("</td>");
                html.append("</tr>");
            }
            html.append("</tbody>");
            html.append("</table>");

        } else {
            html.append("<h3>Empty Cart</h3>");
        }

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
