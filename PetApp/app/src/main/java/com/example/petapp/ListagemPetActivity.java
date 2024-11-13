package com.example.petapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ListagemPetActivity extends AppCompatActivity {
    private final RepositorioPet repositorioPet = new RepositorioPet(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pet);
        setTitle(R.string.title_listagem_pet);

        ListView listView = findViewById(R.id.listviewpet);
        List<Pet> listaDB = this.repositorioPet.listarPet();
        String[] dados = new String[listaDB.size()];
        for(int i = 0; i < listaDB.size(); i++){
            Pet pet = listaDB.get(i);
            dados[i] = pet.nome + " - "  +pet.idade;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dados);

        listView.setAdapter(adapter);
    }
}