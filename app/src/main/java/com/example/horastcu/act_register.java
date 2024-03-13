package com.example.horastcu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.horastcu.modelo.Register;
import com.example.horastcu.modelo.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class act_register extends AppCompatActivity {

    //Definir variables
    EditText txtNombreR, txtCorreoR, txtContraR, txtConfContra,txtCarnet, txtApellido;
    TextView txtLinkLogin;
    //Spinner spnTipo;
    String tipo, tcu, horasAprobadas;
    Button btnRegistro;
    Register register = new Register();
    User user;

    ArrayList<User> listaUser = new ArrayList<>();
    ArrayAdapter<User> arrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    //onCreate

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_register);

        txtCorreoR = findViewById(R.id.txtCorreoR);
        txtNombreR = findViewById(R.id.txtNombreR);
        txtCarnet = findViewById(R.id.txtCarnet);
        txtContraR = findViewById(R.id.txtContraR);
        txtConfContra = findViewById(R.id.txtConfContra);
        txtLinkLogin = findViewById(R.id.txtLinkLogin);
        txtApellido = findViewById(R.id.txtApellido);

        /*spnTipo = findViewById(R.id.spnTipo);

        String[] spnData = new String[] {"Estudiante", "Profesor"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spnData);
        spnTipo.setAdapter(spnAdapter);*/


        btnRegistro = findViewById(R.id.btnRegistro);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        //---------------------------
        String text = "Ya tienes una cuenta?";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpanRegister = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_register.this, act_login.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanRegister, 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLinkLogin.setText(ss);
        txtLinkLogin.setMovementMethod(LinkMovementMethod.getInstance());
        //----------------------------

        //---------------Registro con Firebase--------------------------------------------------------------------------------
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tipo = spnTipo.getSelectedItem().toString();

                if(txtNombreR.getText().toString().isEmpty() || txtCorreoR.getText().toString().isEmpty()||
                        txtContraR.getText().toString().isEmpty()||
                        txtConfContra.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Por favor ingrese todos los datos",
                            Toast.LENGTH_LONG).show();
                }else{
                    user = new User(UUID.randomUUID().toString(),txtNombreR.getText().toString(),txtCarnet.getText().toString(),txtCorreoR.getText().toString(),
                            txtContraR.getText().toString(),txtConfContra.getText().toString(), tipo="", tcu="", horasAprobadas="0");
                    databaseReference.child("User").child(user.getUid()).setValue(user);
                    Toast.makeText(act_register.this,register.agregarUsuario(user),
                            Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(act_register.this, act_login.class);
                    startActivity(intent);
                    limpiar();

                }
            }
        });  //Registro con Firebase

    }//Fin del onCreate

    //------------------------------------------------------------------------------------------



    //------------------------------------------------------------------------------------------
    public void limpiar(){
        txtNombreR.setText("");
        txtCarnet.setText("");
        txtCorreoR.setText("");
        txtContraR.setText("");
        txtConfContra.setText("");
    }//Fin metodo limpia

}//Fin de la clase
