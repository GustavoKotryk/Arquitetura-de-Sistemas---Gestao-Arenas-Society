package org.example.view;

import org.example.infra.database.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("TESTANDO CONEXÃO COM O BANCO NA NUVEM\n");

        Conexao.testarConexao();

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            System.out.println("\nTabelas no banco:");
            boolean temTabelas = false;
            while (rs.next()) {
                System.out.println("   - " + rs.getString(1));
                temTabelas = true;
            }

            if (!temTabelas) {
                System.out.println("   Nenhuma tabela encontrada!");
                System.out.println("\nCriando tabela Reserva...");

                String createTable = """
                    CREATE TABLE IF NOT EXISTS Reserva (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        clienteNome VARCHAR(100) NOT NULL,
                        clienteTelefone VARCHAR(20) NOT NULL,
                        dataHora DATETIME NOT NULL,
                        duracaoHoras INT NOT NULL,
                        precoTotal DECIMAL(10,2) NOT NULL,
                        status VARCHAR(20) NOT NULL
                    )
                    """;
                stmt.execute(createTable);
                System.out.println("✅ Tabela Reserva criada com sucesso!");
            }

        } catch (Exception e) {
            System.err.println("Erro na consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}