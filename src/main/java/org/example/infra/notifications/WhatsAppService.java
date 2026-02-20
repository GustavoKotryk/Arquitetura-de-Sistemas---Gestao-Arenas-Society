package org.example.infra.notifications;

import org.example.domain.INotificacaoService;
import org.example.domain.Reserva;

public class WhatsAppService implements INotificacaoService {
    @Override
    public void enviarConfirmacao(Reserva reserva) {
        System.out.println(" WhatsApp enviado para " + reserva.getClienteTelefone());
        System.out.println("Reserva confirmada para " + reserva.getDataHora());
    }

    @Override
    public void enviarCancelamento(Reserva reserva) {
        System.out.println(" WhatsApp: Reserva cancelada - " + reserva.getId());
    }

    @Override
    public void enviarLembrete(Reserva reserva) {
        System.out.println(" WhatsApp: Lembrete - Sua reserva é amanhã!");
    }
}