package com.example.pix;

public class TransacoesEntity {
    private String tipo;
    private Double valor;
    private String data;
    private Double saldo;

    public TransacoesEntity(String tipo, Double valor, String data, Double saldo) {
        this.setTipo(tipo);
        this.setValor(valor);
        this.setData(data);
        this.setSaldo(saldo);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Tipo: " + this.tipo + " - Valor: " + this.valor + " - Data: " + this.data + " - Saldo pós procedimento:" + this.saldo ;
    }
}
