package org.example.domain;

import java.time.LocalDateTime;

public interface ICalculoPrecoStrategy {
    double calcularPreco(LocalDateTime dataHora, int duracaoHoras);
}