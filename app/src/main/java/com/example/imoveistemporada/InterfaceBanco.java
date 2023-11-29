package com.example.imoveistemporada;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InterfaceBanco
{
    FirebaseDatabase database;

    public InterfaceBanco(String no)
    {
        DatabaseReference myRef = database.getReference(no);
    }

    public void Gravardados()
    {

    }
}
