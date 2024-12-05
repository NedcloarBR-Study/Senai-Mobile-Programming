package com.example.pix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

public class ContaActivity extends AppCompatActivity {
    private final ContaRepository contaRepository = new ContaRepository(this);
    private final TransacoesRepository transacoesRepository = new TransacoesRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        setTitle(R.string.conta_title);
        this.updateSaldo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateSaldo();
    }

    public void onDeposit(View view) {
        String valorOperacaoString = this.getValorOperacao();
        if(!this.validarValor(valorOperacaoString)) return;
        this.contaRepository.depositar(valorOperacaoString);
        TransacoesEntity transacao = new TransacoesEntity("DEPOSITO", Double.parseDouble(valorOperacaoString), new Date().toString(), this.contaRepository.getSaldo());
        this.transacoesRepository.criarTransacao(transacao);
        this.updateSaldo();
    }

    public void onWithdraw(View view) {
        String valorOperacaoString = this.getValorOperacao();
        Double saldoAtual = this.contaRepository.getSaldo();
        if(!this.validarValor(valorOperacaoString)) return;
        if(saldoAtual < Double.parseDouble(valorOperacaoString)) {
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
            return;
        }
        this.contaRepository.retirar(valorOperacaoString);
        TransacoesEntity transacao = new TransacoesEntity("RETIRADA", Double.parseDouble(valorOperacaoString), new Date().toString(), this.contaRepository.getSaldo());
        this.transacoesRepository.criarTransacao(transacao);
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
        String saldoFormatado = String.format(getString(R.string.saldo), this.contaRepository.getSaldo());
        saldoView.setText(saldoFormatado);
    }

    public String getValorOperacao() {
        EditText valorOperacoView = findViewById(R.id.valor);
        String valorOperacaoString = valorOperacoView.getText().toString();
        valorOperacoView.setText("");
        return valorOperacaoString;
    }

    public boolean validarValor(String valor) {
        if(valor.isEmpty() || Double.parseDouble(valor) <= 0) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
