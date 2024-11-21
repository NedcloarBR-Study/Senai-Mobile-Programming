package com.example.e2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AlunoRepository extends SQLiteOpenHelper {

    public AlunoRepository(Context context) {
        super(context, "Aluno_Repository", null, 1);
    }


    public void criarAluno(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("nota", aluno.getNota());
        Log.i("INFO", aluno.getNome() + " - " + aluno.getNota());
        super.getWritableDatabase().insert("alunos", null, values);
    }

    public void removerAluno(Aluno aluno) {
        String[] args = {aluno.getId().toString()};
        super.getWritableDatabase().delete("alunos", "id=?", args);
    }

    public List<Aluno> listarAlunos() {
        String sql = "SELECT * FROM alunos";
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, null);
        List<Aluno> lista = new ArrayList<Aluno>();
        cursor.moveToFirst();
        for(int i = 0; i < cursor.getCount(); i++) {
            Integer id = cursor.getInt(0);
            String nome = cursor.getString(1);
            double nota = cursor.getDouble(2);
            Aluno aluno = new Aluno(id, nome, nota);
            lista.add(aluno);
            cursor.moveToNext();
        }
        cursor.close();
        return lista;
    }

    public List<Aluno> pesquisarAlunosPorNota(double notaMin, double notaMax) {
        String sql = "SELECT * FROM alunos WHERE nota BETWEEN ? AND ?";
        String[] selectionArgs = {String.valueOf(notaMin), String.valueOf(notaMax)};
        Cursor cursor = super.getReadableDatabase().rawQuery(sql, selectionArgs);
        List<Aluno> lista = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Integer id = cursor.getInt(0);
            String nome = cursor.getString(1);
            double nota = cursor.getDouble(2);
            Aluno aluno = new Aluno(id, nome, nota);
            lista.add(aluno);
            cursor.moveToNext();
        }
        cursor.close();
        return lista;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE alunos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "nota REAL" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS alunos";
        sqLiteDatabase.execSQL(sql);
        this.onCreate(sqLiteDatabase);
    }
}
