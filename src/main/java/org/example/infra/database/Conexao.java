package org.example.infra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static final String URL = "jdbc:mysql://yamanote.proxy.rlwy.net:25133/railway";
    public static final String USER = "root";
    public static final String PASSWORD = "mkYfwmhDZVDKSQikoXfWMveClnbAiMWD";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
