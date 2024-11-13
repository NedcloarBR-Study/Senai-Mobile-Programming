package com.example.pix.acitivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pix.entities.ContaEntity;
import com.example.pix.repositories.ContaRepository;
import com.example.pix.repositories.Dados;
import com.example.pix.R;

public class LoginActivity extends AppCompatActivity {
    private final ContaRepository contaRepository = new ContaRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.login_title);
        setContentView(R.layout.activity_login);
    }

    public void logar(View view) {
        EditText loginView = findViewById(R.id.login);
        EditText passwordView = findViewById(R.id.password);

        String loginContent = loginView.getText().toString().trim();
        String passwordContent = passwordView.getText().toString().trim();

        if(loginContent.isEmpty() || passwordContent.isEmpty()) {
            Toast.makeText(this, R.string.login_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        ContaEntity conta = this.contaRepository.getConta(loginContent, Integer.parseInt(passwordContent));

        if(conta == null) {
            Toast.makeText(this, R.string.login_not_found, Toast.LENGTH_LONG).show();
            return;
        }

        Dados.conta = conta;
        Intent contaActivity = new Intent(this, ContaActivity.class);
        startActivity(contaActivity);
    }

    public void criar(View view) {
        Intent cadastroActivity = new Intent(this, CadastroActivity.class);
        startActivity(cadastroActivity);
    }
}
