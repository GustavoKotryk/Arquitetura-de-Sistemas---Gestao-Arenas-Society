package org.example.domain;

import java.sql.SQLException;
import java.util.List;

public interface IReservaRepository {

    Reserva criarReserva(Reserva reserva)throws SQLException;

    Reserva buscarPorId(int id) throws SQLException;

    List<Reserva> buscarTodas() throws SQLException;

    void atualizar(Reserva reserva) throws SQLException;

    void deletar(int id) throws SQLException;


}
