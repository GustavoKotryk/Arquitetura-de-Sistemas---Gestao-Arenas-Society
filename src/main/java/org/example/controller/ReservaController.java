package org.example.controller;

import org.example.domain.Reserva;
import org.example.service.ReservaService;

import java.sql.SQLException;
import java.util.List;

public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService)throws SQLException{
        this.reservaService= reservaService;
    }

    public String agendarReserva(String nome, String email, String telefone, double valorTotal)throws SQLException{
        try {
            Reserva novaReserva = new Reserva(nome, email, telefone, valorTotal);
            Reserva reservaSalva = reservaService.criarReserva(novaReserva);
            return "Reserva criada com sucesso! ID: " + reservaSalva.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reserva> todasReservas(){
        try {
            return reservaService.buscarReserva(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
