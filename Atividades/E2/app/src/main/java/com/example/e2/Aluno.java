package com.example.e2;

public class Aluno {
    private Integer id;
    private String nome;
    private double nota;

    public Aluno(Integer id,String nome, double nota) {
        this.id = id;
        this.nome = nome;
        this.nota = nota;
    }

    public Aluno(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return nome + " - " + nota;
    }
}