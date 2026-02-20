package org.example;

import org.example.domain.Reserva;
import org.example.infra.notifications.WhatsAppService;
import org.example.infra.repository.MemoryReservaRepository;
import org.example.service.ReservaService;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("SISTEMA DE GESTÃO ARENA SOCIETY\n");

        var repository = new MemoryReservaRepository();
        var notificacaoService = new WhatsAppService();
        var reservaService = new ReservaService(repository, notificacaoService);

        System.out.println("Criando reservas...");

        Reserva reserva1 = reservaService.criarReserva(
                "Engel",
                "69696969696",
                LocalDateTime.now().plusDays(1).withHour(10),
                2
        );

        Reserva reserva2 = reservaService.criarReserva(
                "Kotryk",
                "77777777777",
                LocalDateTime.now().plusDays(1).withHour(20),
                1
        );

        System.out.println("\n Lista de reservas:");
        List<Reserva> reservas = reservaService.listarReservas();
        reservas.forEach(r -> {
            System.out.printf("ID: %d | Cliente: %s | Data: %s | Preço: R$%.2f%n",
                    r.getId(), r.getClienteNome(), r.getDataHora(), r.getPrecoTotal());
        });

        System.out.println("\n Cancelando reserva 1...");
        reservaService.cancelarReserva(1L);

        System.out.println("\n Sistema finalizado!");
    }
}