package com.example.rios_martinez_andre_pmdm_tarea5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import coil.Coil;
import coil.request.ImageRequest;
import database.ContactosDB;
import modelo.Contactos;

public class AgregarContactoActivity extends AppCompatActivity {

    private EditText etNombre, etTlf, etEmail, etImagen;
    private Button btnGuardar;
    private ImageView imageView;

    ContactosDB db;
    int idContacto = -1;


    protected void onCreate(Bundle c){
        super.onCreate(c);
        setContentView(R.layout.activity_agregarcontacto);

        etEmail = findViewById(R.id.etEmailC);
        etImagen = findViewById(R.id.etImagen);
        etNombre = findViewById(R.id.etNombre);
        etTlf = findViewById(R.id.etTlf);
        imageView = findViewById(R.id.imgContacto);
        btnGuardar = findViewById(R.id.btnGuardar);

        //Flecha de volver
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new ContactosDB(this);

        if (getIntent().hasExtra("id")){
            idContacto = getIntent().getIntExtra("id", -1);
            cargarContacto(idContacto);
            setTitle("Editar Contacto");
        } else {
            setTitle("Nuevo Contacto");
        }

        btnGuardar.setOnClickListener(V -> {
            guardarContacto();
            finish();
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void cargarContacto(int id){
        Contactos contactos = db.leerContacto(id);
        //Contactos contactos = null;

        etTlf.setText(contactos.getTelefono());
        etNombre.setText(contactos.getNombre());
        etEmail.setText(contactos.getEmail());
        etImagen.setText(contactos.getFoto());

        if (contactos.getFoto() != null && !contactos.getFoto().isEmpty()){
            Coil.imageLoader(this).enqueue(
                    new ImageRequest.Builder(this)
                            .data(contactos.getFoto())
                            .target(imageView)
                            .build()
            );
        }else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }



    }

    private void guardarContacto(){


        Contactos contactos = new Contactos(
                idContacto,
                etNombre.getText().toString(),
                etTlf.getText().toString(),
                etEmail.getText().toString(),
                etImagen.getText().toString()
        );

        if (idContacto == -1){
            db.insertar(contactos);
        } else {
            db.actualizar(contactos);
        }

        finish();
    }

}
