package com.example.pix.acitivities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
//import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pix.repositories.ContaRepository;
import com.example.pix.R;

public class ContaActivity extends AppCompatActivity {
    private final ContaRepository contaRepository = new ContaRepository(this);
    private TextView SaldoView;
    private EditText ValorView;
//    private ListView ExtratoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.conta_title);
        setContentView(R.layout.activity_conta);

        this.SaldoView = findViewById(R.id.saldo);
        this.ValorView = findViewById(R.id.valor);
//        this.ExtratoListView = findViewById(R.id.extrato_list);


        this.atualizarSaldo();
    }

    private void atualizarSaldo() {
        double saldo = this.contaRepository.getSaldo();
        this.SaldoView.setText(String.format(getString(R.string.balance), saldo));
    }

    public void depositar(View view) {
        String valorString = this.ValorView.getText().toString();
        if (this.checkInvalid(valorString)) {
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
            return;
        }
        double valor = Double.parseDouble(valorString);
        this.contaRepository.depositar(valor);
        this.atualizarSaldo();
        this.ValorView.setText("");
    }

    public void saque(View view) {
        String valorString = this.ValorView.getText().toString().trim();
        if (this.checkInvalid(valorString)) {
            Toast.makeText(this, R.string.invalid_amount, Toast.LENGTH_SHORT).show();
            return;
        }
        double valor = Double.parseDouble(valorString);
        if (this.contaRepository.getSaldo() < valor) {
            Toast.makeText(this, R.string.insufficient_amount, Toast.LENGTH_SHORT).show();
            return;
        }
        this.contaRepository.sacar(valor);
        atualizarSaldo();
        this.ValorView.setText("");
    }


//    public void extrato(View view) {
//        Cursor cursor = conta.getExtrato();
//        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "Nenhuma transação encontrada!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        while (cursor.moveToNext()) {
//            @SuppressLint("Range") String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
//            @SuppressLint("Range") double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
//            @SuppressLint("Range") double saldoAtual = cursor.getDouble(cursor.getColumnIndex("saldo_atual"));
//            String transacao = tipo + ": R$ " + valor + " (Saldo: R$ " + saldoAtual + ")";
//            adapter.add(transacao);
//        }
//        lvExtrato.setAdapter(adapter);
//        cursor.close();
//    }

    private boolean checkInvalid(String campo) {
        return campo == null || campo.trim().isEmpty();
    }
}
