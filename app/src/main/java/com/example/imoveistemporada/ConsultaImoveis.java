package com.example.imoveistemporada;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ConsultaImoveis extends AppCompatActivity implements ImovelAdapter.OnImovelClickListener {

    private Button voltar;
    private RecyclerView recyclerView;
    private ImovelAdapter imovelAdapter;
    private List<Imovel> imoveisList;

    private DatabaseReference imoveisRef = FirebaseDatabase.getInstance().getReference("Imoveis");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_imoveis);

        // Inicializar elementos de interface
        voltar = findViewById(R.id.voltar);

        // Configurar o RecyclerView e o Adapter
        recyclerView = findViewById(R.id.recyclerViewImoveis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imoveisList = new ArrayList<>();
        imovelAdapter = new ImovelAdapter(imoveisList);
        recyclerView.setAdapter(imovelAdapter);

        // Configurar um ouvinte de clique para o botão "voltar"
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Voltar para a atividade principal
                Intent intent = new Intent(ConsultaImoveis.this, Principal.class);
                startActivity(intent);
            }
        });

        // Configurar um ouvinte de clique para o Adapter
        imovelAdapter.setOnImovelClickListener(this);

        // Adicionar um ouvinte para recuperar os dados do Firebase
        imoveisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Limpar a lista antes de adicionar novos dados
                imoveisList.clear();

                // Iterar sobre os dados no dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Imovel imovel = snapshot.getValue(Imovel.class);
                    if (imovel != null) {
                        // Adicionar o imóvel à lista
                        imoveisList.add(imovel);
                    }
                }

                // Notificar o Adapter sobre a mudança nos dados
                imovelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Lidar com erro de leitura no banco de dados
                // Por exemplo, exibir uma mensagem de erro
                Log.e("ConsultaImoveis", "Erro ao ler dados do Firebase", databaseError.toException());
                Toast.makeText(ConsultaImoveis.this, "Erro ao ler dados do Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método da interface de clique que será chamado quando um item for clicado
    @Override
    public void onImovelClick(Imovel imovel) {
        // Criar um Intent para abrir a atividade de Detalhes do Imóvel
        Intent intent = new Intent(ConsultaImoveis.this, DetalhesImovel.class);
        // Passar informações extras, se necessário
        intent.putExtra("imovel_id", imovel.getIdImovel());
        startActivity(intent);
    }
}
