package com.example.cep;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlunos;
    private AlunoAdapter alunoAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);
        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchAlunos();
    }

    private void fetchAlunos() {
        Call<List<Aluno>> call = apiService.getAlunos();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Aluno> alunos = response.body();
                    alunoAdapter = new AlunoAdapter(alunos);
                    recyclerViewAlunos.setAdapter(alunoAdapter);
                } else {
                    Toast.makeText(ListActivity.this, "Erro ao buscar alunos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Erro ao buscar alunos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
