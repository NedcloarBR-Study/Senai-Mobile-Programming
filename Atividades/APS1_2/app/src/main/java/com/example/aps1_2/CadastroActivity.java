package com.example.aps1_2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private final CarroRepository carroRepository = new CarroRepository(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.cadastro_title);
        setContentView(R.layout.activity_cadastro);
    }

    public void salvarCarro(View view) {
        EditText marca = findViewById(R.id.marca);
        EditText modelo = findViewById(R.id.modelo);
        EditText ano = findViewById(R.id.ano);
        EditText cor = findViewById(R.id.cor);

        String modeloString = modelo.getText().toString();
        String marcaString = marca.getText().toString();
        String anoString = ano.getText().toString();
        String corString = cor.getText().toString();
        carroRepository.salvarCarro(modeloString, marcaString, Integer.parseInt(anoString), corString);
    }
}