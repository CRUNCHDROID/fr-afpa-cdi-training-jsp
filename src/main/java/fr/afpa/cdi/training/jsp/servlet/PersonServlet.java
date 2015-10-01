/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.afpa.cdi.training.jsp.servlet;

import fr.afpa.cdi.training.jsp.bean.Person;
import fr.afpa.cdi.training.jsp.model.PersonModel;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cdi420
 */
@WebServlet(name = "PersonServlet", urlPatterns = {"/Person"})
public class PersonServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        Integer id = 0;
        try {
            id = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
        }
        PersonModel personModel = new PersonModel();

        if (action == null) {
            action = "list";
        }
        switch (action.toLowerCase()) {
            case "list":
                request.setAttribute("persons", personModel.findAll());
                request.getRequestDispatcher("/Persons.jsp").forward(request, response);
                break;
            case "edit":
                request.setAttribute("person", personModel.findById(id));
                request.getRequestDispatcher("/PersonForm.jsp").forward(request, response);
                break;
            case "add":
                request.getRequestDispatcher("/PersonForm.jsp").forward(request, response);
                break;
            case "delete":
                personModel.delete(id);
                request.setAttribute("persons", personModel.findAll());
                request.getRequestDispatcher("/Persons.jsp").forward(request, response);
                break;
        }

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

        Person person = new Person();
        PersonModel personModel = new PersonModel();
        person.setFirstName(request.getParameter("firstName"));
        person.setLastName(request.getParameter("lastName"));
        String birthdate = request.getParameter("birthdate");
        Integer id = 0;

        try {
            id = Integer.valueOf(request.getParameter("id"));
            person.setId(id);
        } catch (NumberFormatException ex) {
        }

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
            person.setBirthdate(new java.sql.Date(date.getTime()));
        } catch (ParseException ex) {
        }

        if (id > 0) {
            boolean updated = personModel.update(person);
            Logger.getLogger(PersonServlet.class.getName()).log(Level.SEVERE, "is updated ::: {0}", updated);

        } else {
            boolean saved = personModel.save(person);
            Logger.getLogger(PersonServlet.class.getName()).log(Level.SEVERE, "is saved ::: {0}", saved);
        }

        request.setAttribute("persons", personModel.findAll());
        request.getRequestDispatcher("/Persons.jsp").forward(request, response);

    }

}
