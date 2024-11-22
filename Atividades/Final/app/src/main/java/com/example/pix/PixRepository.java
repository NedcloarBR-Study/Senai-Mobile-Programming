package com.example.pix;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class PixRepository extends SQLiteOpenHelper {
    public PixRepository(Context context) {
        super(context, "Pix_Repository", null, 1);
    }

    public void cadastrarChave(ChavePIX chave) {
        String sql = "INSERT INTO pix (chave, tipo, conta_id) VALUES ('" + chave.getChave() + "', '" + chave.getTipo() + "', 1)";
        super.getWritableDatabase().execSQL(sql);
    }

    public void removerChave(ChavePIX chave) {
        String sql = "DELETE FROM pix WHERE chave = '" + chave.getChave() + "'";
        super.getWritableDatabase().execSQL(sql);
    }

    public List<ChavePIX> listarChaves() {
        String sql = "SELECT chave, tipo FROM pix";
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        List<ChavePIX> lista = new ArrayList<ChavePIX>();
        for (int i = 0; i < cursor.getCount(); i++) {
            ChavePIX chave = new ChavePIX(cursor.getString(0), cursor.getString(1));
            lista.add(chave);
            cursor.moveToNext();
        }
        cursor.close();
        return lista;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE pix (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "chave TEXT," +
                "tipo TEXT," +
                "conta_id INTEGER," +
                "FOREIGN KEY(conta_id) REFERENCES conta(id)" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS pix";
        sqLiteDatabase.execSQL(sql);
        this.onCreate(sqLiteDatabase);
    }
}
