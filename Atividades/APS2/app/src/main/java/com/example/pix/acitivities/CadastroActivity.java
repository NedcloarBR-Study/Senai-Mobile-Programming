package com.example.pix.acitivities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pix.entities.ContaEntity;
import com.example.pix.repositories.ContaRepository;
import com.example.pix.R;

public class CadastroActivity extends AppCompatActivity {
    private final ContaRepository contaRepository = new ContaRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.cadastro_title);
        setContentView(R.layout.activity_cadastro);
    }

    public void criar(View view) {
        EditText loginView = findViewById(R.id.login_cadastro);
        EditText passwordView = findViewById(R.id.password_cadastro);

        String loginContent = loginView.getText().toString().trim();
        int passwordContent = Integer.parseInt(passwordView.getText().toString().trim());

        ContaEntity conta = this.contaRepository.criar(loginContent, passwordContent);
        if(conta == null) {
            Toast.makeText(this, R.string.create_conta_error, Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, R.string.create_conta_success, Toast.LENGTH_SHORT).show();
        finish();
    }
}