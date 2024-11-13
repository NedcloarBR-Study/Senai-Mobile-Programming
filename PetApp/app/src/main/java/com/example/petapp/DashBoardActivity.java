package com.example.petapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashBoardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        String login = (String) getIntent().getSerializableExtra("login");
        Toast.makeText(this, "Bem-vindo: " + login, Toast.LENGTH_SHORT).show();
        setTitle("DashBoard");
    }

    public void cadastroPet(View view) {
        Intent intent = new Intent(this, CadastroPetActivity.class);
        startActivity(intent);
    }

    public void listagemPet(View view) {
        Intent intent = new Intent(this, ListagemPetActivity.class);
        startActivity(intent);
    }

    public void atualizarPet(View view) {
        Intent intent = new Intent(this, AtualizarPetActivity.class);
        startActivity(intent);
    }

    public void removerPet(View view) {
        Intent intent = new Intent(this, RemoverPetActivity.class);
        startActivity(intent);
    }
}