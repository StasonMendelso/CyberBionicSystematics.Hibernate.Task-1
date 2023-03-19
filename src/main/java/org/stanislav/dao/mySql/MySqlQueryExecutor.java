package org.stanislav.dao.mySql;

import org.stanislav.dao.DatabaseConfigurer;
import org.stanislav.dao.QueryExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Stanislav Hlova
 */
public class MySqlQueryExecutor implements QueryExecutor {
    private final DatabaseConfigurer databaseConfigurer;

    public MySqlQueryExecutor(DatabaseConfigurer databaseConfigurer) {

        this.databaseConfigurer = databaseConfigurer;
    }

    @Override
    public void executeQuery(String query) {
        try (Connection connection = databaseConfigurer.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("|%30s", resultSetMetaData.getColumnName(i));
            }
            System.out.print("|");
            while (resultSet.next()) {
                System.out.println();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("|%30s", resultSet.getObject(i));
                }
                System.out.print("|");
            }
            System.out.println();
        } catch (SQLException exception) {
            System.out.println("Can't execute query.");
            throw new RuntimeException(exception);
        }
    }

    @Override
    public int executeUpdate(String query) {
        try (Connection connection = databaseConfigurer.getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException exception) {
            System.out.println("Can't execute query.");
            throw new RuntimeException(exception);
        }
    }
}
