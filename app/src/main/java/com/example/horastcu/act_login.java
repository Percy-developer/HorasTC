package com.example.horastcu;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horastcu.modelo.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class act_login extends AppCompatActivity {
    //Definir variables

    User user;
    EditText txtCorreo, txtContra;
    TextView txtLinkRegister, txtLinkOlvl;
    Button btnLogin;

    //Declaracion Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //Fin Declaracion Firebase

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login);

        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);
        txtLinkRegister = findViewById(R.id.txtLinkRegistro);
        txtLinkOlvl = findViewById(R.id.txtLinkOlvl);

        btnLogin = findViewById(R.id.btnLogin);

        inicializarFireBase();

        //---------------------------
        String text = "No estás registrado?";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpanLogin = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_login.this, act_register.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanLogin, 0, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLinkRegister.setText(ss);
        txtLinkRegister.setMovementMethod(LinkMovementMethod.getInstance());
        //----------------------------

        //---------------------------
        String text1 = "¿Olvidaste tu contraseña?";
        SpannableString sss = new SpannableString(text1);

        ClickableSpan clickableSpanContra = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_login.this, act_olv_contrasena.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        sss.setSpan(clickableSpanContra, 0, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLinkOlvl.setText(sss);
        txtLinkOlvl.setMovementMethod(LinkMovementMethod.getInstance());
        //-----------Login con Firebase-----------------

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference usersRef = databaseReference.child("User");
                usersRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot objSnapshot: snapshot.getChildren()) {
                            user = objSnapshot.getValue(User.class);
                            if (txtCorreo != null && txtContra != null) {
                                if (user.getCorreo().equalsIgnoreCase(txtCorreo.getText().toString())) {
                                    if (user.getContraseña().equals(txtContra.getText().toString())) {

                                        if (user.getTipo().equals("")){
                                            Toast.makeText(act_login.this, "No se ha asignado un rol al usuario", Toast.LENGTH_SHORT).show();

                                        }else {
                                            if (user.getTipo().equals("Estudiante")) {
                                                Intent intent = new Intent(act_login.this, act_modulo_usuario.class);
                                                intent.putExtra("loggedUser", user);
                                                startActivity(intent);
                                                limpiar();
                                            }
                                            if (user.getTipo().equals("Profesor")) {
                                                Toast.makeText(act_login.this, "Bienvenido Profesor", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(act_login.this, act_modulo_profesor.class);
                                                intent.putExtra("loggedUser", user);
                                                startActivity(intent);
                                                limpiar();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(act_login.this, "Correo o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(act_login.this, "Error al ingresar: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });  //Login con Firebase

    }//Fin del onCreate

    //--------------Inicializamos Firebase----------------------------------------------------------------------
    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();//Se obtiene la instancia de firebase
        databaseReference = firebaseDatabase.getReference();//Se obtiene la referencia para uilizar la BD
    }

    //----------------Inicializamos Firebase--------------------------------------------------------------------


    public void limpiar() {
        txtCorreo.setText("");
        txtContra.setText("");

    }//Fin metodo limpiar
}
