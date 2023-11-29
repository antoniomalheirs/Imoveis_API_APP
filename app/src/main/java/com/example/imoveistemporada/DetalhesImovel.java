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
            obterDetalhesImovel(imovelId);
        }

        volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalhesImovel.this, ConsultaImoveis.class);
                startActivity(intent);
            }
        });
    }

    // Inicializa os elementos visuais
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

    // Obtém os detalhes do imóvel a partir do ID
    private void obterDetalhesImovel(String imovelId) {
        imoveisRef.child(imovelId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Imovel imovel = dataSnapshot.getValue(Imovel.class);
                if (imovel != null) {
                    atualizarTextViews(imovel);
                    buscarEExibirDadosClima(imovel.getCidade());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trata erro na leitura do banco de dados
            }
        });
    }

    // Atualiza os elementos visuais com os detalhes do imóvel
    private void atualizarTextViews(Imovel imovel) {
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

    // Obtém e exibe os dados do clima para a cidade do imóvel
    private void buscarEExibirDadosClima(String cidade) {
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

                buscarDadosClima(latitude1, longitude1, cidade);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Busca os dados do clima utilizando a API
    private void buscarDadosClima(double latitude, double longitude, String cidade) {
        AjudanteOpenWeather ajudanteOpenWeather = new AjudanteOpenWeather();
        ajudanteOpenWeather.getClimaDados(this, cidade, new AjudanteOpenWeather.DadosClimaListener() {
            @Override
            public void onSuccess(DadosClima dadosClima) {
                DadosClima.Main main = dadosClima.getMain();
                DadosClima.Weather[] weather = dadosClima.getWeather();

                if (main != null && weather != null && weather.length > 0) {
                    double temperatura = main.getTemp();
                    atualizarTextViewClima("Temperatura: " + Double.toString(temperatura) + " ºC");
                } else {
                    atualizarTextViewClima("Erro");
                }
            }

            @Override
            public void onFailure(String mensagemDeErro) {
                atualizarTextViewClima("Falha");
            }
        });
    }

    // Atualiza o TextView com a descrição do clima
    private void atualizarTextViewClima(String descricao) {
        runOnUiThread(() -> textViewClima.setText("Descrição do clima: " + descricao));
    }
}
