package com.example.horastcu;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horastcu.modelo.Actividad;
import com.example.horastcu.modelo.AdapterA;
import com.example.horastcu.modelo.AdapterR;
import com.example.horastcu.modelo.RegistroActividad;
import com.example.horastcu.modelo.RegistroReporte;
import com.example.horastcu.modelo.Reporte;
import com.example.horastcu.modelo.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class act_nuevo_reporte extends AppCompatActivity {

    TextView txtCerrarSesion;
    EditText nombreActR, horasActR, descripcionActR, lugarActR, fechaActR, horaActR;
    Button btnAgregarReporte, btnAbrirReportes;
    Reporte reporte;
    Adapter adapter;
    AdapterR adapterR;
    RegistroReporte registroReporte;

    User loggedEstudiante;

    ArrayList<Reporte> listaReporte = new ArrayList<>();
    ArrayAdapter<Reporte> arrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference actStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_nuevo_reporte);

        txtCerrarSesion = findViewById(R.id.txtCerrarSesion);
        btnAgregarReporte = findViewById(R.id.btnAgregarReporte);
        btnAbrirReportes = findViewById(R.id.btnAbrirReportes);

        nombreActR = findViewById(R.id.nombreActR);
        horasActR = findViewById(R.id.horasActR);
        descripcionActR = findViewById(R.id.descripcionActR);
        lugarActR = findViewById(R.id.lugarActR);
        fechaActR = findViewById(R.id.fechaActR);
        horaActR = findViewById(R.id.horaActR);

        actStorage = FirebaseStorage.getInstance().getReference("Reportes");//Referencia del Storage
        firebaseDatabase = FirebaseDatabase.getInstance();//Referencia de la base de datos
        databaseReference=firebaseDatabase.getReference("Reportes");

        loggedEstudiante = (User) getIntent().getSerializableExtra("loggedEstudiante");


        btnAgregarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreActR.getText().toString().isEmpty() || horasActR.getText().toString().isEmpty()||
                        descripcionActR.getText().toString().isEmpty() || lugarActR.getText().toString().isEmpty()||
                        fechaActR.getText().toString().isEmpty()||
                        horaActR.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Por favor ingrese todos los datos",
                            Toast.LENGTH_LONG).show();
                }else {
                    uploadFile();
                }
            }
        });

        btnAbrirReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirReportes();
            }
        });

        //---------------------------
        String text = "Cerrar Sesión";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpanlogout = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_nuevo_reporte.this, act_login.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanlogout, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtCerrarSesion.setText(ss);
        txtCerrarSesion.setMovementMethod(LinkMovementMethod.getInstance());
        //----------------------------
    }

    private void uploadFile(){
        String estado = "Revision";
        int horasEstimadas = Integer.parseInt(horasActR.getText().toString());
        String correoEstudiante = loggedEstudiante.getCorreo();

        Toast.makeText(act_nuevo_reporte.this,"Reporte Guardado Correctamente",Toast.LENGTH_SHORT).show();
        Reporte reporte = new Reporte(UUID.randomUUID().toString(), nombreActR.getText().toString(),
                 horasEstimadas, descripcionActR.getText().toString(), lugarActR.getText().toString(),
                fechaActR.getText().toString(), horaActR.getText().toString(), estado, correoEstudiante);
        //String reporteId = databaseReference.push().getKey();
        databaseReference.child(reporte.getUid()).setValue(reporte);
        Intent intent= new Intent(act_nuevo_reporte.this, act_reportes.class);
        intent.putExtra("loggedEstudiante", loggedEstudiante);
        startActivity(intent);
    }
    private void abrirReportes(){
        Intent intent = new Intent(act_nuevo_reporte.this, act_reportes.class);
        startActivity(intent);
    }

    public void limpiar(){
        nombreActR.setText("");
        horasActR.setText("");
        descripcionActR.setText("");
        lugarActR.setText("");
        fechaActR.setText("");
        horaActR.setText("");
    }//Fin metodo limpia
}
