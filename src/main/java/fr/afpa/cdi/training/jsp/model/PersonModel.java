package fr.afpa.cdi.training.jsp.model;

import fr.afpa.cdi.training.jsp.bean.Person;
import fr.afpa.cdi.training.jsp.jdbc.ConnectionFactory;
import fr.afpa.cdi.training.jsp.jdbc.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cdi420
 */
public class PersonModel implements CrudModel<Person> {

    private Connection connection;
    private Statement statement;

    public PersonModel() {

    }

    @Override
    public boolean save(Person person) {
        int row = 0;

        String query = String.format("INSERT INTO person (first_name, last_name, birthdate) VALUES (%s, %s, %s)",
                person.getFirstName(), person.getLastName(), person.getBirthdate());

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            row = statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionUtil.close(statement);
            ConnectionUtil.close(connection);
        }
        return row > 0;
    }

    @Override
    public boolean update(Person person) {
        int row = 0;

        String query = String.format("UPDATE person SET first_name = %s, last_name = %s, birthdate = %s WHERE id = %s",
                person.getFirstName(), person.getLastName(), person.getBirthdate(), person.getId());

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            row = statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionUtil.close(statement);
            ConnectionUtil.close(connection);
        }
        return row > 0;
    }

    @Override
    public Person findById(Integer id) {
        String query = String.format("SELECT id, first_name, last_name, birthdate WHERE id = %s", id);
        ResultSet result = null;
        Person person = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                person = new Person(result.getInt("id"), result.getString("first_name"), result.getString("last_name"), result.getDate("birthdate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionUtil.close(result);
            ConnectionUtil.close(statement);
            ConnectionUtil.close(connection);
        }
        return person;
    }

    @Override
    public List<Person> findAll() {

        String query = String.format("SELECT id, first_name, last_name, birthdate");
        ResultSet result = null;
        List<Person> persons = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                persons.add(new Person(result.getInt("id"), result.getString("first_name"), result.getString("last_name"), result.getDate("birthdate")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionUtil.close(result);
            ConnectionUtil.close(statement);
            ConnectionUtil.close(connection);
        }
        return persons;
    }

    @Override
    public boolean delete(Person person) {
        return delete(person.getId());
    }

    @Override
    public boolean delete(Integer id) {
        int row = 0;

        String query = String.format("DELETE FROM WHERE id = %s", id);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            row = statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionUtil.close(statement);
            ConnectionUtil.close(connection);
        }
        return row > 0;
    }

}
