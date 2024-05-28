package com.example.cep;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    @POST("/alunos")
    Call<Aluno> createAluno(@Body Aluno aluno);

    @GET("/alunos")
    Call<List<Aluno>> getAlunos();
}

