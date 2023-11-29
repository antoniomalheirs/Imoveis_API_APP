package com.example.imoveistemporada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroImovel extends AppCompatActivity {
    private EditText editTextNomeProprietario, editTextTelefoneContato, editTextCep, editTextCidade, editTextValorDiaria;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Imoveis");
    private Button btnCadastrar, btnCancelar;
    private Usuario usuario;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser usuarioAtual = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_imovel);

        usuario = new Usuario();
        usuario.setUid(usuarioAtual.getUid().toString());
        usuario.setEmail(usuarioAtual.getEmail().toString());

        // Referenciar os EditTexts do layout
        editTextNomeProprietario = findViewById(R.id.editTextNomeProprietario);
        editTextTelefoneContato = findViewById(R.id.editTextTelefoneContato);
        editTextValorDiaria = findViewById(R.id.editTextValorDiaria);
        editTextCep = findViewById(R.id.editTextCep);
        editTextCidade = findViewById(R.id.editTextCidade);

        // Referenciar os botões
        btnCadastrar = findViewById(R.id.btnCadastrarImovel);
        btnCancelar = findViewById(R.id.btnCancelar);

        editTextCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // Quando o campo CEP perde o foco, realizar a consulta
                    consultarCep(editTextCep.getText().toString());
                }
            }
        });

        // Adicionar evento de clique ao botão Cadastrar
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarImovel();
            }
        });

        // Adicionar evento de clique ao botão Cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroImovel.this, Principal.class);
                startActivity(intent);
            }
        });
    }

    // Método para consultar informações do CEP
    private void consultarCep(String cep) {
        // Verificar se o CEP tem 8 dígitos
        if (cep.length() == 8) {
            // Configurar a interface Retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://viacep.com.br/ws/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Criar uma instância da interface de serviço
            CepApiService cepApiService = retrofit.create(CepApiService.class);

            // Fazer a chamada à API
            Call<CepResponse> call = cepApiService.getCepInfo(cep);
            call.enqueue(new Callback<CepResponse>() {
                @Override
                public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Preencher o campo de cidade com a resposta da API
                        CepResponse cepResponse = response.body();
                        editTextCidade.setText(cepResponse.getCidade());
                    }
                }

                @Override
                public void onFailure(Call<CepResponse> call, Throwable t) {
                    editTextCidade.setText("Digite um CEP válido");
                }
            });
        } else {
            editTextCidade.setText("Digite um CEP válido");
        }
    }

    // Método para cadastrar um novo imóvel
    private void cadastrarImovel() {
        if (camposPreenchidos()) {
            // Criar o objeto Imovel
            Imovel imovel = new Imovel(
                    editTextNomeProprietario.getText().toString(),
                    editTextTelefoneContato.getText().toString(),
                    editTextCep.getText().toString(),
                    editTextCidade.getText().toString(),
                    Double.parseDouble(editTextValorDiaria.getText().toString())
            );

            imovel.setProprietario(usuario);

            // Verificar se o objeto Imovel é válido antes de salvar no banco de dados
            if (imovel != null) {
                // Salvar no banco de dados
                String key = myRef.push().getKey();
                imovel.setIdImovel(key);

                myRef.child(key).setValue(imovel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Sucesso ao salvar no banco de dados
                                Toast.makeText(getApplicationContext(), "Imóvel cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Falha ao salvar no banco de dados
                                Toast.makeText(getApplicationContext(), "Erro ao cadastrar imóvel", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Exibir mensagem se o objeto Imovel não for válido
                Toast.makeText(getApplicationContext(), "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Exibir mensagem se algum campo estiver vazio
            Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para verificar se todos os campos estão preenchidos
    private boolean camposPreenchidos() {
        String cidadeText = editTextCidade.getText().toString().trim();

        return !editTextNomeProprietario.getText().toString().isEmpty() &&
                !editTextTelefoneContato.getText().toString().isEmpty() &&
                !editTextCep.getText().toString().isEmpty() &&
                !cidadeText.isEmpty() &&
                !cidadeText.equals("Digite um CEP válido") &&
                !editTextValorDiaria.getText().toString().isEmpty();
    }
}
