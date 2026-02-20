package org.example.domain;

import java.util.List;
import java.util.Optional;

public interface IReservaRepository {
    Reserva salvar(Reserva reserva);
    Optional<Reserva> buscarPorId(Long id);
    List<Reserva> listarTodas();
    void remover(Long id);
}