package com.example.finaledition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finaledition.util.ConnectionFactory;


public class Cadastro extends AppCompatActivity {

    ConnectionFactory db;
    Button btnConfirmar;
    EditText edtEmailCadastro, edtSenhaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new ConnectionFactory(this);
        edtSenhaCadastro =findViewById(R.id.edtSenhaCadastro);
        edtEmailCadastro = findViewById(R.id.edtEmailCadastro);
        btnConfirmar = findViewById(R.id.btnConfirmar);
    }

    public void confirmar(View view ){
        String email = edtEmailCadastro.getText().toString();
        String senha = edtSenhaCadastro.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.usuarioExiste(email)) {
            Toast.makeText(this, "Usuário já existe!", Toast.LENGTH_SHORT).show();
        } else {
            boolean sucesso = db.inserirUsuario(email, senha);
            if (sucesso) {
                Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Cadastro.this, MainActivity.class );
                startActivity(intent);
                finish(); // Volta para tela de login
            } else {
                Toast.makeText(this, "Erro ao cadastrar.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}