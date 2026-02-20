package org.example.infra.repository;

import org.example.domain.IReservaRepository;
import org.example.domain.Reserva;
import org.example.infra.database.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqlReservaRepository implements IReservaRepository {


    @Override
    public Reserva criarReserva(Reserva reserva) throws SQLException {
        String query = """
                INSERT INTO Reserva (nome, email, telefone, valorTotal)
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


    @Override
    public Reserva buscarPorId(int id) throws SQLException {
        String query = """
                SELECT id, nome, email, telefone, valorTotal
                FROM Reserva
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar();) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Reserva(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getDouble("valorTotal")
                );}
        }
        return null;
    }

    @Override
    public List<Reserva> buscarTodas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();

        String query = """
                SELECT id, nome, email, telefone, valorTotal
                FROM Reserva 
                """;
        try (Connection conn = Conexao.conectar();){
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                double valorTotal = rs.getDouble("valorTotal");
                reservas.add(new Reserva(id, nome, email, telefone, valorTotal));
            }
        }
        return reservas;
    }

    @Override
    public void atualizar(Reserva reserva) throws SQLException {
        String query = """
                UPDATE Reserva
                SET nome =?, email= ?, telefone = ?, valorTotal = ?
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, reserva.getNome());
            stmt.setString(2, reserva.getEmail());
            stmt.setString(3, reserva.getTelefone());
            stmt.setDouble(4, reserva.getValorTotal());
            stmt.setInt(5, reserva.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deletar(int id) throws SQLException {
        String query = """
                DELETE FROM Reserva
                WHERE id =?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }



}
