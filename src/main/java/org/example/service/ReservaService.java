package org.example.service;

import org.example.domain.*;
import java.sql.SQLException;
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
        try {
            definirStrategy(dataHora);

            double preco = strategy.calcularPreco(dataHora, duracaoHoras);

            Reserva reserva = new Reserva(clienteNome, clienteTelefone, dataHora, duracaoHoras);
            reserva.setPrecoTotal(preco);

            Reserva salva = repository.salvar(reserva);
            notificacaoService.enviarConfirmacao(salva);
            return salva;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar reserva no banco de dados: " + e.getMessage(), e);
        }
    }

    private void definirStrategy(LocalDateTime dataHora) {
        int hora = dataHora.getHour();
        // Horário de pico: 18h às 6h (noite/madrugada)
        if (hora >= 18 || hora <= 6) {
            this.strategy = new org.example.infra.strategies.PrecoHorarioPicoStrategy();
        } else {
            this.strategy = new org.example.infra.strategies.PrecoDiaComumStrategy();
        }
    }

    public List<Reserva> listarReservas() {
        try {
            return repository.listarTodas();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar reservas do banco de dados: " + e.getMessage(), e);
        }
    }


    public void validarDataPassado(LocalDateTime dataHora)throws SQLException{
        if (dataHora.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Data inválida: não é possível agendar para uma data/hora no passado.\n" +
                    "Informe uma data a partir de "+ LocalDateTime.now());
        }
    }

    public void cancelarReserva(Long id) {
        try {
            repository.buscarPorId(id).ifPresentOrElse(
                    reserva -> {
                        try {
                            repository.remover(id);
                            notificacaoService.enviarCancelamento(reserva);
                        } catch (SQLException e) {
                            throw new RuntimeException("Erro ao remover reserva: " + e.getMessage(), e);
                        }
                    },
                    () -> {
                        throw new RuntimeException("Reserva não encontrada com ID: " + id);
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar reserva para cancelamento: " + e.getMessage(), e);
        }
    }

    public Reserva buscarReserva(Long id) {
        try {
            return repository.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Reserva não encontrada com ID: " + id));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar reserva no banco de dados: " + e.getMessage(), e);
        }
    }
}