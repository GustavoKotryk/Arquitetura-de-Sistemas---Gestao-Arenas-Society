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

    
    public Reserva() {}

    public Reserva(String clienteNome, String clienteTelefone,
                   LocalDateTime dataHora, int duracaoHoras) {
        this.clienteNome = clienteNome;
        this.clienteTelefone = clienteTelefone;
        this.dataHora = dataHora;
        this.duracaoHoras = duracaoHoras;
        this.status = StatusReserva.PENDENTE;
    }

    public Reserva(int id, String nome, String email, String telefone, double valorTotal) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public String getClienteTelefone() {
        return clienteTelefone;
    }

    public void setClienteTelefone(String clienteTelefone) {
        this.clienteTelefone = clienteTelefone;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(int duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public enum StatusReserva {
        PENDENTE, CONFIRMADA, CANCELADA, FINALIZADA
    }
}