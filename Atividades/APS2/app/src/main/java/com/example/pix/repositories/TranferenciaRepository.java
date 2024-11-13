package com.example.pix.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.pix.entities.ContaEntity;
import com.example.pix.entities.TransferenciaEntity;
import com.example.pix.infra.TransferenciaEnum;

public class TranferenciaRepository extends SQLiteOpenHelper {

    public TranferenciaRepository(@Nullable Context context) {
        super(context, "TranferenciaRepository", null, 1);
    }

    public TransferenciaEntity criar(TransferenciaEnum tipo, double valor, int remetente, int destinatario) {
        SQLiteDatabase db = super.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tipo", String.valueOf(tipo));
        values.put("valor", String.valueOf(valor));
        values.put("remetente", String.valueOf(remetente));
        values.put("destinatario", String.valueOf(destinatario));
        db.insert("transferencias", null, values);
//
//        try {
//            if (cursor.moveToFirst()) {
//                return null;
//            }
//            String sql = "INSERT INTO contas (login, password, saldo) VALUES (?, ?, ?)";
//            SQLiteStatement stmt = db.compileStatement(sql);
//
//            stmt.bindString(1, login);
//            stmt.bindLong(2, password);
//            stmt.bindDouble(3, 0);
//
//            long rowId = stmt.executeInsert();
//
//            if (rowId != -1) {
//                return new ContaEntity((int) rowId, login, password, 0);
//            } else {
//                return null;
//            }
//
//        } finally {
//            cursor.close();
//            db.close();
//        }
    }

    public ContaEntity getTranferencias(int id) {
        /*String sql = "SELECT * FROM contas WHERE login = ? AND password = ?";

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

        return null;*/
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE transferencias (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT NOT NULL," +
                "valor REAL NOT NULL DEFAULT 0," +
                "remetente INTEGER NOT NULL," +
                "destinatario INTEGER NOT NULL," +
                "FOREIGN KEY (remetente) REFERENCES contas(id)," +
                "FOREIGN KEY (destinatario) REFERENCES contas(id)" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
