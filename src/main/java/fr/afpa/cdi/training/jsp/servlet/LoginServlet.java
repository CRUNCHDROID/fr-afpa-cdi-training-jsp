package fr.afpa.cdi.training.jsp.servlet;

import fr.afpa.cdi.training.jsp.helper.PageHelper;
import fr.afpa.cdi.training.jsp.model.PersonModel;
import java.io.IOException;
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("submit") != null) {
            switch (request.getParameter("submit")) {
                case "Login":
                    doLogin(request, response);
                    break;
                case "Logout":
                    doLogout(request, response);
                    break;
            }
        } else {
            Cookie cookieAttempt = getCookie(request.getCookies(), "LOGIN_ATTEMPT");
            Cookie cookieAttemptMin = getCookie(request.getCookies(), "LOGIN_ATTEMPT_MIN");
            Cookie authCookie = getCookie(request.getCookies(), "AUTH");

            if (authCookie != null) {
                PageHelper.getWelcomePage(response);
            } else {
                if (cookieAttempt != null && Integer.valueOf(cookieAttempt.getValue()) == 3) {

                    if (minutesExpired(cookieAttemptMin.getValue()) >= 1) {
                        cookieAttempt.setMaxAge(0);
                        cookieAttemptMin.setMaxAge(0);
                        response.addCookie(cookieAttempt);
                        response.addCookie(cookieAttemptMin);
                        PageHelper.getLoginForm(response, false);
                    } else {
                        PageHelper.getErrorPage(response, cookieAttempt.getValue());
                    }

                } else {
                    PageHelper.getLoginForm(response, false);
                }
            }
        }

    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = getCookie(request.getCookies(), "AUTH");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        PageHelper.getLoginForm(response, false);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Cookie cookieAttempt = getCookie(request.getCookies(), "LOGIN_ATTEMPT");
        Cookie cookieAttemptMin = getCookie(request.getCookies(), "LOGIN_ATTEMPT_MIN");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (username.equals("admin") && password.equals("root")) {

            if (cookieAttempt != null && cookieAttemptMin != null) {
                cookieAttempt.setMaxAge(0);
                cookieAttemptMin.setMaxAge(0);
                response.addCookie(cookieAttempt);
                response.addCookie(cookieAttemptMin);
            }

            response.addCookie(new Cookie("AUTH", "1"));
            PageHelper.getWelcomePage(response);

        } else {
            if (cookieAttempt != null && cookieAttemptMin != null) {

                Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, cookieAttemptMin.getValue());

                cookieAttempt.setValue(String.valueOf(Integer.valueOf(cookieAttempt.getValue()) + 1));
                response.addCookie(cookieAttempt);

                if (Integer.valueOf(cookieAttempt.getValue()) == 3) {
                    PageHelper.getErrorPage(response, cookieAttempt.getValue());
                } else {
                    PageHelper.getLoginForm(response, true);
                }

            } else {
                response.addCookie(new Cookie("LOGIN_ATTEMPT", "1"));
                response.addCookie(new Cookie("LOGIN_ATTEMPT_MIN", formatter.format(new Date())));
                PageHelper.getLoginForm(response, true);
            }
        }
    }

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

    private long minutesExpired(String stringDate) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        Date cookieDate;

        long minute = 0;

        try {
            cookieDate = format.parse(stringDate);

            long currTime = today.getTime();
            long cookieTime = cookieDate.getTime();
            long diff = currTime - cookieTime;
            minute = diff / (60 * 1000) % 60;
        } catch (ParseException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return minute;
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
