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
    private Button gereimoveis, consultaimoveis;
    private ViewPager2 viewPager;
    private ImovelAdapterCarrossel imovelAdapter;
    private List<Imovel> imoveisList;
    private DatabaseReference imoveisRef = FirebaseDatabase.getInstance().getReference("Imoveis");
    private Handler autoScrollHandler = new Handler(Looper.getMainLooper());
    private final long AUTO_SCROLL_DELAY = 3000; // Delay em milissegundos
    private boolean userInteracted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        viewPager = findViewById(R.id.viewPager);
        imoveisList = new ArrayList<>();
        imovelAdapter = new ImovelAdapterCarrossel(imoveisList);
        viewPager.setAdapter(imovelAdapter);

        // Adiciona um OnPageChangeListener para detectar interação do usuário
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                // Verifica se o usuário interagiu
                userInteracted = state == ViewPager2.SCROLL_STATE_DRAGGING;
            }
        });

        // Inicia a rotação automática
        startAutoScroll();

        imoveisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imoveisList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Imovel imovel = snapshot.getValue(Imovel.class);
                    if (imovel != null) {
                        imoveisList.add(imovel);
                    }
                }
                imovelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                showToast("Erro ao ler dados do Firebase");
            }
        });

        gereimoveis = findViewById(R.id.Abririmoveis);
        consultaimoveis = findViewById(R.id.consultaimoveis);

        gereimoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, CadastroImovel.class);
                startActivity(intent);
            }
        });

        consultaimoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this, ConsultaImoveis.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(Principal.this, message, Toast.LENGTH_SHORT).show();
    }

    private void startAutoScroll() {
        autoScrollHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!userInteracted) {
                    // Se o usuário não interagiu, avance para o próximo item
                    int currentItem = viewPager.getCurrentItem();
                    int nextItem = (currentItem + 1) % imoveisList.size();
                    viewPager.setCurrentItem(nextItem);
                }
                // Continue o auto-scroll
                startAutoScroll();
            }
        }, AUTO_SCROLL_DELAY);
    }
}
