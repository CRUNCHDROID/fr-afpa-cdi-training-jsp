/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.servlet;

import fr.afpa.cdi.training.jsp.model.PersonModel;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cdi420
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    private Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (request.getParameter("auth")) {
            case "Login":
                doLogin(request, response);
                break;
            case "Logout":
                doLogout(request, response);
                break;
        }

    }

    private long diffDate(String dateString) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        Date date;
        try {
            date = dateFormat.parse(dateString);
            long dif = new Date().getTime() - date.getTime();
            return dif / (60 * 1000) % 60;
        } catch (ParseException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = getCookie(request.getCookies(), "AUTH");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        PageUtil.buildPage(response, PageUtil.getLoginForm(false));
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Cookie cookieAttempt = getCookie(request.getCookies(), "LOGIN_ATTEMPT");
        Cookie cookieAttemptMin = getCookie(request.getCookies(), "LOGIN_ATTEMPT_MIN");

        if (username.equals("admin") && password.equals("root")) {

            if (cookieAttempt != null && cookieAttemptMin != null) {
                cookieAttempt.setMaxAge(0);
                cookieAttemptMin.setMaxAge(0);
                response.addCookie(cookieAttempt);
                response.addCookie(cookieAttemptMin);
            }

            response.addCookie(new Cookie("AUTH", "1"));
            PageUtil.buildPage(response, PageUtil.getWelcomePage());
        } else {
            if (cookieAttempt != null && cookieAttemptMin != null) {

                Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, cookieAttemptMin.getValue());

                if (Integer.valueOf(cookieAttempt.getValue()) >= 3 && diffDate(cookieAttemptMin.getValue()) >= 10) {

                    PageUtil.buildPage(response, PageUtil.getErrorPage(cookieAttempt.getValue()));
                } else {
                    cookieAttempt.setValue(String.valueOf(Integer.valueOf(cookieAttempt.getValue()) + 1));
                    response.addCookie(cookieAttempt);
                    PageUtil.buildPage(response, PageUtil.getLoginForm(true));
                }
            } else {
                response.addCookie(new Cookie("LOGIN_ATTEMPT", "1"));
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                response.addCookie(new Cookie("LOGIN_ATTEMPT_MIN", formatter.format(new Date())));
                PageUtil.buildPage(response, PageUtil.getLoginForm(true));
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
