package com.example.finaledition.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConnectionFactory extends SQLiteOpenHelper {

    public ConnectionFactory(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static final String DBNAME = "users.db";

    public ConnectionFactory(Context context) {
        super(context, DBNAME, null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, senha TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // Inserir novo usuário
    public boolean inserirUsuario(String email, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("senha", senha);

        long result = db.insert("users", null, values);
        return result != -1;
    }

    // Verificar se usuário já existe
    public boolean usuarioExiste(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Verificar login
    public boolean verificarLogin(String email, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ? AND senha = ?", new String[]{email, senha});
        boolean valido = cursor.getCount() > 0;
        cursor.close();
        return valido;
    }

}

