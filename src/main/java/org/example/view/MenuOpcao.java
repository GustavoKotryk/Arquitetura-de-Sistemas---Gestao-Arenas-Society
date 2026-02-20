package org.example.view;

public enum MenuOpcao {
    LISTAR(1, "Listar todas as reservas"),
    BUSCAR(2, "Buscar reserva por ID"),
    SAIR(3, "Sair");

    private final int codigo;
    private final String descricao;

    MenuOpcao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static MenuOpcao fromCodigo(int codigo) {
        for (MenuOpcao opcao : values()) {
            if (opcao.codigo == codigo) {
                return opcao;
            }
        }
        return null;
    }
}