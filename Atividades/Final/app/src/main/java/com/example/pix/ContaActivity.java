package com.example.pix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ContaActivity extends AppCompatActivity {
    private final ContaRepository contaRepository = new ContaRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conta);
        setTitle(R.string.conta_title);
        this.updateSaldo();
    }

    public void onDeposit(View view) {
        EditText valorOperacoView = findViewById(R.id.valor);
        this.contaRepository.depositar(valorOperacoView.getText().toString());
        this.updateSaldo();
    }

    public void onWithdraw(View view) {
        EditText valorOperacoView = findViewById(R.id.valor);
        this.contaRepository.retirar(valorOperacoView.getText().toString());
        this.updateSaldo();
    }

    public void onStatement(View view) {
        Intent ExtratoActivity = new Intent(this, ExtratoActivity.class);
        startActivity(ExtratoActivity);
    }

    public void onPix(View view) {
        Intent PixActivity = new Intent(this, PixActivity.class);
        startActivity(PixActivity);
    }

    public void updateSaldo() {
        TextView saldoView = findViewById(R.id.saldo);
        String saldoFormatado = String.format(getString(R.string.saldo), this.contaRepository.getSaldo().toString());
        saldoView.setText(saldoFormatado);
    }

}