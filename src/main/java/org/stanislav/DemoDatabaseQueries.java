package org.stanislav;

import org.stanislav.dao.DAOFactory;
import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.EmployeeDao;
import org.stanislav.dao.mySql.MySqlDAOFactory;
import org.stanislav.models.Employee;

import java.util.List;

/**
 * @author Stanislav Hlova
 */
public class DemoDatabaseQueries {
    public static void main(String[] args) {
        DatabaseConfigurer databaseConfigurer = new DatabaseConfigurer();
        DAOFactory daoFactory = new MySqlDAOFactory(databaseConfigurer);
        EmployeeDao employeeDao = daoFactory.createEmployeeDao();

        printContactsDataAboutEmployees(employeeDao);
        printContactsDataAboutSingleEmployees(employeeDao);
        printContactsDataAboutManagers(employeeDao);
    }

    private static void printContactsDataAboutEmployees(EmployeeDao employeeDao) {
        List<Employee> employees = employeeDao.readEmployeesWithContactData();
        final String pattern = "|%-30s|%-30s|\n";
        System.out.println("Table with employees contact data.");
        System.out.printf(pattern, "Telephone number", "Address");
        for (Employee employee : employees) {
            System.out.printf(pattern, employee.getTelephoneNumber(), employee.getAddress());
        }
    }

    private static void printContactsDataAboutSingleEmployees(EmployeeDao employeeDao) {
        List<Employee> employees = employeeDao.readSingleEmployeesWithContactData();
        final String pattern = "|%-30s|%-30s|\n";
        System.out.println("Table with single employees and their contact data, date of birth.");
        System.out.printf(pattern, "Telephone number", "Date of birth");
        for (Employee employee : employees) {
            System.out.printf(pattern, employee.getTelephoneNumber(), employee.getDateOfBirthday());
        }
    }

    private static void printContactsDataAboutManagers(EmployeeDao employeeDao) {
        List<Employee> employees = employeeDao.readManagersWithContactData();
        final String pattern = "|%-30s|%-30s|\n";
        System.out.println("Table with managers and their contact data, date of birth.");
        System.out.printf(pattern, "Telephone number", "Date of birth");
        for (Employee employee : employees) {
            System.out.printf(pattern, employee.getTelephoneNumber(), employee.getDateOfBirthday());
        }
    }
}
