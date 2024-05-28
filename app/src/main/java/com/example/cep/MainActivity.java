package com.example.cep;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRA, editTextNome, editTextCEP, editTextLogradouro, editTextComplemento, editTextBairro, editTextCidade, editTextUF;
    private Button buttonSave;

    private ApiService apiService;
    private ViaCepService viaCepService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRA = findViewById(R.id.editTextRA);
        editTextNome = findViewById(R.id.editTextNome);
        editTextCEP = findViewById(R.id.editTextCEP);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextUF = findViewById(R.id.editTextUF);
        buttonSave = findViewById(R.id.buttonSave);

        apiService = ApiClient.getClient().create(ApiService.class);
        viaCepService = ApiClient.getViaCepClient().create(ViaCepService.class);

        editTextCEP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 8) {
                    buscarCep(editable.toString());
                }
            }
        });

        buttonSave.setOnClickListener(view -> {
            String ra = editTextRA.getText().toString();
            String nome = editTextNome.getText().toString();
            String cep = editTextCEP.getText().toString();
            String logradouro = editTextLogradouro.getText().toString();
            String complemento = editTextComplemento.getText().toString();
            String bairro = editTextBairro.getText().toString();
            String cidade = editTextCidade.getText().toString();
            String uf = editTextUF.getText().toString();

            if (!ra.isEmpty() && !nome.isEmpty() && !cep.isEmpty() && !logradouro.isEmpty() && !bairro.isEmpty() && !cidade.isEmpty() && !uf.isEmpty()) {
                Aluno aluno = new Aluno(Integer.parseInt(ra), nome, cep, logradouro, complemento, bairro, cidade, uf);
                salvarAluno(aluno);
            } else {
                Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        Button buttonList = findViewById(R.id.buttonList);
        buttonList.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
        });
    }

    private void buscarCep(String cep) {
        Call<Endereco> call = viaCepService.getEndereco(cep);
        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Endereco endereco = response.body();
                    editTextLogradouro.setText(endereco.getLogradouro());
                    editTextComplemento.setText(endereco.getComplemento());
                    editTextBairro.setText(endereco.getBairro());
                    editTextCidade.setText(endereco.getLocalidade());
                    editTextUF.setText(endereco.getUf());
                } else {
                    Toast.makeText(MainActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarAluno(Aluno aluno) {
        Call<Aluno> call = apiService.createAluno(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limparCampos() {
        editTextRA.setText("");
        editTextNome.setText("");
        editTextCEP.setText("");
        editTextLogradouro.setText("");
        editTextComplemento.setText("");
        editTextBairro.setText("");
        editTextCidade.setText("");
        editTextUF.setText("");
    }
}