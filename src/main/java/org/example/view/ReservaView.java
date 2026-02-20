package org.example.view;

import org.example.domain.Reserva;
import org.example.service.ReservaService;

import java.util.List;
import java.util.Scanner;

public class ReservaView {
    private final ReservaService reservaService;
    private final Scanner scanner;

    public ReservaView(ReservaService reservaService) {
        this.reservaService = reservaService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("SISTEMA DE GESTÃO ARENA SOCIETY - BANCO NA NUVEM ☁️\n");

        MenuOpcao opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != MenuOpcao.SAIR);

        scanner.close();
        System.out.println("Sistema finalizado!");
    }

    private void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        for (MenuOpcao opcao : MenuOpcao.values()) {
            System.out.printf("%d. %s%n", opcao.getCodigo(), opcao.getDescricao());
        }
        System.out.print("Escolha uma opção: ");
    }

    private MenuOpcao lerOpcao() {
        try {
            int codigo = scanner.nextInt();
            scanner.nextLine();
            MenuOpcao opcao = MenuOpcao.fromCodigo(codigo);

            if (opcao == null) {
                System.out.println("Opção inválida! Tente novamente.");
            }
            return opcao;

        } catch (Exception e) {
            System.out.println("Entrada inválida! Digite um número.");
            scanner.nextLine();
            return null;
        }
    }

    private void processarOpcao(MenuOpcao opcao) {
        if (opcao == null) return;

        switch (opcao) {
            case LISTAR:
                listarReservas();
                break;
            case BUSCAR:
                buscarReservaPorId();
                break;
            case SAIR:
                break;
        }
    }

    private void listarReservas() {
        try {
            System.out.println("\nLista de reservas no banco de dados:");
            List<Reserva> reservas = reservaService.listarReservas();

            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada no banco de dados.");
                System.out.println("Use seu sistema de cadastro para adicionar reservas!");
            } else {
                System.out.println("-".repeat(120));
                System.out.printf("%-5s %-15s %-15s %-20s %-10s %-10s %-10s%n",
                        "ID", "Cliente", "Telefone", "Data/Hora", "Duração", "Preço", "Status");
                System.out.println("-".repeat(120));

                reservas.forEach(r -> {
                    System.out.printf("%-5d %-15s %-15s %-20s %-8dh R$%-8.2f %-10s%n",
                            r.getId(),
                            truncar(r.getClienteNome(), 15),
                            r.getClienteTelefone(),
                            r.getDataHora().toString().replace("T", " "),
                            r.getDuracaoHoras(),
                            r.getPrecoTotal(),
                            r.getStatus());
                });
                System.out.println("-".repeat(120));
                System.out.printf("Total de reservas: %d%n", reservas.size());
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar reservas: " + e.getMessage());
        }
    }

    private void buscarReservaPorId() {
        try {
            System.out.print("\nDigite o ID da reserva: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            Reserva reserva = reservaService.buscarReserva(id);
            System.out.println("\nReserva encontrada:");
            System.out.println("-".repeat(80));
            System.out.printf("ID: %d%n", reserva.getId());
            System.out.printf("Cliente: %s%n", reserva.getClienteNome());
            System.out.printf("Telefone: %s%n", reserva.getClienteTelefone());
            System.out.printf("Data/Hora: %s%n", reserva.getDataHora().toString().replace("T", " "));
            System.out.printf("Duração: %d hora(s)%n", reserva.getDuracaoHoras());
            System.out.printf("Preço Total: R$ %.2f%n", reserva.getPrecoTotal());
            System.out.printf("Status: %s%n", reserva.getStatus());
            System.out.println("-".repeat(80));

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private String truncar(String texto, int tamanho) {
        if (texto == null) return "";
        return texto.length() <= tamanho ? texto : texto.substring(0, tamanho - 3) + "...";
    }
}