package org.example.service;

import org.example.domain.IReservaRepository;
import org.example.domain.Reserva;
import org.example.infra.repository.SqlReservaRepository;

import java.sql.SQLException;
import java.util.List;

public class ReservaServiceImpl implements ReservaService {


    private final IReservaRepository reservaRepository;

    public ReservaServiceImpl(IReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva criarReserva(Reserva reserva) throws SQLException {
        return reservaRepository.criarReserva(reserva);
    }

    @Override
    public Reserva buscarPorId(int id) throws SQLException {
        return reservaRepository.buscarPorId(id);
    }

    @Override
    public List<Reserva> buscarReserva(int id) throws SQLException {
        return reservaRepository.buscarTodas();
    }

    @Override
    public void atualizarReserva(Reserva reserva) throws SQLException {
    reservaRepository.atualizar(reserva);
    }

    @Override
    public void deletarReserva(int id) throws SQLException {
    reservaRepository.deletar(id);
    }
}
