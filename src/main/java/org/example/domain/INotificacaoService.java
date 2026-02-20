package org.example.domain;

public interface INotificacaoService {
    void enviarConfirmacao(Reserva reserva);
    void enviarCancelamento(Reserva reserva);
    void enviarLembrete(Reserva reserva);
}