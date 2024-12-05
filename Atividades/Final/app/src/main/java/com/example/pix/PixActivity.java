package com.example.pix;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Date;

public class PixActivity extends AppCompatActivity {

    private PixRepository pixRepository;
    private TransacoesRepository transacoesRepository;
    private ContaRepository contaRepository;

    private ListView listaChavesPIXView;
    private ArrayList<ChavePIX> listaChavesPIX;
    private ArrayAdapter<ChavePIX> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);
        setTitle(R.string.pix_title);

        this.pixRepository = new PixRepository(this);
        this.transacoesRepository = new TransacoesRepository(this);
        this.contaRepository = new ContaRepository(this);

        this.listaChavesPIXView = findViewById(R.id.lista_chaves);
        this.listaChavesPIX = (ArrayList<ChavePIX>) this.pixRepository.listarChaves();

        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listaChavesPIX);
        this.listaChavesPIXView.setAdapter(this.adapter);

        this.listaChavesPIXView.setOnItemClickListener((parent, view, position, id) -> exibirDialogoRemocao(position));
    }

    private void exibirDialogoRemocao(int position) {
        ChavePIX chave = this.listaChavesPIX.get(position);
        new AlertDialog.Builder(this)
                .setTitle("Remover Chave")
                .setMessage("Deseja remover a chave " + chave.getChave() + " do tipo " + chave.getTipo() + "?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> removerChave(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void cadastrarChave(View view) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_cadastrar_chave, null);

        EditText inputChave = dialogView.findViewById(R.id.input_chave);
        Spinner tipoChaveSpinner = dialogView.findViewById(R.id.spinner_tipo_chave);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipos_chave_pix,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoChaveSpinner.setAdapter(spinnerAdapter);

        new AlertDialog.Builder(this)
                .setTitle("Cadastrar Chave PIX")
                .setView(dialogView)
                .setPositiveButton("Salvar", (dialog, which) -> {
                    String chave = inputChave.getText().toString().trim();
                    String tipo = tipoChaveSpinner.getSelectedItem().toString();

                    if (validarChave(chave, tipo)) {
                        ChavePIX novaChave = new ChavePIX(chave, tipo);
                        Log.i("Chave PIX", chave.toString());
                        this.pixRepository.cadastrarChave(novaChave);
                        this.listaChavesPIX.add(novaChave);
                        this.adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Chave cadastrada com sucesso!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Chave inválida!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private boolean validarChave(String chave, String tipo) {
        if (chave.isEmpty()) return false;
        switch (tipo) {
            case "Telefone":
                return Patterns.PHONE.matcher(chave).matches();
            case "CPF":
                return isCPFValido(chave);
            default:
                return false;
        }
    }

    private boolean isCPFValido(String cpf) {
        return cpf.matches("\\d{11}");
    }

    public void removerChave(int position) {
        ChavePIX chave = this.listaChavesPIX.get(position);
        this.pixRepository.removerChave(chave);
        this.listaChavesPIX.remove(position);
        this.adapter.notifyDataSetChanged();
    }

    public void enviarPix(View view) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_enviar_pix, null);
        TextView saldoView = dialogView.findViewById(R.id.text_saldo);
        saldoView.setText(String.format("Saldo: R$ %.2f", this.contaRepository.getSaldo()));
        EditText inputChaveDestino = dialogView.findViewById(R.id.input_chave_destino);
        EditText inputValor = dialogView.findViewById(R.id.input_valor);
        if(this.pixRepository.listarChaves().isEmpty()) {
            Toast.makeText(this, "Você deve ter pelo menos 1 chave cadastrada para enviar um PIX", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Enviar PIX")
                .setView(dialogView)
                .setPositiveButton("Enviar", (dialog, which) -> {
                    String chaveDestino = inputChaveDestino.getText().toString().trim();
                    String valorStr = inputValor.getText().toString().trim();

                    if (validarEnvioPix(chaveDestino, valorStr)) {
                        double valor = Double.parseDouble(valorStr);
                        realizarEnvioPix(chaveDestino, valor);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private boolean validarEnvioPix(String chaveDestino, String valorStr) {
        if (TextUtils.isEmpty(chaveDestino)) {
            Toast.makeText(this, "A chave PIX não pode estar vazia.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(valorStr)) {
            Toast.makeText(this, "O valor não pode estar vazio.", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            double valor = Double.parseDouble(valorStr);
            if (valor <= 0) {
                Toast.makeText(this, "O valor deve ser maior que zero.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(valor > this.contaRepository.getSaldo()) {
                Toast.makeText(this, "Saldo insuficiente.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void realizarEnvioPix(String chaveDestino, double valor) {
        boolean sucesso = this.contaRepository.retirar(String.valueOf(valor));
        TransacoesEntity transacao = new TransacoesEntity("PIX", valor, new Date().toString(), this.contaRepository.getSaldo());
        this.transacoesRepository.criarTransacao(transacao);
        if (sucesso) {
            Toast.makeText(this, "PIX enviado com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao enviar PIX. Verifique a chave ou o saldo.", Toast.LENGTH_SHORT).show();
        }
    }
}
