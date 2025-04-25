package com.joordex.agenda_online;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.joordex.agenda_online.AgregarNota.Agregar_nota;
import com.joordex.agenda_online.Archivados.Notas_archivadas;
import com.joordex.agenda_online.ListarNotas.Listar_notas;
import com.joordex.agenda_online.Perfil.Perfil_usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {

    Button CerrarSesion, AgregarNotas, ListarNotas, Archivados, Perfil, AcerdaDe;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView UidPrincipal, NombresPrincipal, CorreoPrincipal;
    ProgressBar progressBarDatos;
    LinearLayoutCompat linear_correo, linear_nombres;

    DatabaseReference Usuarios;

    Dialog dialog_informacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agenda Online");

        UidPrincipal = findViewById(R.id.UidPrincipal);
        NombresPrincipal = findViewById(R.id.NombresPrincipal);
        CorreoPrincipal = findViewById(R.id.CorreoPrincipal);
        progressBarDatos = findViewById(R.id.progressBarDatos);
        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        dialog_informacion = new Dialog(this);

        linear_correo = findViewById(R.id.linear_correo);
        linear_nombres = findViewById(R.id.linear_nombres);

        AgregarNotas = findViewById(R.id.AgregarNotas);
        ListarNotas = findViewById(R.id.ListarNotas);
        Archivados = findViewById(R.id.Archivados);
        Perfil = findViewById(R.id.Perfil);
        AcerdaDe = findViewById(R.id.AcerdaDe);
        CerrarSesion = findViewById(R.id.CerrarSesion);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        AgregarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uid_usuario = UidPrincipal.getText().toString();
                String correo_usuario = CorreoPrincipal.getText().toString();

                Intent intent = new Intent(MenuPrincipal.this, Agregar_nota.class);
                intent.putExtra("Uid", uid_usuario);
                intent.putExtra("Correo", correo_usuario);
                startActivity(intent);
            }
        });

        ListarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Listar_notas.class));
                Toast.makeText(MenuPrincipal.this, "Listar notas", Toast.LENGTH_SHORT).show();
            }
        });

        Archivados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Notas_archivadas.class));
                Toast.makeText(MenuPrincipal.this, "Notas archivadas (Próximamente)", Toast.LENGTH_SHORT).show();
            }
        });

        Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, Perfil_usuario.class));
                Toast.makeText(MenuPrincipal.this, "Perfil del usuario (Próximamente)", Toast.LENGTH_SHORT).show();
            }
        });

        AcerdaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Informacion();
            }
        });

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalirAplicacion();
            }
        });

    }


    @Override
    protected void onStart() {
        ComprobarInicioDeSesion();
        super.onStart();
    }

    private void ComprobarInicioDeSesion(){
        if (user!=null){
            //El usuario inicio sesion
            CargaDeDatos();
        }else {
            //Lo mandara alv jaja
            startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
            finish();
        }
    }

    private void CargaDeDatos(){
        Usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Si el usuarios existe
                if(snapshot.exists()){
                    //Progress bar se oculta
                    progressBarDatos.setVisibility(View.GONE);
                    //Hago visible
                    //UidPrincipal.setVisibility(View.VISIBLE);
                    //NombresPrincipal.setVisibility(View.VISIBLE);
                    //CorreoPrincipal.setVisibility(View.VISIBLE);
                    linear_correo.setVisibility(View.VISIBLE);
                    linear_nombres.setVisibility(View.VISIBLE);

                    //Obtengo los datos
                    String uid = "" + snapshot.child("uid").getValue();
                    String nombre = "" + snapshot.child("nombres").getValue();
                    String correo = "" + snapshot.child("correo").getValue();

                    //Seteo datos
                    UidPrincipal.setText(uid);
                    NombresPrincipal.setText(nombre);
                    CorreoPrincipal.setText(correo);

                    //Habilitar botones del menu
                    AgregarNotas.setEnabled(true);
                    ListarNotas.setEnabled(true);
                    Archivados.setEnabled(true);
                    Perfil.setEnabled(true);
                    AcerdaDe.setEnabled(true);
                    CerrarSesion.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
    }

    private void Informacion(){
        Button EntendidoInfo;

        dialog_informacion.setContentView(R.layout.cuadro_dialogo_informacion);

        EntendidoInfo = dialog_informacion.findViewById(R.id.EntendidoInfo);

        EntendidoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_informacion.dismiss();
            }
        });

        dialog_informacion.show();
        dialog_informacion.setCanceledOnTouchOutside(false);
    }
}