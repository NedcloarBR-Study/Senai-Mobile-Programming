package com.example.aps1_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarroRepository extends SQLiteOpenHelper {

    public CarroRepository(Context context) {
        super(context, "carro_repository", null, 1);
    }

    public void salvarCarro(String modelo, String marca, int ano, String cor) {
        String sql = "INSERT INTO carro (modelo, marca, ano, cor) VALUES (?, ?, ?, ?)";
        super.getWritableDatabase().execSQL(sql, new String[]{modelo, marca, String.valueOf(ano), cor});
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE carro (id INTEGER PRIMARY KEY, modelo TEXT, marca TEXT, ano TEXT, cor TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
