package com.example.finaledition;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Calendar;

public class GerarEvento extends AppCompatActivity {

    private Button btnCriar;
    private EditText edtNome, edtData, edtHora;

    private ImageView imgQrcode;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gerar_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCriar = findViewById(R.id.btnCriar);
        edtNome = findViewById(R.id.edtNome);
        edtData = findViewById(R.id.edtData);
        edtHora = findViewById(R.id.edtHora);

        imgQrcode = findViewById(R.id.imgQrCode);

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateQRCode();
            }
        });

        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendario = Calendar.getInstance();
                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(
                        GerarEvento.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String dataFormatada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                                edtData.setText(dataFormatada);
                            }
                        },
                        ano, mes, dia
                );
                datePicker.show();
            }
        });

        edtHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(
                        GerarEvento.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String horaFormatada = String.format("%02d:%02d", hourOfDay, minute);
                                edtHora.setText(horaFormatada);
                            }
                        },
                        hora, minuto, true // o último "true" define o formato 24h
                );
                timePicker.show();
            }
        });
    }

    private void generateQRCode() {
        String eventName = edtNome.getText().toString().trim();
        String eventDate = edtData.getText().toString().trim();
        String eventTime = edtHora.getText().toString();

        if (eventName.isEmpty() || eventDate.isEmpty() || eventTime.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String qrContent = null;
        try {
            qrContent = "EVENTO:" + eventName + "\n" +
                    "DATA:" + eventDate + "\n" +
                    "HORA:" + eventTime;

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(qrContent, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            imgQrcode.setImageBitmap(bitmap);
            imgQrcode.setVisibility(View.VISIBLE);
        } catch (Exception e) { // Captura qualquer exceção, não só WriterException
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar QR Code: " + e.getMessage(), Toast.LENGTH_LONG).show();

            // Log detalhado para debug
            Log.e("QRCodeError", "Conteúdo do QR: " + qrContent);
            Log.e("QRCodeError", "Stack trace: ", e);
        }
    }
    private boolean isValidDate(String date) {
        String pattern = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d\\d$";
        return date.matches(pattern);
    }

    public void salvar() {

    }
}