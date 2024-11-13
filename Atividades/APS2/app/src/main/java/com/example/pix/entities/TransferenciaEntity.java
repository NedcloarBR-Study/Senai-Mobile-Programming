package com.example.pix.entities;

import com.example.pix.infra.TransferenciaEnum;

public class TransferenciaEntity {
    public int id;
    public TransferenciaEnum tipo;
    public double valor;
    public int remetente;
    public int destinatario;
}
