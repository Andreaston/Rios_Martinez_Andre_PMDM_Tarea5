package com.example.rios_martinez_andre_pmdm_tarea5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adaptador.ContactosAdapter;
import database.ContactosDB;
import modelo.Contactos;

public class ListaContactosActivity extends AppCompatActivity {

    private RecyclerView recicladorContactos;
    private List<Contactos> listaContactos;
    private SharedPreferences preferencias;
    private ContactosAdapter contactosAdapter;
    private ContactosDB contactosDB;
    private FloatingActionButton btnAgregar;

    @Override
    protected void onCreate(Bundle d){
        super.onCreate(d);
        setContentView(R.layout.activity_listacontactos);

        btnAgregar = findViewById(R.id.btnFlotante);

        //preferencias de sesion
        preferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);

        //BBDD
        contactosDB = new ContactosDB(this);

        //reciclador para los contactos
        recicladorContactos = findViewById(R.id.recicladorContactos);
        recicladorContactos.setLayoutManager( new LinearLayoutManager(this));

        //lista de los contactos -- FALTA METODO LEER
        //listaContactos =

        //conexión con el adaptador
        contactosAdapter = new ContactosAdapter(this,listaContactos);
        recicladorContactos.setAdapter(contactosAdapter);

        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(this,AgregarContactoActivity.class);
            startActivity(intent);
        });

    }

    //Meter las opciones del toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    //gestionar los clicks en las opciones del toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if (id == R.id.op_exportar){
            //FALTA exportar
            return true;
        } else if (id == R.id.op_eliminar_todos) {
            //FALTA Eliminar
            listaContactos.clear();
            contactosAdapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.op_cerrar_sesion) {
            preferencias.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);

    }

}
