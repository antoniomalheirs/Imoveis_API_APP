package com.example.imoveistemporada;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {
    private Button botaoCadastrarImoveis, botaoConsultarImoveis;
    private ViewPager2 viewPager;
    private ImovelAdapterCarrossel imovelAdapter;
    private List<Imovel> listaDeImoveis;
    private DatabaseReference referenciaImoveis = FirebaseDatabase.getInstance().getReference("Imoveis");
    private Handler manipuladorAutoScroll = new Handler(Looper.getMainLooper());
    private final long DELAY_AUTO_SCROLL = 3000; // Delay em milissegundos
    private boolean usuarioInteragiu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        viewPager = findViewById(R.id.viewPager);
        listaDeImoveis = new ArrayList<>();
        imovelAdapter = new ImovelAdapterCarrossel(listaDeImoveis);
        viewPager.setAdapter(imovelAdapter);

        // Adiciona um OnPageChangeListener para detectar interação do usuário
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                // Verifica se o usuário interagiu
                usuarioInteragiu = state == ViewPager2.SCROLL_STATE_DRAGGING;
            }
        });

        // Inicia a rotação automática
        iniciarAutoScroll();

        referenciaImoveis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeImoveis.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Imovel imovel = snapshot.getValue(Imovel.class);
                    if (imovel != null) {
                        listaDeImoveis.add(imovel);
                    }
                }
                imovelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Trata erro
                exibirToast("Erro ao ler dados do Firebase");
            }
        });

        botaoCadastrarImoveis = findViewById(R.id.Abririmoveis);
        botaoConsultarImoveis = findViewById(R.id.consultaimoveis);

        botaoCadastrarImoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, CadastroImovel.class);
                startActivity(intent);
            }
        });

        botaoConsultarImoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, ConsultaImoveis.class);
                startActivity(intent);
            }
        });
    }

    private void exibirToast(String mensagem) {
        Toast.makeText(Principal.this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void iniciarAutoScroll() {
        manipuladorAutoScroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!usuarioInteragiu) {
                    // Se o usuário não interagiu, avance para o próximo item
                    int itemAtual = viewPager.getCurrentItem();
                    int proximoItem = (itemAtual + 1) % listaDeImoveis.size();
                    viewPager.setCurrentItem(proximoItem);
                }
                // Continue o auto-scroll
                iniciarAutoScroll();
            }
        }, DELAY_AUTO_SCROLL);
    }
}
