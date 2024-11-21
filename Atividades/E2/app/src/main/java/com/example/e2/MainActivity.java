package com.example.e2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final AlunoRepository alunoRepository = new AlunoRepository(this);
    private EditText editTextNome, editTextNota;
    private ListView listViewAlunos;
    private final ArrayList<Aluno> listaAlunos = new ArrayList<Aluno>();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.initUI();
        this.carregarListaAlunos();
        this.configurarEventosListView();
    }

    private void initUI() {
        this.editTextNome = this.findViewById(R.id.editTextNome);
        this.editTextNota = this.findViewById(R.id.editTextNota);
        this.listViewAlunos = this.findViewById(R.id.listViewAlunos);
    }

    private void carregarListaAlunos() {
        this.listaAlunos.addAll(this.alunoRepository.listarAlunos());
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.listaAlunos);
        this.listViewAlunos.setAdapter(this.adapter);
    }

    private void configurarEventosListView() {
        this.listViewAlunos.setOnItemClickListener((adapterView, view, position, id) -> this.confirmarRemocao(position));
    }

    public void adicionarAluno(View view) {
        String nome = this.editTextNome.getText().toString().trim();
        String notaStr = this.editTextNota.getText().toString().trim();

        if (!this.validarEntradas(nome, notaStr)) return;

        double nota = Double.parseDouble(notaStr);
        Aluno novoAluno = new Aluno(nome, nota);

        this.alunoRepository.criarAluno(novoAluno);
        this.listaAlunos.add(novoAluno);
        this.adapter.notifyDataSetChanged();

        this.limparCampos();
        this.calcularMedia();
    }

    private boolean validarEntradas(String nome, String notaStr) {
        if (nome.isEmpty() || notaStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            double nota = Double.parseDouble(notaStr);
            if (nota < 0 || nota > 10) {
                Toast.makeText(this, "Nota deve estar entre 0 e 10", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Nota inválida", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void limparCampos() {
        this.editTextNome.setText("");
        this.editTextNota.setText("");
    }

    private void confirmarRemocao(final int position) {
        Aluno aluno = this.listaAlunos.get(position);
        new AlertDialog.Builder(this)
                .setTitle("Remover Aluno")
                .setMessage("Deseja remover o aluno " + aluno.getNome() + "?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> this.removerAluno(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void removerAluno(int position) {
        Aluno aluno = this.listaAlunos.get(position);
        this.alunoRepository.removerAluno(aluno);
        this.listaAlunos.remove(position);
        this.adapter.notifyDataSetChanged();
        this.calcularMedia();
    }

    private void calcularMedia() {
        if (this.listaAlunos.isEmpty()) {
            Toast.makeText(this, "Nenhum aluno na turma", Toast.LENGTH_SHORT).show();
            return;
        }

        double soma = 0;
        for (Aluno aluno : this.listaAlunos) {
            soma += aluno.getNota();
        }
        double media = soma / this.listaAlunos.size();
        Toast.makeText(this, "Média da Turma: " + String.format("%.2f", media), Toast.LENGTH_LONG).show();
    }

    public void abrirFiltrarPorNota(View view) {
        Intent intent = new Intent(this, FiltrarPorNotaActivity.class);
        startActivity(intent);
    }
}
