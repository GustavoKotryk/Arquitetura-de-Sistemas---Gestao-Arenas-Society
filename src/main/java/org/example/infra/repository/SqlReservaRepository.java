package org.example.infra.repository;

import org.example.domain.IReservaRepository;
import org.example.domain.Reserva;
import org.example.infra.database.Conexao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqlReservaRepository implements IReservaRepository {

    @Override
    public Reserva salvar(Reserva reserva) throws SQLException {
        // Adaptado para as colunas que existem: id, nome, email, telefone, valorTotal
        String query = """
                INSERT INTO Reserva (nome, email, telefone, valorTotal)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, reserva.getClienteNome());     // nome
            stmt.setString(2, "email@exemplo.com");          // email (ajustar depois)
            stmt.setString(3, reserva.getClienteTelefone()); // telefone
            stmt.setDouble(4, reserva.getPrecoTotal());      // valorTotal

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                reserva.setId(rs.getLong(1));
            }
        }
        return reserva;
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) throws SQLException {
        String query = """
                SELECT id, nome, telefone, valorTotal
                FROM Reserva
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToReserva(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Reserva> listarTodas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String query = """
                SELECT id, nome, telefone, valorTotal
                FROM Reserva
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservas.add(mapResultSetToReserva(rs));
            }
        }
        return reservas;
    }

    @Override
    public void remover(Long id) throws SQLException {
        String query = """
                DELETE FROM Reserva
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Reserva mapResultSetToReserva(ResultSet rs) throws SQLException {
        // Como não temos dataHora e duracaoHoras no banco, usamos valores padrão
        Reserva reserva = new Reserva(
                rs.getString("nome"),           // nome
                rs.getString("telefone"),       // telefone
                LocalDateTime.now(),            // dataHora padrão (hoje)
                1                                // duracaoHoras padrão (1 hora)
        );
        reserva.setId(rs.getLong("id"));
        reserva.setPrecoTotal(rs.getDouble("valorTotal"));
        reserva.setStatus(Reserva.StatusReserva.PENDENTE); // status padrão
        return reserva;
    }
}