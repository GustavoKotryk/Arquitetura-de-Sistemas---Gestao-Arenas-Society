package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private Long id;
    private String nome;
    private String descricao;

    public Arena(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }

    // Horários fixos disponíveis: 07h às 23h
    public List<String> getHorariosDisponiveis() {
        List<String> horarios = new ArrayList<>();
        for (int h = 7; h <= 23; h++) {
            String tag = (h >= 18) ? String.format("%02d:00★", h) : String.format("%02d:00", h);
            horarios.add(tag);
        }
        return horarios;
    }
}