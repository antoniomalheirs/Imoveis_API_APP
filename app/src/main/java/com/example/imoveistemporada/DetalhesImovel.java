package com.example.imoveistemporada;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesImovel extends AppCompatActivity {

    private DatabaseReference imoveisRef = FirebaseDatabase.getInstance().getReference("Imoveis");
    private AjudanteOpenWeather openWeatherHelper = new AjudanteOpenWeather();

    private TextView textViewTituloDetalhes;
    private TextView textViewIdImovel;
    private TextView textViewNomeProprietario;
    private TextView textViewContato;
    private TextView textViewValorDiaria;
    private TextView textViewCep;
    private TextView textViewCidade;
    private TextView textViewPropemail;
    private TextView textViewUid;
    private TextView textViewClima;

    private Button volta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_imovel);

        initializeViews();

        Intent intent = getIntent();
        if (intent != null) {
            String imovelId = intent.getStringExtra("imovel_id");
            fetchImovelDetails(imovelId);
        }

        volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesImovel.this, ConsultaImoveis.class);
                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        textViewTituloDetalhes = findViewById(R.id.textViewTituloDetalhes);
        textViewIdImovel = findViewById(R.id.textViewIdImovel);
        textViewNomeProprietario = findViewById(R.id.textViewNomeProprietario);
        textViewContato = findViewById(R.id.textViewContato);
        textViewValorDiaria = findViewById(R.id.textViewValorDiaria);
        textViewCep = findViewById(R.id.textViewCep);
        textViewCidade = findViewById(R.id.textViewCidade);
        textViewPropemail = findViewById(R.id.textViewPropemail);
        textViewUid = findViewById(R.id.textViewUid);
        textViewClima = findViewById(R.id.textViewClima);
        volta = findViewById(R.id.button);
    }

    private void fetchImovelDetails(String imovelId) {
        imoveisRef.child(imovelId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Imovel imovel = dataSnapshot.getValue(Imovel.class);
                if (imovel != null) {
                    updateTextViews(imovel);
                    fetchAndDisplayWeatherData(imovel.getCidade());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database read error
            }
        });
    }

    private void updateTextViews(Imovel imovel) {
        textViewTituloDetalhes.setText("Detalhes do Imóvel");
        textViewIdImovel.setText("ID do Imóvel: " + imovel.getIdImovel());
        textViewNomeProprietario.setText("Nome do Proprietário: " + imovel.getNomeProprietario());
        textViewContato.setText("Contato: " + imovel.getTelefoneContato());
        textViewValorDiaria.setText("Valor Diária: R$ " + imovel.getValorDiaria());
        textViewCep.setText("CEP: " + imovel.getCep());
        textViewCidade.setText("Cidade: " + imovel.getCidade());
        textViewPropemail.setText("E-mail do Proprietário: " + imovel.getProprietario().getEmail());
        textViewUid.setText("UID do Proprietário: " + imovel.getProprietario().getUid());
    }

    private void fetchAndDisplayWeatherData(String cidade) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(cidade, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();

                DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

                String formattedLatitude = decimalFormat.format(latitude);
                String formattedLongitude = decimalFormat.format(longitude);

                double latitude1 = Double.parseDouble(formattedLatitude);
                double longitude1 = Double.parseDouble(formattedLongitude);

                fetchWeatherData(latitude1, longitude1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchWeatherData(double latitude, double longitude) {
        DadosClima weatherData = openWeatherHelper.getClimaDados(Double.toString(latitude), Double.toString(longitude));
        if (weatherData != null) {
            updateWeatherTextView(weatherData.getDescription());
        } else {
            updateWeatherTextView("Erro ao carregar API");
        }
    }

    private void updateWeatherTextView(String descricao) {
        runOnUiThread(() -> textViewClima.setText("Descrição do clima: " + descricao));
    }
}
