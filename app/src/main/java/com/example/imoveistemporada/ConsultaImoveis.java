package com.example.imoveistemporada;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ConsultaImoveis extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ImovelAdapter imovelAdapter;
    private List<Imovel> imoveisList;

    private DatabaseReference imoveisRef = FirebaseDatabase.getInstance().getReference("Imoveis");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_imoveis);

        // Configurar o RecyclerView e o Adapter
        recyclerView = findViewById(R.id.recyclerViewImoveis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imoveisList = new ArrayList<>();
        imovelAdapter = new ImovelAdapter(imoveisList);
        recyclerView.setAdapter(imovelAdapter);

        // Recuperar referência ao nó "Imoveis" no Firebase


        // Adicionar um listener para recuperar os dados
        imoveisRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Limpar a lista antes de adicionar novos dados
                imoveisList.clear();

                // Iterar sobre os dados no dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Imovel imovel = snapshot.getValue(Imovel.class);
                    if (imovel != null) {
                        imoveisList.add(imovel);
                    }
                }

                // Notificar o Adapter sobre a mudança nos dados
                imovelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Lidar com erro de leitura no banco de dados
                // Por exemplo, exibir uma mensagem de erro
                Log.e("ConsultaImoveis", "Erro ao ler dados do Firebase", databaseError.toException());
                Toast.makeText(ConsultaImoveis.this, "Erro ao ler dados do Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

