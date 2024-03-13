package com.example.horastcu;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class act_contra_nueva extends AppCompatActivity {
    Button btnCambio;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_contra_nueva);

        btnCambio=findViewById(R.id.btnCambio);

        btnCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(act_login.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(act_contra_nueva.this, act_login.class);
                Toast.makeText(act_contra_nueva.this,"Cambio de contraseña realizado", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }
}
