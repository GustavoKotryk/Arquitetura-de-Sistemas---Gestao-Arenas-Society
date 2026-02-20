package org.example.domain;

import java.time.LocalDateTime;

public class Reserva {
    private Long id;
    private String clienteNome;
    private String clienteTelefone;
    private LocalDateTime dataHora;
    private int duracaoHoras;
    private double precoTotal;
    private StatusReserva status;

    // Construtores
    public Reserva() {}

    public Reserva(String clienteNome, String clienteTelefone,
                   LocalDateTime dataHora, int duracaoHoras) {
        this.clienteNome = clienteNome;
        this.clienteTelefone = clienteTelefone;
        this.dataHora = dataHora;
        this.duracaoHoras = duracaoHoras;
        this.status = StatusReserva.PENDENTE;
    }

    // Getters e Setters (gerar todos)

    public enum StatusReserva {
        PENDENTE, CONFIRMADA, CANCELADA, FINALIZADA
    }
}