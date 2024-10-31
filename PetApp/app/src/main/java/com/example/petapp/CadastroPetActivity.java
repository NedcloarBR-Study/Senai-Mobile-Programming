package com.example.petapp;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;


public class CadastroPetActivity extends AppCompatActivity {
    RepositorioPet repositorioPet;
    RepositorioLog repositorioLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);
        setTitle("Cadastro Pet");
        Log.i("pet", "Carregado Cadastro Pet com sucesso");
        this.repositorioPet = new RepositorioPet(this);
        this.repositorioLog = new RepositorioLog(this);
    }

    public void cadastrar(View view){
        EditText editTextNome = findViewById(R.id.editTextNomePet);
        EditText editTextIdade = findViewById(R.id.editTextIdadePet);
        String nome = editTextNome.getText().toString();
        String idade = editTextIdade.getText().toString();
        if(nome.isEmpty() || idade.isEmpty()){
            Toast.makeText(this,"Preencha os campos", Toast.LENGTH_LONG).show();
            return;
        }
        Pet pet = new Pet();
        pet.nome = nome;
        pet.idade = Integer.parseInt(idade);
        this.repositorioPet.adicionarPet(pet);
        com.example.petapp.Log log =new com.example.petapp.Log();
        log.data = new Date().toString();
        log.operacao = "cadastro pet";
        log.usuario = DadosCompartilhados.usuarioLogado;
        repositorioLog.logar(log);
        Toast.makeText(this,"Cadastro realizado com sucesso.", Toast.LENGTH_LONG).show();
        editTextNome.setText("");
        editTextIdade.setText("");
    }


}