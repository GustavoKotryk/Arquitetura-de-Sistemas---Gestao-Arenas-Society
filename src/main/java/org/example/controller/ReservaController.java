package org.example.controller;

import org.example.domain.Reserva;
import org.example.service.ReservaService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    public String agendarReserva(String nome, String telefone, LocalDateTime dataHora, int duracaoHoras) {
        try {
            Reserva reservaSalva = reservaService.criarReserva(nome, telefone, dataHora, duracaoHoras);
            return "Reserva criada com sucesso! ID: " + reservaSalva.getId();
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

    public String cancelarReserva(Long id) {
        try {
            reservaService.cancelarReserva(id);
            return "Reserva cancelada com sucesso!";
        } catch (Exception e) {
            return "Erro ao cancelar reserva: " + e.getMessage();
        }
    }

    public Reserva buscarReserva(Long id) {
        try {
            return reservaService.buscarReserva(id);
        } catch (Exception e) {
            throw new RuntimeException("Reserva não encontrada: " + e.getMessage());
        }
    }
}