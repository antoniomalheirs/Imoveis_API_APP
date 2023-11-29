package com.example.imoveistemporada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Principal extends AppCompatActivity
{
    private Button gereimoveis, consultaimoveis;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        gereimoveis = findViewById(R.id.Abririmoveis);
        consultaimoveis = findViewById(R.id.consultaimoveis);

        gereimoveis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Principal.this, CadastroImovel.class);
                startActivity(intent);

                finish();
            }
        });

        consultaimoveis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Principal.this, ConsultaImoveis.class);
                startActivity(intent);

                finish();
            }
        });
    }
}