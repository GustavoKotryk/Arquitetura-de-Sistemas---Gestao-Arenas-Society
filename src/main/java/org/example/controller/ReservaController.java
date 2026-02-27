package org.example.controller;

import org.example.domain.Arena;
import org.example.domain.Reserva;
import org.example.service.ReservaService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaController {

    private final ReservaService reservaService;

    private final List<Arena> arenas = List.of(
            new Arena(1L, "Arena Central", "Quadra society coberta - grama sintética"),
            new Arena(2L, "Arena Norte",   "Quadra society aberta - iluminação LED"),
            new Arena(3L, "Arena Sul",     "Quadra coberta com vestiário completo"),
            new Arena(4L, "Arena Leste",   "Quadra futsal + society - multiuso")
    );

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    public List<Arena> listarArenas() {
        return arenas;
    }

    public Arena buscarArena(Long id) {
        return arenas.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Arena não encontrada com ID: " + id));
    }

    public String agendarReserva(Long arenaId, String nome, String telefone,
                                 LocalDateTime dataHora, int duracaoHoras) throws SQLException {


        try {
            // Valida que a arena existe antes de delegar ao service
            reservaService.validarDataPassado(dataHora);
            buscarArena(arenaId);
            Reserva salva = reservaService.criarReserva(nome, telefone, dataHora, duracaoHoras);
            return "Reserva criada com sucesso! ID: " + salva.getId()
                    + " | Preço: R$ " + String.format("%.2f", salva.getPrecoTotal());
        } catch (Exception e) {
            return "Erro ao criar reserva: " + e.getMessage();
        }
    }

    public List<Reserva> listarTodasReservas() {
        try {
            return reservaService.listarReservas();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar reservas: " + e.getMessage());
        }
    }
}