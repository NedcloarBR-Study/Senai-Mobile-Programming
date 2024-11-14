package com.example.pix;

public class TransacoesEntity {
    private String tipo;
    private String valor;
    private String data;

    public TransacoesEntity(String tipo, String valor, String data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    @Override
    public String toString() {
        return this.tipo + " - " + this.valor + " - " + this.data;
    }
}
