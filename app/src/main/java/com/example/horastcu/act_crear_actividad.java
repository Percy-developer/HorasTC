package com.example.horastcu;

import static android.content.Intent.ACTION_GET_CONTENT;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.horastcu.modelo.Actividad;
import com.example.horastcu.modelo.AdapterA;
import com.example.horastcu.modelo.RegistroActividad;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class act_crear_actividad extends AppCompatActivity {

    TextView txtCerrarSesion, txtImgSubida;
    String tcu;
    ImageView imgActividad, imgHome;
    Uri fotoTemp;
    EditText txtNombreActividad, txtDescripcionActividad, txtLugarActividad, txtHorasEstimadasActividad, txtHoraActividad, txtPublicoActividad, txtNombreImg;
    EditText txtFechaActividad;
    Button btnCrearActividad;
    Actividad actividad;
    Adapter adapter;
    AdapterA adapterA;
    RegistroActividad registroActividad;
    private final int PICK_IMAGE_REQUEST = 1;
    Button btnElegirImg;
    ProgressBar pbImg;
    User loggedProfesor;
    private StorageTask aUploadTask;

    ArrayList<Actividad> listaActividad = new ArrayList<>();
    ArrayAdapter<Actividad> arrayAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference actStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_crear_actividad);

        txtCerrarSesion = findViewById(R.id.txtCerrarSesion);
        btnCrearActividad = findViewById(R.id.btnCrearActividad);
        imgActividad = findViewById(R.id.imgActividad);
        txtFechaActividad = findViewById(R.id.txtFechaActividad);
        txtNombreActividad = findViewById(R.id.txtNombreActividad);
        txtDescripcionActividad = findViewById(R.id.txtDescripcionActividad);
        txtHorasEstimadasActividad = findViewById(R.id.txtHorasEstimadasActividad);
        txtHoraActividad = findViewById(R.id.txtHoraActividad);
        txtLugarActividad = findViewById(R.id.txtLugarActividad);
        txtPublicoActividad = findViewById(R.id.txtPublicoActividad);
        txtImgSubida = findViewById(R.id.txtImgSubida);
        btnElegirImg = findViewById(R.id.btnElegirImg);
        pbImg = findViewById(R.id.pbImg);
        imgHome = findViewById(R.id.imgHome);
        actStorage = FirebaseStorage.getInstance().getReference("Actividades");//Referencia del Storage
        firebaseDatabase = FirebaseDatabase.getInstance();//Referencia de la base de datos
        databaseReference=firebaseDatabase.getReference("Actividades");

        loggedProfesor = (User) getIntent().getSerializableExtra("loggedProfesor");

        txtImgSubida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirListaActividades();
            }
        });

        btnElegirImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    OpenFileChooser();
            }
        });

        btnCrearActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtNombreActividad.getText().toString().isEmpty() || txtDescripcionActividad.getText().toString().isEmpty()||
                        txtHorasEstimadasActividad.getText().toString().isEmpty() || txtHoraActividad.getText().toString().isEmpty()||
                        txtLugarActividad.getText().toString().isEmpty()||
                        txtPublicoActividad.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Por favor ingrese todos los datos",
                            Toast.LENGTH_LONG).show();
                }else {

                    if (aUploadTask != null && aUploadTask.isInProgress()) {
                        Toast.makeText(act_crear_actividad.this, "Subiendo Actividad", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                    }
                }
                /*Intent intent = new Intent(act_crear_actividad.this, act_modulo_profesor.class);
                startActivity(intent);
                limpiar();*/

                /*if(txtNombreActividad.getText().toString().isEmpty() || txtDescripcionActividad.getText().toString().isEmpty()||
                        txtHorasEstimadasActividad.getText().toString().isEmpty() || txtHoraActividad.getText().toString().isEmpty()||
                        txtLugarActividad.getText().toString().isEmpty()||
                        txtPublicoActividad.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Por favor ingrese todos los datos",
                            Toast.LENGTH_LONG).show();
                }else {
                    int horasEstimadas = Integer.parseInt(txtHorasEstimadasActividad.getText().toString());

                    actividad = new Actividad(txtNombreActividad.getText().toString(),
                    txtDescripcionActividad.getText().toString(), horasEstimadas,
                    txtLugarActividad.getText().toString(), txtPublicoActividad.getText().toString(),
                    txtFechaActividad.getText().toString(), txtHoraActividad.getText().toString(), imgActividad.toString() );
                    databaseReference.child("Actividad").child(actividad.getNombreActividad()).setValue(actividad);
                    Toast.makeText(act_crear_actividad.this, registroActividad.agregarActividad(actividad),
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(act_crear_actividad.this, act_modulo_profesor.class);
                    startActivity(intent);
                    limpiar();
                }

                 */

            }
        });

        //---------------------------
        String text = "Cerrar Sesión";
        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpanlogout = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(act_crear_actividad.this, act_login.class);
                //Toast.makeText(act_login.this,"Probando", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpanlogout, 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtCerrarSesion.setText(ss);
        txtCerrarSesion.setMovementMethod(LinkMovementMethod.getInstance());

        //-----------------------------------------------------------------------------------------------

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_crear_actividad.this, act_modulo_profesor.class);
                startActivity(intent);
            }
        });

    }//Fin del metodo onCreate


    private void OpenFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData()!= null){
            fotoTemp = data.getData();
            imgActividad.setImageURI(fotoTemp);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void uploadFile(){
        if(fotoTemp != null){
            StorageReference fileReference = actStorage.child(System.currentTimeMillis()
            +"."+getFileExtension(fotoTemp));

            aUploadTask = fileReference.putFile(fotoTemp).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pbImg.setProgress(0);
                        }
                    },5000);
                    int horasEstimadas = Integer.parseInt(txtHorasEstimadasActividad.getText().toString());

                    Toast.makeText(act_crear_actividad.this,"Actividad Guardada Correctamente",Toast.LENGTH_SHORT).show();
                    Actividad actividad = new Actividad(txtNombreActividad.getText().toString(),
                            txtDescripcionActividad.getText().toString(), horasEstimadas,
                            txtLugarActividad.getText().toString(), txtPublicoActividad.getText().toString(),
                            txtFechaActividad.getText().toString(), txtHoraActividad.getText().toString(), imgActividad.toString(),
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(), tcu="");
                    String actividadId = databaseReference.push().getKey();
                    databaseReference.child(actividadId).setValue(actividad);
                    Intent intent= new Intent(act_crear_actividad.this, act_modulo_profesor.class);
                    intent.putExtra("loggedProfesor", loggedProfesor);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(act_crear_actividad.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    pbImg.setProgress((int)progress);
                }
            });
        }else {
            Toast.makeText(this,"No se eligió la imagen", Toast.LENGTH_SHORT).show();
        }
    }
    private void abrirListaActividades(){
        Intent intent = new Intent(act_crear_actividad.this, act_modulo_profesor.class);
        startActivity(intent);
    }

    public void limpiar(){
        txtNombreActividad.setText("");
        txtDescripcionActividad.setText("");
        txtHorasEstimadasActividad.setText("");
        txtHoraActividad.setText("");
        txtFechaActividad.setText("");
        txtLugarActividad.setText("");
        txtPublicoActividad.setText("");
    }//Fin metodo limpia

}
