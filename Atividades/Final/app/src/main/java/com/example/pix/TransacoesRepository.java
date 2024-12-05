package com.example.pix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TransacoesRepository extends SQLiteOpenHelper {
    public TransacoesRepository(Context context) {
        super(context, "Transacoes_Repository", null, 1);
    }

    public void criarTransacao(TransacoesEntity transacao) {
        ContentValues values = new ContentValues();
        values.put("tipo", transacao.getTipo());
        values.put("valor", transacao.getValor());
        values.put("data", transacao.getData().toString());
        values.put("saldo", transacao.getSaldo());
        values.put("conta_id", 1);
        super.getWritableDatabase().insert("transacoes", null, values);
    }

    public List<TransacoesEntity> getTransacoes() {
        String sql = "SELECT tipo, valor, data, saldo FROM transacoes";
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        Log.i("cursor", "cursor");
        List<TransacoesEntity> lista = new ArrayList<TransacoesEntity>();
        for (int i = 0; i < cursor.getCount(); i++) {
            TransacoesEntity transacao = new TransacoesEntity(cursor.getString(0), cursor.getDouble(1), cursor.getString(2), cursor.getDouble(3));
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
                "saldo REAL," +
                "conta_id INTEGER," +
                "FOREIGN KEY(conta_id) REFERENCES conta(id)" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS transacoes";
        sqLiteDatabase.execSQL(sql);
        this.onCreate(sqLiteDatabase);
    }
}
