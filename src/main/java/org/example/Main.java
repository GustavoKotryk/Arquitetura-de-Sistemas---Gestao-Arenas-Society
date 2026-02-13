package org.example;

import java.util.Scanner;

class SistemaReservaArena {

    public void fazerReserva(String nomeCliente, String nomeArena, String tipoPagamento) {
        System.out.println("\n=== PROCESSANDO RESERVA ===");
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Arena: " + nomeArena);

        if (tipoPagamento.equalsIgnoreCase("PIX")) {
            System.out.println("💰 Pago via PIX - R$ 100");
        } else if (tipoPagamento.equalsIgnoreCase("CARTAO")) {
            System.out.println("💳 Pago via Cartão - R$ 100");
        } else if (tipoPagamento.equalsIgnoreCase("DINHEIRO")) {
            System.out.println("💵 Pago em Dinheiro - R$ 100");
        } else {
            System.out.println("❌ Forma de pagamento inválida!");
        }

        System.out.println("📧 Email enviado para " + nomeCliente);
        System.out.println("💾 Salvo no banco de dados (Simulado)");
    }
}

class ArenaVIP extends SistemaReservaArena {
    @Override
    public void fazerReserva(String nome, String arena, String pagamento) {
        System.out.println("\n--- [ERRO DE NEGÓCIO] ---");
        System.out.println("❌ O cliente " + nome + " tentou reservar, mas VIP não faz reserva normal!");
    }
}
