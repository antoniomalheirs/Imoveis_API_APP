package com.example.imoveistemporada;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClimaApiService {
    @GET("onecall")
    Call<DadosClima> getOneCallInfo(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("exclude") String excludePart,
            @Query("appid") String apiKey
    );
}



