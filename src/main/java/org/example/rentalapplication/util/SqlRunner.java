package org.example.rentalapplication.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


@Component
public class SqlRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public SqlRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        executeSqlFile("rental_application_tool_type.sql");
        executeSqlFile("rental_application_tool.sql");
        executeSqlFile("rental_application_tool_seq.sql");
        executeSqlFile("rental_application_tool_type_seq.sql");
    }
    private void executeSqlFile(String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String sql = reader.lines().collect(Collectors.joining("\n"));
            // Split SQL statements by semicolon
            String[] statements = sql.split(";");
            // Execute each SQL statement
            for (String statement : statements) {
                jdbcTemplate.execute(statement.trim());
            }
            System.out.println("SQL file " + fileName + " executed successfully");
        } catch (Exception e) {
            System.out.println("Error executing SQL file " + fileName + ": " + e.getMessage());
        }
    }
}
