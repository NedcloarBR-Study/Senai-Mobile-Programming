package com.example.pix;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TransacoesRepository extends SQLiteOpenHelper {
    public TransacoesRepository(Context context) {
        super(context, "Transacoes_Repository", null, 1);
    }

    public void criarTransacao(String tipo, String valor, String data) {
        String sql = "INSERT INTO transacoes (tipo, valor, data, conta_id) VALUES (?, ?, ?, 1)";
        super.getWritableDatabase().execSQL(sql, new Object[]{tipo, valor, data});
    }

    public List<TransacoesEntity> getTransacoes() {
        String sql = "SELECT tipo, valor, data FROM transacoes";
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        List<TransacoesEntity> lista = new ArrayList<TransacoesEntity>();
        for (int i = 0; i < cursor.getCount(); i++) {
            TransacoesEntity transacao = new TransacoesEntity(cursor.getString(0), cursor.getString(1), cursor.getString(2));
            lista.add(transacao);
            cursor.moveToNext();
        }
        cursor.close();
        return lista;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE transacoes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT," +
                "valor REAL," +
                "data TEXT," +
                "conta_id INTEGER," +
                "FOREIGN KEY(conta_id) REFERENCES conta(id)" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
