package com.example.pix;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContaRepository extends SQLiteOpenHelper {

    public ContaRepository(Context context) {
        super(context, "Conta_Repository", null, 1);
    }

    public void depositar(String valor) {
        String sql = "UPDATE conta SET saldo = saldo + ? WHERE id = 1";
        super.getWritableDatabase().execSQL(sql, new Object[]{valor});
    }

    public void retirar(String valor) {
        String sql = "UPDATE conta SET saldo = saldo - ? WHERE id = 1";
        super.getWritableDatabase().execSQL(sql, new Object[]{valor});
    }

    public Double getSaldo() {
        String sql = "SELECT saldo FROM conta WHERE id = 1";
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            Double saldo = cursor.getDouble(0);
            cursor.close();
            Log.i("saldo", String.valueOf(saldo));
            return saldo;
        } else {
            cursor.close();
            return 0.0;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateTable = "CREATE TABLE conta (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "saldo REAL NOT NULL DEFAULT 0" +
                ")";
        sqLiteDatabase.execSQL(sqlCreateTable);
        String sqlCreateConta = "INSERT INTO conta (saldo) VALUES (0)";
        sqLiteDatabase.execSQL(sqlCreateConta);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
