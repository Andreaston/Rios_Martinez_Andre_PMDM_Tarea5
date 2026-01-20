package com.example.rios_martinez_andre_pmdm_tarea5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    //Decalración de las preferencias
    private SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle c){
        super.onCreate(c);
        setContentView(R.layout.activity_login);

        EditText campoEmail = findViewById(R.id.etEmail);
        EditText campoContra = findViewById(R.id.etContrasenha);
        CheckBox guardar = findViewById(R.id.cbGuardar);
        Button btnLogin = findViewById(R.id.btnLogin);

        //Establezco el nuevo archivo donde se guardan las preferencias en modo privado
        preferencias = getSharedPreferences("PreferenciasUsuario",MODE_PRIVATE);

        //Guardar sesión si hay, sino false
        if (preferencias.getBoolean("guardarSesion", false)){
            Intent intent = new Intent(this, ListaContactosActivity.class);
            startActivity(intent);
            return;
        }

        //Si se pulsa el botón
        btnLogin.setOnClickListener(x -> {
            //Pillamos los textos de los dos campos
            String email = campoEmail.getText().toString();
            String contrasenha = campoContra.getText().toString();

            //Si el usuario marca el check...
            if (guardar.isChecked()){
                //Abre el archivo de preferencias
                SharedPreferences.Editor editor = preferencias.edit();
                //guarada los datos clave-valor
                editor.putString("email", email);
                editor.putString("contrasenha", contrasenha);
                //recuerda que está pulsado
                editor.putBoolean("guardarSesion", true);
                //Confirma cambios
                editor.apply();

            }

            Intent intent = new Intent(this, ListaContactosActivity.class);
            startActivity(intent);

        });


    }

}
