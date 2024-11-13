package com.example.pix.entities;

public class ContaEntity {
    public int id;
    public String login;
    public int password;
    public double saldo;


    public ContaEntity( int id, String login, int password, double saldo) {
        this.login = login;
        this.id = id;
        this.password = password;
        this.saldo = saldo;
    }
}
