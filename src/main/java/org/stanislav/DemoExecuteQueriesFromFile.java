package org.stanislav;

import org.stanislav.dao.DAOFactory;
import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.QueryExecutor;
import org.stanislav.dao.QueryFileExecutor;
import org.stanislav.dao.mySql.MySqlDAOFactory;

/**
 * @author Stanislav Hlova
 */
public class DemoExecuteQueriesFromFile {
    public static void main(String[] args) {
        final String pathToFileWithQueries = "database/queries.txt";

        DatabaseConfigurer databaseConfigurer = new DatabaseConfigurer();
        DAOFactory daoFactory = new MySqlDAOFactory(databaseConfigurer);
        QueryExecutor queryExecutor = daoFactory.createQueryExecutor();

        QueryFileExecutor queryFileExecutor = new QueryFileExecutor(queryExecutor);
        queryFileExecutor.execute(pathToFileWithQueries);
    }
}
