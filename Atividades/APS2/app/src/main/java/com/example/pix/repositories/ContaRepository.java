package com.example.pix.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.pix.entities.ContaEntity;

public class ContaRepository extends SQLiteOpenHelper {

    public ContaRepository(@Nullable Context context) {
        super(context, "ContaRepository", null, 1);
    }

    public ContaEntity criar(String login, int password) {
        SQLiteDatabase db = super.getWritableDatabase();

        String checkQuery = "SELECT id FROM contas WHERE login = ?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{login});

        try {
            if (cursor.moveToFirst()) {
                return null;
            }
            String sql = "INSERT INTO contas (login, password, saldo) VALUES (?, ?, ?)";
            SQLiteStatement stmt = db.compileStatement(sql);

            stmt.bindString(1, login);
            stmt.bindLong(2, password);
            stmt.bindDouble(3, 0);

            long rowId = stmt.executeInsert();

            if (rowId != -1) {
                return new ContaEntity((int) rowId, login, password, 0);
            } else {
                return null;
            }

        } finally {
            cursor.close();
            db.close();
        }
    }

    public ContaEntity getConta(String login, int password) {
        String sql = "SELECT * FROM contas WHERE login = ? AND password = ?";

        try (Cursor cursor = super.getReadableDatabase().rawQuery(sql, new String[]{login, String.valueOf(password)})) {

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String loginResult = cursor.getString(cursor.getColumnIndexOrThrow("login"));
                int passwordResult = cursor.getInt(cursor.getColumnIndexOrThrow("password"));
                double saldo = cursor.getInt(cursor.getColumnIndexOrThrow("saldo"));

                return new ContaEntity(id, loginResult, passwordResult, saldo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void depositar(double valor) {
        Double saldo = this.getSaldo() + valor;
        ContentValues values = new ContentValues();
        values.put("saldo", saldo);

        super.getWritableDatabase().update("contas", values, "id = ?", new String[]{String.valueOf(Dados.conta.id)});
//        this.registrarTransacao("Depósito", valor);
    }

    public void sacar(double valor) {
        ContentValues values = new ContentValues();
        values.put("saldo", getSaldo() - valor);
        super.getWritableDatabase().update("contas", values, "id = ?", new String[]{String.valueOf(Dados.conta.id)});
//        registrarTransacao("Retirada", valor);
    }

    /*
        private void registrarTransacao(String tipo, double valor) {
            SQLiteDatabase db = super.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("tipo", tipo);
            values.put("valor", valor);
            values.put("saldo_atual", getSaldo());
            db.insert("transacoes", null, values);
            db.close();
        }
    */
    public double getSaldo() {
        SQLiteDatabase db = super.getReadableDatabase();
        double saldo = 0;

        Cursor cursor = db.rawQuery("SELECT saldo FROM contas WHERE id = ?", new String[]{String.valueOf(Dados.conta.id)});

        try {
            if (cursor.moveToFirst()) {
                saldo = cursor.getDouble(cursor.getColumnIndexOrThrow("saldo"));
            }
        } finally {
            cursor.close();
            db.close();
        }

        return saldo;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE contas (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "login TEXT NOT NULL," +
                "password INTEGER NOT NULL," +
                "saldo REAL NOT NULL DEFAULT 0)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
