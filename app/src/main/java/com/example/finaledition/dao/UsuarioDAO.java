package com.example.finaledition.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.finaledition.util.ConnectionFactory;
import com.example.finaledition.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private ConnectionFactory conexao;
    private SQLiteDatabase banco;

    public UsuarioDAO(Context context) {
        conexao = new ConnectionFactory(context, "banco.db", null, 1);

        banco = conexao.getWritableDatabase();
    }

    //método inserir
    public Long insert(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("dataEvento", usuario.getDataEvento());
        values.put("horarioEvento", usuario.getHorarioEvento());
        values.put("nomeEvento", usuario.getNomeEvento());
        return (banco.insert("usuario", null, values));
    }


    public List<Usuario> obterTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = banco.query("usuario", new String[]{"id", "nome", "dataEvento", "horarioEvento", "nomeEvento"},
                //era um args
                "nome=?", null, null, null, null);

        while (cursor.moveToNext()) {
            Usuario u = new Usuario();
            u.setId(cursor.getLong(0));
            u.setNome((cursor.getString(1)));
            u.setDataEvento(cursor.getString(2));
            u.setHorarioEvento(cursor.getString(3));
            u.setNomeEvento(cursor.getString(4));
            usuarios.add(u);
        }
        return usuarios;
    }

    /* public Visitante read(String no)*/
}


