package com.example.cep;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://665506a83c1d3b6029380e17.mockapi.io/";
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";

    private static Retrofit retrofit;
    private static Retrofit viaCepRetrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getViaCepClient() {
        if (viaCepRetrofit == null) {
            viaCepRetrofit = new Retrofit.Builder()
                    .baseUrl(VIA_CEP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return viaCepRetrofit;
    }
}

