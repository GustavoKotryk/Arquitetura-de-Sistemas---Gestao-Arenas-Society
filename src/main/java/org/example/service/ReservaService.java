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
        definirStrategy(dataHora);

        double preco = strategy.calcularPreco(dataHora, duracaoHoras);

        Reserva reserva = new Reserva(clienteNome, clienteTelefone, dataHora, duracaoHoras);
        reserva.setPrecoTotal(preco);

        try {
            Reserva salva = repository.salvar(reserva);
            notificacaoService.enviarConfirmacao(salva);
            return salva;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar reserva: " + e.getMessage());
        }
    }

    private void definirStrategy(LocalDateTime dataHora) {
        int hora = dataHora.getHour();
        if (hora >= 18 || hora <= 6) {
            this.strategy = new org.example.infra.strategies.PrecoHorarioPicoStrategy();
        } else {
            this.strategy = new org.example.infra.strategies.PrecoDiaComumStrategy();
        }
    }

    public List<Reserva> listarReservas() {
        try {
            return repository.listarTodas();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar reservas: " + e.getMessage());
        }
    }

    public void cancelarReserva(Long id) {
        try {
            repository.buscarPorId(id).ifPresent(reserva -> {
                try {
                    repository.remover(id);
                    notificacaoService.enviarCancelamento(reserva);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao cancelar reserva: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar reserva para cancelamento: " + e.getMessage());
        }
    }

    public Reserva buscarReserva(Long id) {
        try {
            return repository.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar reserva: " + e.getMessage());
        }
    }
}