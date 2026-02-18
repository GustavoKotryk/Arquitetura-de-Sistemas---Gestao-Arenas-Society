package org.example.infra.repository;

import org.example.domain.Reserva;
import org.example.infra.database.Conexao;

import java.sql.*;

public class MemoryReservaRepository{


    public Reserva criarReserva(Reserva reserva)throws SQLException{
        String query = """
                INSERT INTO Reserva (nome, email, telefone, valorTotal;)
                VALUES (?,?,?,?)
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, reserva.getNome());
            stmt.setString(2, reserva.getEmail());
            stmt.setString(3, reserva.getTelefone());
            stmt.setDouble(4, reserva.getValorTotal());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()){
                reserva.setId(rs.getInt(1));
            }
        }
        return reserva;
    }

}
