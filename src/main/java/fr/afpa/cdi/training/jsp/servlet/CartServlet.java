/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.servlet;

import fr.afpa.cdi.training.jsp.helper.CartPageHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cdi420
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/Cart"})
public class CartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String submit = request.getParameter("submit");

        if (submit != null) {
            String reference = request.getParameter("reference");
            try {
                Integer quantity = Integer.valueOf(request.getParameter("quantity"));
                if (reference != null && !reference.isEmpty() && quantity > 0) {
                    session.setAttribute(reference, quantity);
                }
            } catch (NumberFormatException e) {
                CartPageHelper.getCartForm(response, session, true);
                return;
            }
            CartPageHelper.getCartForm(response, session, false);
        } else {
            String action = request.getParameter("action");

            if (action != null) {
                String ref = request.getParameter("ref");
                Integer qty = Integer.valueOf(request.getParameter("qty"));
                System.out.println(ref);
                System.out.println(qty);
                switch (action) {
                    case "add":
                        qty++;
                        session.setAttribute(ref, qty);
                        break;

                    case "remove":
                        qty--;
                        if (qty == 0) {
                            session.removeAttribute(ref);
                        } else {
                            session.setAttribute(ref, qty);
                        }
                        break;

                    case "empty":
                        session.removeAttribute(ref);
                        break;
                }
            }
            CartPageHelper.getCartForm(response, session, false);
        }

    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
