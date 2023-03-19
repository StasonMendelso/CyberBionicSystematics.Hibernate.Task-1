package org.stanislav.dao;

/**
 * @author Stanislav Hlova
 */
public abstract class DAOFactory {
    public abstract EmployeeDao createEmployeeDao();
}
