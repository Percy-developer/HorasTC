package com.example.horastcu;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horastcu.modelo.Actividad;
import com.example.horastcu.modelo.User;

public class act_detalle_actividad extends AppCompatActivity {

    TextView txtCerrarSesion, lblNombre, lblDescripcion, lblPoblacion, lblLugar, lblFecha, lblHora;

    Actividad selectedActividad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_detalle_actividad);

        txtCerrarSesion = findViewById(R.id.txtCerrarSesion);
        lblNombre = findViewById(R.id.lblDetalleNombre);
        lblDescripcion = findViewById(R.id.lblDetalleDescripcion);
        lblPoblacion = findViewById(R.id.lblDetallePoblacion);
        lblLugar = findViewById(R.id.lblDetalleLugar);
        lblFecha = findViewById(R.id.lblDetalleFecha);
        lblHora = findViewById(R.id.lblDetalleHora);

        selectedActividad = (Actividad) getIntent().getSerializableExtra("selectedActividad");
        lblNombre.setText(selectedActividad.getNombreActividad());
        lblDescripcion.setText(selectedActividad.getDescripcionActividad());
        lblPoblacion.setText(selectedActividad.getPublico());
        lblLugar.setText(selectedActividad.getLugar());
        lblFecha.setText(selectedActividad.getFecha());
        lblHora.setText(selectedActividad.getHora());

        //---------------------------
        String text = "Cerrar Sesión";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpanlogout = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_detalle_actividad.this, act_login.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanlogout, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtCerrarSesion.setText(ss);
        txtCerrarSesion.setMovementMethod(LinkMovementMethod.getInstance());
        //----------------------------
    }
}
