package com.example.horastcu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class act_olv_contrasena extends AppCompatActivity {
    Button btnConfCod;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_olv_contrasena);

        btnConfCod=findViewById(R.id.btnConfCod);

        btnConfCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(act_login.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(act_olv_contrasena.this, act_contra_nueva.class);
                Toast.makeText(act_olv_contrasena.this,"Código válido", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }



}
