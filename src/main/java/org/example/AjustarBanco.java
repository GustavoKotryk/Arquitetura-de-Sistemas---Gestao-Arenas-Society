package org.example;

import org.example.infra.database.Conexao;
import java.sql.Connection;
import java.sql.Statement;

public class AjustarBanco {
    public static void main(String[] args) {
        String sql = """
            ALTER TABLE Reserva 
            CHANGE COLUMN cliente_nome clienteNome VARCHAR(100),
            CHANGE COLUMN cliente_telefone clienteTelefone VARCHAR(20),
            CHANGE COLUMN data_hora dataHora DATETIME,
            CHANGE COLUMN duracao_horas duracaoHoras INT,
            CHANGE COLUMN preco_total precoTotal DECIMAL(10,2)
        """;

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Colunas renomeadas com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}