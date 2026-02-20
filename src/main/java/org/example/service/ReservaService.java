package org.example.service;

import org.example.domain.Reserva;

import java.sql.SQLException;
import java.util.List;

public interface ReservaService {
    Reserva criarReserva(Reserva reserva) throws SQLException;
    Reserva buscarPorId(int id) throws SQLException;
    List<Reserva> buscarReserva(int id) throws SQLException;
    void atualizarReserva(Reserva reserva) throws SQLException;
    void deletarReserva(int id) throws SQLException;
}
