package org.stanislav.dao.mySql;

import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.EmployeeDao;
import org.stanislav.models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        public static final String INSERT_EMPLOYEE = "INSERT INTO employees(name, telephone_number) VALUES (?,?);";
        public static final String INSERT_EMPLOYEE_PERSONAL_DETAILS = "INSERT INTO employee_personal_details(person_id, marital_status_id, date_of_birthday, address) VALUES (?,?,?,?)";
        public static final String INSERT_EMPLOYEE_JOB_DETAILS = "INSERT INTO employee_job_details(person_id, salary, position_id) VALUES (?,?,?)";
        public static final String READ_ID_JOB_POSITION = "SELECT id FROM positions WHERE position = ?";
        public static final String READ_ID_MARITAL_STATUS = "SELECT id FROM marital_status WHERE marital_status = ?";
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

    @Override
    public boolean create(Employee employee) {
        Connection connection = null;
        try {
            connection = databaseConfigurer.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            try (PreparedStatement insertEmployeeStatement = connection.prepareStatement(Query.INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertPersonalDetails = connection.prepareStatement(Query.INSERT_EMPLOYEE_PERSONAL_DETAILS);
                 PreparedStatement insertJobDetails = connection.prepareStatement(Query.INSERT_EMPLOYEE_JOB_DETAILS);
                 PreparedStatement selectJobPositionId = connection.prepareStatement(Query.READ_ID_JOB_POSITION);
                 PreparedStatement selectMaritalStatusId = connection.prepareStatement(Query.READ_ID_MARITAL_STATUS)) {
                insertEmployeeStatement.setString(1, employee.getName());
                insertEmployeeStatement.setString(2, employee.getTelephoneNumber());
                insertEmployeeStatement.executeUpdate();
                int personId;
                try (ResultSet resultSet = insertEmployeeStatement.getGeneratedKeys()) {
                    if (!resultSet.next()) {
                        connection.rollback();
                        return false;
                    }
                    personId = resultSet.getInt(1);
                }
                selectMaritalStatusId.setString(1, employee.getMaritalStatus().getName());
                int maritalStatusId;
                try (ResultSet resultSet = selectMaritalStatusId.executeQuery()) {
                    if (!resultSet.next()) {
                        connection.rollback();
                        return false;
                    }
                    maritalStatusId = resultSet.getInt("id");
                }
                insertPersonalDetails.setInt(1, personId);
                insertPersonalDetails.setInt(2, maritalStatusId);
                insertPersonalDetails.setDate(3, new java.sql.Date(employee.getDateOfBirthday().getTime()));
                insertPersonalDetails.setString(4, employee.getAddress());
                if (insertPersonalDetails.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }
                selectJobPositionId.setString(1, employee.getPosition().getName());
                int positionId;
                try (ResultSet resultSet = selectJobPositionId.executeQuery()) {
                    if (!resultSet.next()) {
                        connection.rollback();
                        return false;
                    }
                    positionId = resultSet.getInt("id");
                }
                insertJobDetails.setInt(1, personId);
                insertJobDetails.setBigDecimal(2, employee.getSalary());
                insertJobDetails.setInt(3, positionId);

                connection.commit();
                return true;
            }
        } catch (SQLException exception) {
            System.out.println("Can't create a new record for employee.");
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException exception1) {
                    System.out.println("Can't rollback or close connection.");
                    throw new RuntimeException(exception1);
                }
            }
            throw new RuntimeException(exception);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exception) {
                    System.out.println("Can't close connection, " + exception.getMessage());
                }
            }
        }
    }
}
