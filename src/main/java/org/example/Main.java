package org.example;

import org.example.controller.ReservaController;
import org.example.infra.notifications.WhatsAppService;
import org.example.infra.repository.SqlReservaRepository;
import org.example.service.ReservaService;
import org.example.view.ReservaView;

public class Main {
    public static void main(String[] args) {
        var repository        = new SqlReservaRepository();
        var notificacaoService = new WhatsAppService();
        var reservaService    = new ReservaService(repository, notificacaoService);

        var reservaController = new ReservaController(reservaService);
        var view              = new ReservaView(reservaController);
        view.iniciar();
    }
}