package org.example;

import org.example.infra.database.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

public class DescobrirColunas {
    public static void main(String[] args) {
        try (Connection conn = Conexao.conectar()) {
            DatabaseMetaData metaData = conn.getMetaData();

            System.out.println("=== COLUNAS DA TABELA Reserva ===\n");

            ResultSet columns = metaData.getColumns(null, null, "Reserva", null);

            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String columnType = columns.getString("TYPE_NAME");
                System.out.println("Coluna: " + columnName + " (" + columnType + ")");
            }

            System.out.println("\n=== FIM ===");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}