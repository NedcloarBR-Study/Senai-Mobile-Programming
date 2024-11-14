package com.example.pix;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ExtratoActivity extends AppCompatActivity {

    private final TransacoesRepository transacoesRepository = new TransacoesRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extrato);
        setTitle(R.string.extrato_title);
        ListView listView = findViewById(R.id.extrato_list);
        List<TransacoesEntity> listaDB = this.transacoesRepository.getTransacoes();
        if(listaDB.isEmpty()) {
            Toast.makeText(this, "Nenhuma Transação encontrada", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] dados = new String[listaDB.size()];
        for(int i = 0; i < listaDB.size(); i++){
            TransacoesEntity transacao = listaDB.get(i);
            dados[i] = transacao.toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dados);
        listView.setAdapter(adapter);
    }
}