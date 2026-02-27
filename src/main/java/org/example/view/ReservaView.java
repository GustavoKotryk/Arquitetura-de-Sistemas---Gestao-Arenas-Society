package org.example.view;

import org.example.controller.ReservaController;
import org.example.domain.Arena;
import org.example.domain.Reserva;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ReservaView {


    private final ReservaController reservaController;
    private final Scanner scanner;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ReservaView(ReservaController reservaController) {
        this.reservaController = reservaController;
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
            if (opcao == null) System.out.println("Opção inválida! Tente novamente.");
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
            case LISTAR_ARENAS   -> listarArenas();
            case AGENDAR         -> agendarReserva();
            case LISTAR_RESERVAS -> listarReservas();
            case SAIR            -> {}
        }
    }


    private void listarArenas() {
        List<Arena> arenas = reservaController.listarArenas();

        System.out.println("\n=== ARENAS DISPONÍVEIS ===");
        System.out.println("-".repeat(70));
        System.out.printf("%-5s %-20s %-40s%n", "ID", "Nome", "Descrição");
        System.out.println("-".repeat(70));
        arenas.forEach(a ->
                System.out.printf("%-5d %-20s %-40s%n", a.getId(), a.getNome(), a.getDescricao()));
        System.out.println("-".repeat(70));
    }

    private void exibirHorariosArena(Arena arena) {
        System.out.println("\nHorários disponíveis — " + arena.getNome() + ":");
        List<String> horarios = arena.getHorariosDisponiveis();
        StringBuilder linha = new StringBuilder("  ");
        for (int i = 0; i < horarios.size(); i++) {
            linha.append(String.format("%-10s", horarios.get(i)));
            if ((i + 1) % 6 == 0) { System.out.println(linha); linha = new StringBuilder("  "); }
        }
        if (linha.length() > 2) System.out.println(linha);
        System.out.println("  (★ = horário de pico R$180/h | demais R$100/h)");
    }



    private void agendarReserva() {
        try {
            listarArenas();
            System.out.print("\nDigite o ID da arena desejada: ");
            Long arenaId = scanner.nextLong();
            scanner.nextLine();

            Arena arena = reservaController.buscarArena(arenaId);
            exibirHorariosArena(arena);

            System.out.print("\nNome do cliente: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine().trim();

            System.out.print("Data e hora (dd/MM/yyyy HH:mm): ");
            String dataStr = scanner.nextLine().trim();
            LocalDateTime dataHora;
            try {
                dataHora = LocalDateTime.parse(dataStr, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido! Use dd/MM/yyyy HH:mm");
                return;
            }

            System.out.print("Duração em horas: ");
            int duracao = scanner.nextInt();
            scanner.nextLine();

            String resultado = reservaController.agendarReserva(arenaId, nome, telefone, dataHora, duracao);
            System.out.println("\n" + resultado);

        } catch (Exception e) {
            System.err.println("Erro ao agendar: " + e.getMessage());
        }
    }

    private void listarReservas() {
        try {
            List<Reserva> reservas = reservaController.listarTodasReservas();

            System.out.println("\nLista de reservas no banco de dados:");
            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada no banco de dados.");
                return;
            }

            System.out.println("-".repeat(120));
            System.out.printf("%-5s %-15s %-15s %-20s %-10s %-10s %-10s%n",
                    "ID", "Cliente", "Telefone", "Data/Hora", "Duração", "Preço", "Status");
            System.out.println("-".repeat(120));

            reservas.forEach(r ->
                    System.out.printf("%-5d %-15s %-15s %-20s %-8dh R$%-8.2f %-10s%n",
                            r.getId(),
                            truncar(r.getClienteNome(), 15),
                            r.getClienteTelefone(),
                            r.getDataHora().toString().replace("T", " "),
                            r.getDuracaoHoras(),
                            r.getPrecoTotal(),
                            r.getStatus())
            );
            System.out.println("-".repeat(120));
            System.out.printf("Total de reservas: %d%n", reservas.size());

        } catch (Exception e) {
            System.err.println("Erro ao listar reservas: " + e.getMessage());
        }
    }

    private String truncar(String texto, int tamanho) {
        if (texto == null) return "";
        return texto.length() <= tamanho ? texto : texto.substring(0, tamanho - 3) + "...";
    }
}