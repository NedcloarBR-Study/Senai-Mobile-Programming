package com.example.pix;

public class ChavePIX {
    private String chave;
    private String tipo;

    public ChavePIX(String chave, String tipo) {
        this.chave = chave;
        this.tipo = tipo;
    }

    public String getChave() {
        return chave;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return this.tipo + ": " + this.chave;
    }
}
