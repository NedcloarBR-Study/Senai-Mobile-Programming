package com.example.e2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FiltrarPorNotaActivity extends AppCompatActivity {

    private EditText editTextNotaMin, editTextNotaMax;
    private Button btnPesquisar;
    private ListView listViewResultados;
    private AlunoRepository alunoRepository;
    private ArrayAdapter<Aluno> adapter;
    private List<Aluno> resultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_por_nota);

        alunoRepository = new AlunoRepository(this);

        initUI();
        configurarBotaoPesquisar();
    }

    private void initUI() {
        editTextNotaMin = findViewById(R.id.editTextNotaMin);
        editTextNotaMax = findViewById(R.id.editTextNotaMax);
        btnPesquisar = findViewById(R.id.btnPesquisar);
        listViewResultados = findViewById(R.id.listViewResultados);
    }

    private void configurarBotaoPesquisar() {
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisarAlunos();
            }
        });
    }

    private void pesquisarAlunos() {
        String notaMinStr = editTextNotaMin.getText().toString().trim();
        String notaMaxStr = editTextNotaMax.getText().toString().trim();

        if (notaMinStr.isEmpty() || notaMaxStr.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha ambos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double notaMin = Double.parseDouble(notaMinStr);
            double notaMax = Double.parseDouble(notaMaxStr);

            if (notaMin > notaMax) {
                Toast.makeText(this, "A nota mínima não pode ser maior que a máxima", Toast.LENGTH_SHORT).show();
                return;
            }

            resultados = alunoRepository.pesquisarAlunosPorNota(notaMin, notaMax);

            if (resultados.isEmpty()) {
                Toast.makeText(this, "Nenhum aluno encontrado no intervalo informado", Toast.LENGTH_SHORT).show();
            } else {
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultados);
                listViewResultados.setAdapter(adapter);
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores válidos para as notas", Toast.LENGTH_SHORT).show();
        }
    }
}
