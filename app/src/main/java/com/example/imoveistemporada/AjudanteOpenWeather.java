package com.example.imoveistemporada;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AjudanteOpenWeather {

    private static final String BASE_URL = "https://api.openweathermap.org/data/3.0/";
    private static final String API_KEY = "2f37b15834260fcd56695155ec5156e2";

    private final ClimaApiService weatherService;

    public AjudanteOpenWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherService = retrofit.create(ClimaApiService.class);
    }

    public DadosClima getClimaDados(String latitude, String longitude) {
        String excludePart = "current,minutely,hourly";

        try {
            Call<DadosClima> call = weatherService.getOneCallInfo(latitude, longitude, excludePart, API_KEY);
            Response<DadosClima> response = call.execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Handle erro na resposta, por exemplo, log ou lançar uma exceção
                return null;
            }
        } catch (Exception e) {
            // Handle exceção durante a chamada, por exemplo, log ou lançar uma exceção
            return null;
        }
    }
}
