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

        String query = String.format("INSERT INTO person (firstname, lastname, birthdate) VALUES ('%s', '%s', '%s')",
                person.getFirstName(), person.getLastName(), person.getBirthdate());

        Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, "Query save ::: {0}", query);

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

        String query = String.format("UPDATE person SET firstname = '%s', lastname = '%s', birthdate = '%s' WHERE id = %s",
                person.getFirstName(), person.getLastName(), person.getBirthdate(), person.getId());
        Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, "Query update ::: {0}", query);
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
        String query = String.format("SELECT * FROM person WHERE id = %s", id);
        Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, "Query findById ::: {0}", query);
        ResultSet result = null;
        Person person = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                person = new Person(result.getInt("id"), result.getString("firstname"), result.getString("lastname"), result.getDate("birthdate"));
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

        String query = "SELECT * FROM person";
        Logger.getLogger(PersonModel.class.getName()).log(Level.SEVERE, "Query findAll ::: {0}", query);
        ResultSet result = null;
        List<Person> persons = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            while (result.next()) {
                persons.add(new Person(result.getInt("id"), result.getString("firstname"), result.getString("lastname"), result.getDate("birthdate")));
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

        String query = String.format("DELETE FROM person WHERE id = %s", id);

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
