package org.example.domain;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IReservaRepository {
    Reserva salvar(Reserva reserva) throws SQLException;
    Optional<Reserva> buscarPorId(Long id) throws SQLException;
    List<Reserva> listarTodas() throws SQLException;
    void remover(Long id) throws SQLException;
}