package com.example.finaledition;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finaledition.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Home extends AppCompatActivity {

    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnScan = findViewById(R.id.btnScan);

    }

    public void perfil(View view){
        Intent intent = new Intent(Home.this, Perfil.class);
        startActivity(intent);
        finish();
    }
    public void Criar(View view){
        Intent intent = new Intent(Home.this, GerarEvento.class);
        startActivity(intent);
    }
    public void Scannear(View view){
        ScannerCod();
    }

    public void ScannerCod(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Aumente o volume para utilizar o flash");
        options.setBeepEnabled(false);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {

        if(result.getContents() != null)
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setTitle("Resul");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                }
            }).show();

        }

    });

}