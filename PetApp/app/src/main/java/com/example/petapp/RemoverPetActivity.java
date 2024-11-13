package com.example.petapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RemoverPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover_pet);
        setTitle("Remover Pet");
    }

    public void remover(View view) {
        EditText editText = findViewById(R.id.editTextIdPet);
        String idString = editText.getText().toString();

        try {
            Integer id = Integer.parseInt(idString);
            boolean resultado = DadosCompartilhados.lista.removeIf(obj -> obj.id.equals(id));
            if(!resultado) {
                Toast.makeText(this,"nâo encontrado id", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(this,"Remoção realizado com sucesso.", Toast.LENGTH_LONG).show();

        } catch(Exception e) {
            Toast.makeText(this,"digite somente numeros", Toast.LENGTH_LONG).show();
        }
    }
}