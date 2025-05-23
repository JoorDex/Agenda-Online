package com.joordex.agenda_online;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pantalla_De_Carga extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);

        firebaseAuth = FirebaseAuth.getInstance();

        int tiempo = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*startActivity(new Intent(Pantalla_De_Carga.this, MainActivity.class));
                finish();*/
                VerificarUsuario();
            }
        }, tiempo);
    }

    private void VerificarUsuario(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null){
            startActivity(new Intent(Pantalla_De_Carga.this, MainActivity.class));
            finish();
        }
        else {
            startActivity(new Intent(Pantalla_De_Carga.this, MenuPrincipal.class));
            finish();
        }
    }
}