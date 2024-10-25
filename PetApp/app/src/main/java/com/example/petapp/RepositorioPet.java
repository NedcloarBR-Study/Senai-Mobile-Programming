package com.example.petapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class RepositorioPet extends SQLiteOpenHelper {

    public RepositorioPet(@Nullable Context context) {
        super(context, "pet", null, 1);
    }

    public void adicionarPet(Pet pet) {
        String sql = "INSERT INTO pet (nome, idade) VALUES ('" + pet.nome + "'," + pet.idade +")";
        super.getWritableDatabase().execSQL(sql);
        Log.i("Pet", "SQL Insert: " + sql);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE pet (id INTEGER NOT NULL PRIMARY KEY, nome TEXT, idade INTEGER)";
        sqLiteDatabase.execSQL(sql);
        Log.i("Pet", "Criado com sucesso a tabela pet");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
