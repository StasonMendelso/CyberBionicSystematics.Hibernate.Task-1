package org.stanislav.dao.mySql;

import org.stanislav.dao.DAOFactory;
import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.EmployeeDao;
import org.stanislav.dao.QueryExecutor;

/**
 * @author Stanislav Hlova
 */
public class MySqlDAOFactory extends DAOFactory {
    private final MySqlEmployeeDao mySqlUserDao;
    private final MySqlQueryExecutor mySqlQueryFileExecutor;

    public MySqlDAOFactory(DatabaseConfigurer databaseConfigurer) {
        this.mySqlUserDao = new MySqlEmployeeDao(databaseConfigurer);
        mySqlQueryFileExecutor = new MySqlQueryExecutor(databaseConfigurer);
    }

    @Override
    public EmployeeDao createEmployeeDao() {
        return mySqlUserDao;
    }

    @Override
    public QueryExecutor createQueryExecutor() {
        return mySqlQueryFileExecutor;
    }
}
