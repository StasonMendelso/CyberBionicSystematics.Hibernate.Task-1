package org.stanislav.dao.mySql;

import org.stanislav.dao.DAOFactory;
import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.EmployeeDao;

/**
 * @author Stanislav Hlova
 */
public class MySqlDAOFactory extends DAOFactory {
    private final MySqlEmployeeDao mySqlUserDao;

    public MySqlDAOFactory(DatabaseConfigurer databaseConfigurer) {
        this.mySqlUserDao = new MySqlEmployeeDao(databaseConfigurer);
    }

    @Override
    public EmployeeDao createEmployeeDao() {
        return mySqlUserDao;
    }
}
