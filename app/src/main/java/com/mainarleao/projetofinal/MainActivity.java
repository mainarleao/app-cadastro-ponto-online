package com.mainarleao.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario;
    EditText etSenha;
    Button btLogar;
    Button btRegistrar;

    public static String usuario;
    public static String senha;

    FirebaseDatabase database;
    DatabaseReference baseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etSenha = findViewById(R.id.etSenha);
        btLogar = findViewById(R.id.btLogar);
        btRegistrar = findViewById(R.id.btRegistrar);

        database = FirebaseDatabase.getInstance();
        baseRef = database.getReference();

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(usuario, senha);
            }
        });

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = etUsuario.getText().toString();
                senha = etSenha.getText().toString();

                logar(usuario, senha);
            }
        });
    }

    private void logar(final String nomeUsuario, final String senhaUsuario){
        baseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("usuario").child(nomeUsuario).getValue()==null){

                    Toast.makeText(MainActivity.this, "Usuário inexistente. " +
                                    "Se deseja registrar como um novo usuário, clique no botão 'Registrar'!",
                            Toast.LENGTH_LONG).show();

                } else {
                    if(dataSnapshot.child("usuario").child(nomeUsuario).child("senha").
                            getValue().equals(md5(senhaUsuario))){
                        proximaJanela();
                    } else {
                        Toast.makeText(MainActivity.this, "Senha incorreta!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void proximaJanela(){
        Intent novaJanela = new Intent(this, Menu.class);
        startActivity(novaJanela);
    }

    private void registrar(final String nomeUsuario, final String senhaUsuario){
        baseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                baseRef.child("usuario").child(nomeUsuario).child("senha").setValue(md5(senhaUsuario));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static final String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {//   i = i + 1
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }

}