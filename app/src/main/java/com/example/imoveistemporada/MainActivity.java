package com.example.imoveistemporada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button loga, registra;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loga = findViewById(R.id.login);
        registra = findViewById(R.id.registrar);
        Intent intent = new Intent(this, Principal.class);
        Intent intent2 = new Intent(this, Registro.class);
        
        loga.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(intent);
            }
        });

        registra.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(intent2);
            }
        });

    }
}