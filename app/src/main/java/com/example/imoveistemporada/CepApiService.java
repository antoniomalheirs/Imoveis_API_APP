package com.example.imoveistemporada;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepApiService {
    @GET("{cep}/json/")
    Call<CepResponse> getCepInfo(@Path("cep") String cep);
}
