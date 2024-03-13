package com.example.horastcu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horastcu.modelo.Actividad;
import com.example.horastcu.modelo.AdapterA;
import com.example.horastcu.modelo.Reporte;
import com.example.horastcu.modelo.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class act_modulo_usuario extends AppCompatActivity {

    TextView txtCerrarSesion, txtvNombreEstudiante, txtvCarnetEstudiante,txtLlamar2, lblHorasCompletadas;
    Button btnDetalles, btnDetalles0,btnDetalles1, btnNuevoReporte,btnReportes;
    private RecyclerView listaActividades;
    private AdapterA adapterA;
    private DatabaseReference actividadReference;
    private DatabaseReference reporteReference;
    private DatabaseReference databaseReference;
    private List<Actividad> actividades;

    User loggedEstudiante;
    ArrayList<Actividad> listActividad = new ArrayList<>();
    ArrayAdapter<Actividad> arrayAdapter;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_modulo_usuario);

        txtCerrarSesion = findViewById(R.id.txtCerrarSesion);
        txtvNombreEstudiante = findViewById(R.id.txtvNombreEstudiante);
        txtvCarnetEstudiante = findViewById(R.id.txtvCarnetEstudiante);
        lblHorasCompletadas = findViewById(R.id.lblHorasCompletadas);
        btnNuevoReporte = findViewById(R.id.btnNuevoReporte);
        btnReportes = findViewById(R.id.btnReportes);
        txtLlamar2 = findViewById(R.id.txtLlamar2);
        listaActividades = findViewById(R.id.listaActividades);

        loggedEstudiante = (User) getIntent().getSerializableExtra("loggedUser");
        txtvNombreEstudiante.setText(loggedEstudiante.getNombre());
        txtvCarnetEstudiante.setText(loggedEstudiante.getCarnet());
        lblHorasCompletadas.setText(loggedEstudiante.getHorasAprobadas());

        listaActividades = findViewById(R.id.listaActividades);
        listaActividades.setHasFixedSize(true);
        listaActividades.setLayoutManager(new LinearLayoutManager(this));
        actividades = new ArrayList<>();
        actividadReference = FirebaseDatabase.getInstance().getReference("Actividades");
        reporteReference = FirebaseDatabase.getInstance().getReference("Reporte");
        actividadReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Actividad actividad = postSnapshot.getValue(Actividad.class);
                    if (actividad.getTcu().equals(loggedEstudiante.getTcu())) {
                        actividades.add(actividad);
                    }
                }
                adapterA = new AdapterA(act_modulo_usuario.this,actividades);
                listaActividades.setAdapter(adapterA);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(act_modulo_usuario.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        //------------------------------------------------------------------------------------------------------------

        btnNuevoReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(act_login.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(act_modulo_usuario.this, act_nuevo_reporte.class);
                intent.putExtra("loggedEstudiante", loggedEstudiante);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(act_login.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(act_modulo_usuario.this, act_reportes.class);
                intent.putExtra("loggedEstudiante", loggedEstudiante);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        inicializarFireBase();


        //---------------------------
        String text = "Cerrar Sesión";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpanlogout = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_modulo_usuario.this, act_login.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanlogout, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtCerrarSesion.setText(ss);
        txtCerrarSesion.setMovementMethod(LinkMovementMethod.getInstance());
        //----------------------------

        //----------------------------

        String llamar = "Atención al cliente: 2534-5311";
        SpannableString ll = new SpannableString(llamar);

        ClickableSpan clickableSpanllamar = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                int telefono = 25345311;
                Intent llamar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telefono));
                startActivity(llamar);
            }
        };
        ll.setSpan(clickableSpanllamar, 21, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLlamar2.setText(ll);
        txtLlamar2.setMovementMethod(LinkMovementMethod.getInstance());
    }//Fin del onCreate

    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();//Se obtiene la instancia de firebase
        databaseReference=firebaseDatabase.getReference();//Se obtiene la referencia para uilizar la BD
    }//Fin del metodo inicializarFireBase

    //--------------------------------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //--------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent= new Intent(act_modulo_usuario.this, act_detalle_actividad.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
