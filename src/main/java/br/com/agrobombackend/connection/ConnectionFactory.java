package br.com.agrobombackend.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_agro_bom?useTimezone=true&serverTimezone=UTC",
                "root",
                "5557"
            );

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }
}
