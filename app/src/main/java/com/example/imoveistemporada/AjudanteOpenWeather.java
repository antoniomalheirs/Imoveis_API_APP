package com.example.imoveistemporada;

import android.content.Context;

import com.google.gson.Gson;

import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AjudanteOpenWeather {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "2f37b15834260fcd56695155ec5156e2";
    private final AsyncHttpClient cliente = new AsyncHttpClient();

    public interface DadosClimaListener {
        void onSuccess(DadosClima dadosClima);
        void onFailure(String mensagemDeErro);
    }

    public void getClimaDados(Context contexto, String localizacao, DadosClimaListener listener) {
        RequestParams parametros = new RequestParams();
        parametros.put("q", localizacao);
        parametros.put("appid", API_KEY);
        parametros.put("units", "metric");

        cliente.get(contexto, BASE_URL, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resposta = new String(responseBody);
                    Gson gson = new Gson();
                    DadosClima dadosClima = gson.fromJson(resposta, DadosClima.class);
                    listener.onSuccess(dadosClima);
                } catch (Exception e) {
                    listener.onFailure("Erro ao processar os dados do clima.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                listener.onFailure("Erro na requisição: " + error.getMessage());
            }
        });
    }
}
