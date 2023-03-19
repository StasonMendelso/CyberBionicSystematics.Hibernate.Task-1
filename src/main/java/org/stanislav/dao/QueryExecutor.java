package org.stanislav.dao;

/**
 * @author Stanislav Hlova
 */
public interface QueryExecutor {
    void executeQuery(String query);

    int executeUpdate(String query);
}
