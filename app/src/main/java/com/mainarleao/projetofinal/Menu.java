package com.mainarleao.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {

    private Button btCadastrar;
    private Button btBuscar;
    private TextView txtBemVindo;

    FirebaseDatabase database;
    DatabaseReference baseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        btBuscar = (Button) findViewById(R.id.btBuscar);
        txtBemVindo = (TextView) findViewById(R.id.txtBemVindo);

        database = FirebaseDatabase.getInstance();
        baseRef = database.getReference();

        carregarDados(MainActivity.usuario);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Menu.this, Cadastrar.class);
                startActivity(it);
            }
        });

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Menu.this, Consulta.class);
                startActivity(it);
            }
        });
    }

    private void carregarDados(final String nomeUsuario) {

        baseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String senha = dataSnapshot.child("usuario").child(nomeUsuario).child("senha").getValue().toString();
//                double altura = Double.parseDouble(dataSnapshot.child("usuarios").child(nomeUsuario).child("altura").getValue().toString());
//                double genero = Double.parseDouble(dataSnapshot.child("usuarios").child(nomeUsuario).child("genero").getValue().toString());

                txtBemVindo.setText("Bem-vindo " + nomeUsuario);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}