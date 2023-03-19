package org.stanislav.dao.mySql;

import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.EmployeeDao;
import org.stanislav.models.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
public class MySqlEmployeeDao implements EmployeeDao {
    private final DatabaseConfigurer databaseConfigurer;

    public MySqlEmployeeDao(DatabaseConfigurer databaseConfigurer) {

        this.databaseConfigurer = databaseConfigurer;
    }

    protected static class Query {
        public static final String READ_EMPLOYEES_WITH_CONTACT_DATA = "SELECT telephone_number, address FROM employees JOIN employee_personal_details ON employees.id = employee_personal_details.person_id;";
        public static final String READ_SINGLE_EMPLOYEES_WITH_CONTACT_DATA_AND_DATE_OF_BIRTH = "SELECT date_of_birthday,telephone_number FROM employees JOIN employee_personal_details ON employees.id = employee_personal_details.person_id JOIN marital_status ON employee_personal_details.marital_status_id = marital_status.id WHERE marital_status='Single';";
        public static final String READ_MANAGERS_WITH_CONTACT_DATA_AND_DATE_OF_BIRTH = "SELECT date_of_birthday,telephone_number FROM employees JOIN employee_personal_details ON employees.id = employee_personal_details.person_id JOIN employee_job_details ON employees.id = employee_job_details.person_id JOIN positions ON employee_job_details.position_id = positions.id WHERE position='Manager'";
    }

    @Override
    public List<Employee> readEmployeesWithContactData() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = databaseConfigurer.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.READ_EMPLOYEES_WITH_CONTACT_DATA)) {
            Employee.Builder<?> builder = new Employee.Builder<>();
            while (resultSet.next()) {
                String telephoneNumber = resultSet.getString("telephone_number");
                String address = resultSet.getString("address");
                employees.add(builder
                        .telephoneNumber(telephoneNumber)
                        .address(address)
                        .build());
            }
        } catch (SQLException exception) {
            System.err.println("Can't read single employees with contact data and date of birthday.");
            throw new RuntimeException(exception);
        }
        return employees;
    }

    @Override
    public List<Employee> readSingleEmployeesWithContactData() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = databaseConfigurer.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.READ_SINGLE_EMPLOYEES_WITH_CONTACT_DATA_AND_DATE_OF_BIRTH)) {
            Employee.Builder<?> builder = new Employee.Builder<>();
            while (resultSet.next()) {
                String telephoneNumber = resultSet.getString("telephone_number");
                Date dateOfBirth = resultSet.getDate("date_of_birthday");
                employees.add(builder
                        .telephoneNumber(telephoneNumber)
                        .dateOfBirthday(dateOfBirth)
                        .build());
            }
        } catch (SQLException exception) {
            System.err.println("Can't read employees with contact data.");
            throw new RuntimeException(exception);
        }
        return employees;
    }

    @Override
    public List<Employee> readManagersWithContactData() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = databaseConfigurer.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Query.READ_MANAGERS_WITH_CONTACT_DATA_AND_DATE_OF_BIRTH)) {
            Employee.Builder<?> builder = new Employee.Builder<>();
            while (resultSet.next()) {
                String telephoneNumber = resultSet.getString("telephone_number");
                Date dateOfBirth = resultSet.getDate("date_of_birthday");
                employees.add(builder
                        .telephoneNumber(telephoneNumber)
                        .dateOfBirthday(dateOfBirth)
                        .build());
            }
        } catch (SQLException exception) {
            System.err.println("Can't read managers with contact data.");
            throw new RuntimeException(exception);
        }
        return employees;
    }
}
