package com.example.horastcu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.horastcu.modelo.Actividad;
import com.example.horastcu.modelo.Reporte;
import com.example.horastcu.modelo.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class act_reportes extends AppCompatActivity {

    Reporte reporte, reporteEstado;
    TextView txtCerrarSesionP, lblFecha, lblActividad, lblLugar, lblHoras;
    Spinner spsRevision;
    TableLayout tbReportes;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    User loggedEstudiante, loggedProfesor, userReportes;

    String seleccionPrevia = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_reportes);

        lblFecha = findViewById(R.id.lblFecha);
        lblActividad = findViewById(R.id.lblActividad);
        lblLugar = findViewById(R.id.lblLugar);
        lblHoras = findViewById(R.id.lblHoras);
        spsRevision = findViewById(R.id.spsRevision);
        tbReportes = findViewById(R.id.tbReportes);
        txtCerrarSesionP = findViewById(R.id.txtCerrarSesionP);

        inicializarFireBase();

        loggedEstudiante = (User) getIntent().getSerializableExtra("loggedEstudiante");
        loggedProfesor = (User) getIntent().getSerializableExtra("loggedProfesor");

        //agregarFilaATabla(reporte);


        // Configurar el Spinner
        String[] spnEstado = new String[]{"Aprobado", "Rechazado"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spnEstado);
        spsRevision.setAdapter(spnAdapter);

        // Configurar la lógica de Firebase para mostrar datos en la tabla
        DatabaseReference reportesRef = databaseReference.child("Reportes");
        reportesRef.orderByChild("nombreR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Limpiar la tabla antes de agregar nuevos datos
                tbReportes.removeAllViews();

                for (DataSnapshot reportSnapshot : snapshot.getChildren()) {
                    reporte = reportSnapshot.getValue(Reporte.class);

                    if (loggedEstudiante != null) {
                        loggedProfesor = null;
                        if (reporte.getCorreoEstudianteR().equalsIgnoreCase(loggedEstudiante.getCorreo())) {
                            agregarFilaATabla(reporte);
                        }
                    }

                    if (loggedProfesor != null) {
                        loggedEstudiante = null;
                        userReportes = (User) getIntent().getSerializableExtra("userReportes");

                        if (reporte.getCorreoEstudianteR().equalsIgnoreCase(userReportes.getCorreo())) {
                            agregarFilaATabla(reporte);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores si es necesario
                Toast.makeText(getApplicationContext(), "Error al leer datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //---------------------------
        String text = "Cerrar Sesión";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpanlogout = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_reportes.this, act_login.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanlogout, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtCerrarSesionP.setText(ss);
        txtCerrarSesionP.setMovementMethod(LinkMovementMethod.getInstance());
        //----------------------------
    }//Fin del OnCreate

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

    private void agregarFilaATabla(Reporte reporte) {
        TableRow row = new TableRow(this);


        // Añade celdas con datos

        HorizontalScrollView scrollViewFecha = new HorizontalScrollView(this);
        TextView textViewFecha = new TextView(this);
        textViewFecha.setText(reporte.getFechaR());
        textViewFecha.setPadding(0,20,20,20);
        textViewFecha.setTextColor(Color.BLACK);
        textViewFecha.setTextSize(2,16);
        textViewFecha.setMaxWidth(190);
        scrollViewFecha.addView(textViewFecha);
        row.addView(scrollViewFecha);

        HorizontalScrollView scrollViewNombre = new HorizontalScrollView(this);
        TextView textViewNombre = new TextView(this);
        textViewNombre.setText(reporte.getNombreR());
        textViewNombre.setPadding(20,20,20,20);
        textViewNombre.setTextColor(Color.BLACK);
        textViewNombre.setTextSize(2,16);
        textViewNombre.setMaxWidth(215);
        scrollViewNombre.addView(textViewNombre);
        row.addView(scrollViewNombre);

        HorizontalScrollView scrollViewLugar = new HorizontalScrollView(this);
        TextView textViewLugar = new TextView(this);
        textViewLugar.setText(reporte.getLugarR());
        textViewLugar.setPadding(20,20,20,20);
        textViewLugar.setTextColor(Color.BLACK);
        textViewLugar.setTextSize(2,16);
        textViewLugar.setMaxWidth(225);
        scrollViewLugar.addView(textViewLugar);
        row.addView(scrollViewLugar);

        HorizontalScrollView scrollViewHoras = new HorizontalScrollView(this);
        TextView textViewHoras = new TextView(this);
        textViewHoras.setText(String.valueOf(reporte.getHorasT()));
        textViewHoras.setPadding(20,20,20,20);
        textViewHoras.setTextColor(Color.BLACK);
        textViewHoras.setTextSize(2,16);
        textViewHoras.setMaxWidth(185);
        scrollViewHoras.addView(textViewHoras);
        row.addView(scrollViewHoras);

        if (loggedEstudiante != null) {
            TextView textViewEstado = new TextView(this);
            textViewEstado.setText(reporte.getEstadoR());
            textViewEstado.setTextColor(Color.BLACK);
            textViewEstado.setPadding(20,20,20,20);
            textViewEstado.setTextSize(2,16);
            textViewEstado.setMaxWidth(190);
            row.addView(textViewEstado);
        }

        if (loggedProfesor != null) {
            Spinner spinner = new Spinner(this);
            spinner.setPadding(20,20,10,20);

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getSpinnerData());
            spinner.setAdapter(spinnerAdapter);

                        if (reporte.getCorreoEstudianteR().equalsIgnoreCase(userReportes.getCorreo())) {
                            spinner.setOnItemSelectedListener(null);
                            if (reporte.getEstadoR().equals("Aprobado")){
                                spinner.setOnItemSelectedListener(null);
                                spinner.setSelection(1);
                            }
                            if (reporte.getEstadoR().equals("Rechazado")) {
                                spinner.setOnItemSelectedListener(null);
                                spinner.setSelection(2);
                            }
                            if (reporte.getEstadoR().equals("Revision")) {
                                spinner.setOnItemSelectedListener(null);
                                spinner.setSelection(0);
                            }
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                    String selectedValue = parentView.getItemAtPosition(position).toString();
                                    seleccionPrevia = reporte.getEstadoR();

                                    DatabaseReference userRef = databaseReference.child("User").child(userReportes.getUid());
                                    DatabaseReference reportesRef = databaseReference.child("Reportes").child(reporte.getUid());

                                    if (reporte.getEstadoR().equals("Revision") || reporte.getEstadoR().equals("Rechazado")){
                                        if (selectedValue.equals("Aprobado")) {
                                            int sumaHoras = Integer.parseInt(userReportes.getHorasAprobadas())+reporte.getHorasT();
                                            //Toast.makeText(act_reportes.this,Integer.toString(sumaHoras), Toast.LENGTH_SHORT).show();
                                            Toast.makeText(act_reportes.this,seleccionPrevia, Toast.LENGTH_SHORT).show();
                                            userReportes.setHorasAprobadas(Integer.toString(sumaHoras));
                                            userRef.child("horasAprobadas").setValue(Integer.toString(sumaHoras));

                                            reporte.setEstadoR("Aprobado");
                                            reportesRef.child("estadoR").setValue("Aprobado");
                                        }
                                    }

                                    if (reporte.getEstadoR().equals("Aprobado") || reporte.getEstadoR().equals("Revision")) {
                                        if (selectedValue.equals("Rechazado")) {
                                            if (reporte.getEstadoR().equals("Aprobado")){
                                                int restaHoras = Integer.parseInt(userReportes.getHorasAprobadas())-reporte.getHorasT();
                                                userReportes.setHorasAprobadas(Integer.toString(restaHoras));
                                                userRef.child("horasAprobadas").setValue(Integer.toString(restaHoras));
                                            }
                                            reporte.setEstadoR("Rechazado");
                                            reportesRef.child("estadoR").setValue("Rechazado");
                                        }
                                    }

                                    if (reporte.getEstadoR().equals("Aprobado") || reporte.getEstadoR().equals("Rechazado")) {
                                        if (selectedValue.equals("Revision")) {
                                            if (reporte.getEstadoR().equals("Aprobado")){
                                                int restaHoras = Integer.parseInt(userReportes.getHorasAprobadas())-reporte.getHorasT();
                                                userReportes.setHorasAprobadas(Integer.toString(restaHoras));
                                                userRef.child("horasAprobadas").setValue(Integer.toString(restaHoras));
                                            }
                                            reporte.setEstadoR("Revision");
                                            reportesRef.child("estadoR").setValue("Revision");
                                        }
                                    }


                                    }



                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {

                                }
                            });


                        }


            //int defaultSelectedIndex = 2;
            //spinner.setSelection(defaultSelectedIndex);


            // Add the spinner to the row
            row.addView(spinner);
        }


        // Agrega la fila a la tabla
        tbReportes.addView(row);
    }

    private List<String> getSpinnerData() {
        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("Revision");
        spinnerData.add("Aprobado");
        spinnerData.add("Rechazado");
        return spinnerData;
    }



}