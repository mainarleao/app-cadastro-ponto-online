package com.mainarleao.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cadastrar extends AppCompatActivity {

    private EditText etdData;
    private EditText ettEntrada;
    private EditText ettIdaIntervalo;
    private EditText ettVoltaIntervalo;
    private EditText ettSaida;
    private Button btInserir;
    private Button btVoltar;


    FirebaseDatabase database;
    DatabaseReference baseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        etdData = (EditText) findViewById(R.id.etdData);
        ettEntrada = (EditText) findViewById(R.id.ettEntrada);
        ettIdaIntervalo = (EditText) findViewById(R.id.ettIdaIntervalo);
        ettVoltaIntervalo = (EditText) findViewById(R.id.ettVoltaIntervalo);
        ettSaida = (EditText) findViewById(R.id.ettSaida);
        btInserir = (Button) findViewById(R.id.btInserir);
        btVoltar = (Button) findViewById(R.id.btVoltar);

        database = FirebaseDatabase.getInstance();
        baseRef = database.getReference();

        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data = etdData.getText().toString();
                String entrada = ettEntrada.getText().toString();
                String idaIntervalo = ettIdaIntervalo.getText().toString();
                String voltaIntervalo = ettVoltaIntervalo.getText().toString();
                String saida = ettSaida.getText().toString();

                cadastrar(MainActivity.usuario, data, entrada, idaIntervalo, voltaIntervalo, saida);

                limpar();
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Cadastrar.this, Menu.class);
                startActivity(it);
            }
        });
    }

    private void cadastrar(final String nomeUsuario, final String data, final String entrada, final String idaIntervalo, final String voltaIntervalo, final String saida){
        baseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                baseRef.child("usuario").child(nomeUsuario).child("data").setValue(data);
                baseRef.child("usuario").child(nomeUsuario).child("entrada").setValue(entrada);
                baseRef.child("usuario").child(nomeUsuario).child("idaIntervalo").setValue(idaIntervalo);
                baseRef.child("usuario").child(nomeUsuario).child("voltaIntervalo").setValue(voltaIntervalo);
                baseRef.child("usuario").child(nomeUsuario).child("saida").setValue(saida);

                Toast.makeText(getBaseContext(), "Horario Cadastrado!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void limpar(){
        etdData.setText(null);
        ettEntrada.setText(null);
        ettIdaIntervalo.setText(null);
        ettVoltaIntervalo.setText(null);
        ettSaida.setText(null);
    }
}