package org.example.service;

import org.example.domain.Reserva;
import org.example.infra.repository.MemoryReservaRepository;

import java.sql.SQLException;
import java.util.List;

public class ReservaServiceImpl implements ReservaService {

    MemoryReservaRepository reservaRepository = new MemoryReservaRepository();

    @Override
    public Reserva criarReserva(Reserva reserva) throws SQLException {
        return null;
    }

    @Override
    public Reserva buscarPorId(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Reserva> buscarReserva(int id) throws SQLException {
        return List.of();
    }

    @Override
    public void atualizarReserva(Reserva reserva) throws SQLException {

    }

    @Override
    public void deletarReserva(int id) throws SQLException {

    }
}
