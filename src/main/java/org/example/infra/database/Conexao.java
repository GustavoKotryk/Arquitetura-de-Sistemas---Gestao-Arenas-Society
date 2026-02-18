package org.example.infra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static final String URL = "teste";
    public static final String USER = "teste";
    public static final String PASSWORD = "teste";

    public static Connection conectar()throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
