package org.example.infra.database;

import org.example.infra.database.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("🔍 TESTANDO CONEXÃO COM O BANCO NA NUVEM\n");


        Conexao.testarConexao();


        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            System.out.println("\nTabelas no banco:");
            while (rs.next()) {
                System.out.println("   - " + rs.getString(1));
            }

        } catch (Exception e) {
            System.err.println("Erro na consulta: " + e.getMessage());
        }
    }
}