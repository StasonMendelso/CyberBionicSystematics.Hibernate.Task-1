package org.stanislav.dao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Stanislav Hlova
 */
public class QueryFileExecutor {
    private final QueryExecutor queryExecutor;

    public QueryFileExecutor(QueryExecutor queryExecutor) {
        this.queryExecutor = queryExecutor;
    }

    public void execute(String pathToFileWithQueries) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFileWithQueries))) {
            String query = bufferedReader.readLine();
            while (query != null) {
                System.out.println("Query: " + query);
                String queryOperator = query.split(" ")[0];
                if (queryOperator == null || queryOperator.isBlank()) {
                    throw new RuntimeException(String.format("Can't execute next line: %s", query));
                }
                switch (queryOperator) {
                    case "SELECT" -> queryExecutor.executeQuery(query);
                    case "INSERT", "UPDATE", "DELETE" -> {
                        int countOfAffectedRows = queryExecutor.executeUpdate(query);
                        System.out.printf("Query affected %d rows!\n", countOfAffectedRows);
                    }
                    default ->
                            throw new RuntimeException(String.format("The next query operator isn't supported: %s, in query: %s", queryOperator, query));
                }

                query = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        System.out.println("All queries were executed successfully!");
    }
}
