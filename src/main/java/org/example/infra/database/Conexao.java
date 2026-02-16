package org.example.infra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static final String URL = "";
    public static final String USER = "";
    public static final String PASSWORD = "";

    public static Connection conectar()throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
