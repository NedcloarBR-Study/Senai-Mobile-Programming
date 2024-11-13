package com.example.petapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPet extends SQLiteOpenHelper {

    public RepositorioPet(@Nullable Context context) {
        super(context, "pet", null, 1);
    }

    public void adicionarPet(Pet pet) {
        String sql = "INSERT INTO pet (nome, idade) VALUES (?, ?)";
        super.getWritableDatabase().execSQL(sql, new String[]{pet.nome, String.valueOf(pet.idade)});
        Log.i("Pet", "SQL Insert: " + sql);
    }

    public List<Pet> listarPet() {
        ArrayList<Pet> list = new ArrayList<Pet>();
        String sql = "SELECT * FROM pet";
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            Pet pet = new Pet();
            pet.id = cursor.getInt(0);
            pet.nome = cursor.getString(1);
            pet.idade = cursor.getInt(2);
            list.add(pet);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public Pet buscarPet(Integer id) {
        String sql = "SELECT * FROM pet WHERE id = ?";
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        Pet pet = null;
        for(int i = 0; i < cursor.getCount(); i++) {
            pet = new Pet();
            pet.id = cursor.getInt(0);
            pet.nome = cursor.getString(1);
            pet.idade = cursor.getInt(2);
            cursor.moveToNext();
        }
        cursor.close();
        return pet;
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
