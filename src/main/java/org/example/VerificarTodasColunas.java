package org.example;

import org.example.infra.database.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class VerificarTodasColunas {
    public static void main(String[] args) {
        String sql = "SELECT * FROM Reserva LIMIT 1";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== COLUNAS DA TABELA Reserva ===\n");

            var metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                String columnType = metaData.getColumnTypeName(i);
                System.out.println("Coluna " + i + ": " + columnName + " (" + columnType + ")");
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}