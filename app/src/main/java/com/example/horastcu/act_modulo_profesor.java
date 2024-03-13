package com.example.horastcu;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.google.android.gms.common.SignInButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class act_modulo_profesor extends AppCompatActivity {
    private RecyclerView listaActividades;
    private AdapterA adapterA;
    private DatabaseReference actividadesRef, usersRef;
    private List<Actividad> actividades;

    TextView txtCerrarSesion, txtvNombreProfesor, txtLlamar, lblTCProfe;
    TableLayout tbReportesEstudiantes;
    TableRow trinicio;
    Button btnFormActividad, btnVerReportesEstudiante;
    User loggedProfesor, usuario;
    Actividad actividad;
    ArrayList<Actividad> listActividad = new ArrayList<>();
    ArrayAdapter<Actividad> arrayAdapter;
    FirebaseDatabase firebaseDatabase;

    StorageReference actStorage;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_modulo_profesor);


        txtCerrarSesion = findViewById(R.id.txtCerrarSesionP);
        txtvNombreProfesor = findViewById(R.id.txtvNombreProfesor);
        txtLlamar = findViewById(R.id.txtLlamar);
        lblTCProfe = findViewById(R.id.lblTCProfe);
        btnFormActividad = findViewById(R.id.btnFormActividad);
        //btnVerReportesEstudiante = findViewById(R.id.btnVerReportesEstudiante);
        listaActividades = findViewById(R.id.listaActividades);
        tbReportesEstudiantes = findViewById(R.id.tbReportesEstudiantes);
        trinicio = findViewById(R.id.trInicio);

        inicializarFireBase();

        loggedProfesor = (User) getIntent().getSerializableExtra("loggedUser");

        listaActividades = findViewById(R.id.listaActividades);
        listaActividades.setHasFixedSize(true);
        listaActividades.setLayoutManager(new LinearLayoutManager(this));
        actividades = new ArrayList<>();
        actividadesRef = FirebaseDatabase.getInstance().getReference("Actividades");
        actividadesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Actividad actividad = postSnapshot.getValue(Actividad.class);
                    if (actividad.getTcu().equals(loggedProfesor.getTcu())){
                        actividades.add(actividad);
                    }
                }
                adapterA = new AdapterA(act_modulo_profesor.this,actividades);
                listaActividades.setAdapter(adapterA);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(act_modulo_profesor.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference user = usersRef.child("User");
        user.orderByChild("tcu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Limpiar la tabla antes de agregar nuevos datos
                tbReportesEstudiantes.removeAllViews();

                for (DataSnapshot reportSnapshot : snapshot.getChildren()) {

                    usuario = reportSnapshot.getValue(User.class);
                    if (usuario.getTcu().equalsIgnoreCase(loggedProfesor.getTcu())) {
                        if (usuario.getTipo().equalsIgnoreCase("estudiante")) {
                            agregarFilaATabla(usuario);
                        }
                    }
                    //Toast.makeText(act_modulo_profesor.this, usuario.getCorreo(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores si es necesario
                Toast.makeText(getApplicationContext(), "Error al leer datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Mostrar los datos del profesor loggeado
        if ((User) getIntent().getSerializableExtra("loggedUser") != null) {
            loggedProfesor = (User) getIntent().getSerializableExtra("loggedUser");
        } else {
            loggedProfesor = (User) getIntent().getSerializableExtra("loggedProfesor");
        }
        txtvNombreProfesor.setText(loggedProfesor.getNombre());
        lblTCProfe.setText(loggedProfesor.getTcu());


        btnFormActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(act_login.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(act_modulo_profesor.this, act_crear_actividad.class);
                intent.putExtra("loggedProfesor",loggedProfesor);
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
                Intent intent= new Intent(act_modulo_profesor.this, act_login.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanlogout, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtCerrarSesion.setText(ss);
        txtCerrarSesion.setMovementMethod(LinkMovementMethod.getInstance());
        //----------------------------

        String llamar = "Atención al cliente: 2534-5311";
        SpannableString ll = new SpannableString(llamar);

        ClickableSpan clickableSpanllamar = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                int telefono = 25345311;
                Intent llamar = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+telefono));
                startActivity(llamar);
            }
        };
        ll.setSpan(clickableSpanllamar, 21, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLlamar.setText(ll);
        txtLlamar.setMovementMethod(LinkMovementMethod.getInstance());

    }//Fin del onCreate

    //--------------------------------------------------------------------------------------------------------------------------

    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();//Se obtiene la instancia de firebase
        usersRef=firebaseDatabase.getReference();//Se obtiene la referencia para uilizar la BD
    }//Fin del metodo inicializarFireBase

    //--------------------------------------------------------------------------------------------------------------------------
    private void agregarFilaATabla(User usuario) {
        TableRow row = new TableRow(this);

        // Añade celdas con datos
        TextView textViewNombre = new TextView(this);
        textViewNombre.setText(usuario.getNombre());
        row.addView(textViewNombre);
        textViewNombre.setPadding(5,5,20,5);

        TextView textViewCorreo = new TextView(this);
        textViewCorreo.setText(usuario.getCorreo());
        row.addView(textViewCorreo);
        textViewCorreo.setPadding(5,5,20,5);

        TextView textViewHoras = new TextView(this);
        textViewHoras.setText("20");
        row.addView(textViewHoras);
        textViewHoras.setPadding(5,5,20,5);


        btnVerReportesEstudiante = new Button(this);
        btnVerReportesEstudiante.setText("Ver Reportes");
        btnVerReportesEstudiante.setTextColor(Color.WHITE);
        btnVerReportesEstudiante.setTextSize(10);
        btnVerReportesEstudiante.setTag(usuario);
        btnVerReportesEstudiante.setBackgroundResource(R.drawable.btn_color_celeste);
        btnVerReportesEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User userReportes = (User) view.getTag();
                Intent intent= new Intent(act_modulo_profesor.this, act_reportes.class);
                intent.putExtra("userReportes" , userReportes);
                intent.putExtra("loggedProfesor", loggedProfesor);
                startActivity(intent);
            }
        });
        row.addView(btnVerReportesEstudiante);

        // Agrega la fila a la tabla
        tbReportesEstudiantes.addView(row);
    }
    //--------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //--------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent= new Intent(act_modulo_profesor.this, act_detalle_actividad.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

}