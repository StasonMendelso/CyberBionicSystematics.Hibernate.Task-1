package org.stanislav.dao;

import org.stanislav.models.Employee;

import java.util.List;

/**
 * @author Stanislav Hlova
 */
public interface EmployeeDao {

    List<Employee> readEmployeesWithContactData();

    List<Employee> readSingleEmployeesWithContactData();

    List<Employee> readManagersWithContactData();
}
