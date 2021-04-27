package com.mainarleao.projetofinal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Consulta extends AppCompatActivity {

    public EditText edtPesquisaData;
    public ListView ListaHorarios;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Data> listPessoa = new ArrayList<Data>();
    private ArrayAdapter<Data> arrayAdapterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        edtPesquisaData = (EditText) findViewById(R.id.edtPesquisaData);
        ListaHorarios = (ListView) findViewById(R.id.ListaHorarios);

        FirebaseApp.initializeApp(Consulta.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        eventoEdit();
    }

    private void eventoEdit() {
        edtPesquisaData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String data = edtPesquisaData.getText().toString().trim();
                pesquisarData(data);

            }
        });
    }

    private void pesquisarData(String data) {
        Query query;
        if (data.equalsIgnoreCase("")){
            query = databaseReference.child("usuario").orderByChild("data");
        }else{
            query = databaseReference.child("usuario").orderByChild("data").startAt(data).endAt(data+"\uf8ff");
        }

        listPessoa.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Data d = objSnapshot.getValue(Data.class);
                    listPessoa.add(d);
                }

                arrayAdapterData = new ArrayAdapter<Data>(Consulta.this,
                        android.R.layout.simple_list_item_1,listPessoa);

                ListaHorarios.setAdapter(arrayAdapterData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        pesquisarData("");
    }
}