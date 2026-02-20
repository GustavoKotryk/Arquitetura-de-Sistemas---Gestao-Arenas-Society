package org.example.infra.repository;

import org.example.domain.IReservaRepository;
import org.example.domain.Reserva;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservaRepository implements IReservaRepository {
    private final Map<Long, Reserva> banco = new HashMap<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    @Override
    public Reserva salvar(Reserva reserva) {
        if (reserva.getId() == null) {
            reserva.setId(contadorId.getAndIncrement());
        }
        banco.put(reserva.getId(), reserva);
        return reserva;
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Reserva> listarTodas() {
        return new ArrayList<>(banco.values());
    }

    @Override
    public void remover(Long id) {
        banco.remove(id);
    }
}