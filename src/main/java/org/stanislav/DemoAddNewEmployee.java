package org.stanislav;

import org.stanislav.dao.DAOFactory;
import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.EmployeeDao;
import org.stanislav.dao.mySql.MySqlDAOFactory;
import org.stanislav.models.Employee;
import org.stanislav.models.MaritalStatus;
import org.stanislav.models.Position;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Stanislav Hlova
 */
public class DemoAddNewEmployee {
    public static void main(String[] args) {
        DatabaseConfigurer databaseConfigurer = new DatabaseConfigurer();
        DAOFactory daoFactory = new MySqlDAOFactory(databaseConfigurer);
        EmployeeDao employeeDao = daoFactory.createEmployeeDao();

        Employee employee = getEmployee();

        boolean status = employeeDao.create(employee);
        if (status) {
            System.out.println("New employee was added to the database!");
        } else {
            System.out.println("New employee wasn't added to the database!");
        }
    }

    private static Employee getEmployee() {
        return new Employee.Builder<>()
                .name("Robby")
                .address("test address")
                .dateOfBirthday(new Date())
                .maritalStatus(MaritalStatus.SINGLE)
                .position(Position.WORKER)
                .salary(BigDecimal.valueOf(6500))
                .telephoneNumber("+38000000000")
                .build();
    }
}
