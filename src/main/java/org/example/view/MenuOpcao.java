package org.example.view;

public enum MenuOpcao {
    LISTAR_ARENAS(1,   "Listar arenas disponíveis"),
    AGENDAR(2,         "Agendar reserva"),
    LISTAR_RESERVAS(3, "Listar todas as reservas"),
    SAIR(4,            "Sair");

    private final int codigo;
    private final String descricao;

    MenuOpcao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static MenuOpcao fromCodigo(int codigo) {
        for (MenuOpcao opcao : values()) {
            if (opcao.codigo == codigo) return opcao;
        }
        return null;
    }
}