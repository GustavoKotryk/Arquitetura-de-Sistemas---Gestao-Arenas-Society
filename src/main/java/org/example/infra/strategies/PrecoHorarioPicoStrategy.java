package org.example.infra.strategies;

import org.example.domain.ICalculoPrecoStrategy;
import java.time.LocalDateTime;

public class PrecoHorarioPicoStrategy implements ICalculoPrecoStrategy {
    private static final double PRECO_POR_HORA = 180.0;

    @Override
    public double calcularPreco(LocalDateTime dataHora, int duracaoHoras) {
        return duracaoHoras * PRECO_POR_HORA;
    }
}