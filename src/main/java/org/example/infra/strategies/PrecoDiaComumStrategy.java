package org.example.infra.strategies;

import org.example.domain.ICalculoPrecoStrategy;
import java.time.LocalDateTime;

public class PrecoDiaComumStrategy implements ICalculoPrecoStrategy {
    private static final double PRECO_POR_HORA = 100.0;

    @Override
    public double calcularPreco(LocalDateTime dataHora, int duracaoHoras) {
        return duracaoHoras * PRECO_POR_HORA;
    }
}