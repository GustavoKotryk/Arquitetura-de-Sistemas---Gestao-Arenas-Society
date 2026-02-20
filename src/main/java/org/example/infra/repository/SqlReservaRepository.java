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
        String query = """
                INSERT INTO Reserva (clienteNome, clienteTelefone, dataHora, duracaoHoras, precoTotal, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, reserva.getClienteNome());
            stmt.setString(2, reserva.getClienteTelefone());
            stmt.setTimestamp(3, Timestamp.valueOf(reserva.getDataHora()));
            stmt.setInt(4, reserva.getDuracaoHoras());
            stmt.setDouble(5, reserva.getPrecoTotal());
            stmt.setString(6, reserva.getStatus().name());

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
                SELECT id, clienteNome, clienteTelefone, dataHora, duracaoHoras, precoTotal, status
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
                SELECT id, clienteNome, clienteTelefone, dataHora, duracaoHoras, precoTotal, status
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
        Reserva reserva = new Reserva(
                rs.getString("clienteNome"),
                rs.getString("clienteTelefone"),
                rs.getTimestamp("dataHora").toLocalDateTime(),
                rs.getInt("duracaoHoras")
        );
        reserva.setId(rs.getLong("id"));
        reserva.setPrecoTotal(rs.getDouble("precoTotal"));
        reserva.setStatus(Reserva.StatusReserva.valueOf(rs.getString("status")));
        return reserva;
    }
}