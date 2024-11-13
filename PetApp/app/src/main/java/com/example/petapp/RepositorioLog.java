package com.example.petapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class RepositorioLog extends SQLiteOpenHelper {
    public RepositorioLog(@Nullable Context context) {
        super(context, "log", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql ="CREATE TABLE log " +
                "(id INTEGER NOT NULL PRIMARY KEY," +
                "data TEXT," +
                "operacao TEXT," +
                "usuario TEXT)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void logar(Log log){
        String sql = "INSERT INTO log (data, operacao, usuario) VALUES (?, ?, ?, ?)";

        super.getWritableDatabase().execSQL(sql, new String[]{log.data, log.operacao, log.usuario});
        android.util.Log.i("pet","SQL insert log: " + sql);
    }

}