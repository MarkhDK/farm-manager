package org.markh.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/farm_planner",
                "farm_dev",
                "1318"
        );
    }
}
