package org.example.infra.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static final String URL = "jdbc:mysql://gestao-arena-society-kotryk.l.aivencloud.com:19626/defaultdb?useSSL=true&trustServerCertificate=true&allowPublicKeyRetrieval=true";
    public static final String USER = "avnadmin";
    public static final String PASSWORD = "AVNS_K9v0DXJ_yDfMJpKUS0f";

    public static Connection conectar()throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
