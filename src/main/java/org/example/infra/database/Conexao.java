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

    public static void testarConexao() {
        System.out.println("Testando conexão com o banco de dados...");
        System.out.println("URL: " + URL);
        System.out.println("Usuário: " + USER);

        try (Connection conn = conectar()) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            System.out.println("Banco: railway");
            System.out.println("Host: yamanote.proxy.rlwy.net:25133");
            System.out.println("Driver: " + conn.getMetaData().getDriverName());
            System.out.println("Banco version: " + conn.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            System.err.println("Detalhes do erro:");
            e.printStackTrace();
        }
    }
}