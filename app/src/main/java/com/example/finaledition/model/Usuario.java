package com.example.finaledition.model;

public class Usuario {

    private Long id;
    private String nome;
    private String dataEvento;
    private String horarioEvento;
    private String nomeEvento;

    public Usuario() {
    }

    public Usuario(String nome, String dataEvento, String horarioEvento, String nomeEvento) {
        this.nome = nome;
        this.dataEvento = dataEvento;
        this.horarioEvento = horarioEvento;
        this.nomeEvento = nomeEvento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getHorarioEvento() {
        return horarioEvento;
    }

    public void setHorarioEvento(String horarioEvento) {
        this.horarioEvento = horarioEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
