package com.example.rios_martinez_andre_pmdm_tarea5;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
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

        //lista de los contactos
        listaContactos = contactosDB.leerTodos();

        //conexión con el adaptador
        contactosAdapter = new ContactosAdapter(this,listaContactos, contactosDB);
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

            new AlertDialog.Builder(ListaContactosActivity.this)
                    .setTitle("Exportar Contactos")
                            .setMessage("Estás seguro de querer exportar los contactos a un JSON")
                                    .setPositiveButton("Sí",(dialog,i) -> {
                                        exportarJSON();
                                        //Toast.makeText(this, "Exportado JSON", Toast.LENGTH_SHORT).show();
                                    }).setNegativeButton("No",(dialog, i) -> dialog.dismiss()).show();


            return true;
        } else if (id == R.id.op_eliminar_todos) {

            new AlertDialog.Builder(this)
                    .setTitle("Eliminar Contctos")
                            .setMessage("¿Quieres eliminar todos los contactos?")
                                    .setPositiveButton("Sí",(dialog, which) -> {
                contactosDB.eliminarTodosContactos();
                listaContactos.clear();
                contactosAdapter.notifyDataSetChanged();
                Toast.makeText(this,"Contactos eliminados", Toast.LENGTH_SHORT).show();
            }).setNegativeButton("No",(dialog, which) -> dialog.dismiss()).show();


            return true;
        } else if (id == R.id.op_cerrar_sesion) {
            preferencias.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);

    }

    @Override
    protected void onResume() {
        super.onResume();

        listaContactos.clear();                     // limpiar lista
        listaContactos.addAll(contactosDB.leerTodos()); // volver a leer BBDD
        contactosAdapter.notifyDataSetChanged();    // refrescar RecyclerView
    }

    private void exportarJSON(){
        try{

            List<Contactos> contactos = contactosDB.leerTodos();

            Gson gson = new Gson();
            String json = gson.toJson(contactos);

            File carpeta = getExternalFilesDir(null);
            File fichero = new File(carpeta, "contactos.json");

            FileOutputStream fileOut = new FileOutputStream(fichero);
            fileOut.write(json.getBytes());
            fileOut.close();

            Toast.makeText(this, "Exportado: " + fichero.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error al exportar", Toast.LENGTH_SHORT).show();
        }
    }

}
