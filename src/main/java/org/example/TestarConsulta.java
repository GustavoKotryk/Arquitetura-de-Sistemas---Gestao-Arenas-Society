package org.example;

import org.example.infra.database.Conexao;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestarConsulta {
    public static void main(String[] args) {
        String sql = "SELECT * FROM Reserva";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Conectou! Verificando dados...");

            if (rs.next()) {
                System.out.println("Tem dados na tabela!");
                System.out.println("ID: " + rs.getLong("id"));
                System.out.println("ClienteNome: " + rs.getString("cliente_nome")); // ALTERADO AQUI
                System.out.println("ClienteTelefone: " + rs.getString("cliente_telefone")); // ALTERADO
                System.out.println("DataHora: " + rs.getTimestamp("data_hora")); // ALTERADO
            } else {
                System.out.println("Tabela vazia - sem dados");
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}