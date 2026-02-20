package org.example.service;

import org.example.domain.*;
import java.time.LocalDateTime;
import java.util.List;

public class ReservaService {
    private final IReservaRepository repository;
    private final INotificacaoService notificacaoService;
    private ICalculoPrecoStrategy strategy;

    public ReservaService(IReservaRepository repository,
                          INotificacaoService notificacaoService) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
    }

    public Reserva criarReserva(String clienteNome, String clienteTelefone,
                                LocalDateTime dataHora, int duracaoHoras) {
        // Escolher estratégia baseada no horário
        definirStrategy(dataHora);

        double preco = strategy.calcularPreco(dataHora, duracaoHoras);

        Reserva reserva = new Reserva(clienteNome, clienteTelefone, dataHora, duracaoHoras);
        reserva.setPrecoTotal(preco);

        Reserva salva = repository.salvar(reserva);
        notificacaoService.enviarConfirmacao(salva);

        return salva;
    }

    private void definirStrategy(LocalDateTime dataHora) {
        int hora = dataHora.getHour();
        if (hora >= 18 || hora <= 6) { // Horário de pico
            this.strategy = new org.example.infra.strategies.PrecoHorarioPicoStrategy();
        } else {
            this.strategy = new org.example.infra.strategies.PrecoDiaComumStrategy();
        }
    }

    public List<Reserva> listarReservas() {
        return repository.listarTodas();
    }

    public void cancelarReserva(Long id) {
        repository.buscarPorId(id).ifPresent(reserva -> {
            repository.remover(id);
            notificacaoService.enviarCancelamento(reserva);
        });
    }
}