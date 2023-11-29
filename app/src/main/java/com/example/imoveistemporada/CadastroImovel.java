package com.example.imoveistemporada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroImovel extends AppCompatActivity
{
    private EditText editTextIdImovel, editTextNomeProprietario, editTextTelefoneContato, editTextCep, editTextDadosComplementaresEndereco, editTextValorDiaria;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Imoveis");

    private Button btnCadastrar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_imovel);

        // Referenciar os EditTexts do layout
        editTextNomeProprietario = findViewById(R.id.editTextNomeProprietario);
        editTextTelefoneContato = findViewById(R.id.editTextTelefoneContato);
        editTextValorDiaria = findViewById(R.id.editTextValorDiaria);
        editTextCep = findViewById(R.id.editTextCep);
        editTextDadosComplementaresEndereco = findViewById(R.id.editTextDadosComplementaresEndereco);

        // Referenciar o botão
        btnCadastrar = findViewById(R.id.btnCadastrarImovel);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Adicionar evento de clique ao botão
        btnCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                cadastrarImovel();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CadastroImovel.this, Principal.class);
                startActivity(intent);

                finish();
            }
        });
    }

    private void cadastrarImovel()
    {
        if (camposPreenchidos())
        {
            // Criar o objeto Imovel
            Imovel imovel = new Imovel(
                    editTextNomeProprietario.getText().toString(),
                    editTextTelefoneContato.getText().toString(),
                    editTextCep.getText().toString(),
                    editTextDadosComplementaresEndereco.getText().toString(),
                    Double.parseDouble(editTextValorDiaria.getText().toString())
            );

            // Verificar se o objeto Imovel é válido antes de salvar no banco de dados
            if (imovel != null)
            {
                // Salvar no banco de dados
                String key = myRef.push().getKey();
                imovel.setIdImovel(key);

                myRef.child(key).setValue(imovel)
                        .addOnSuccessListener(new OnSuccessListener<Void>()
                        {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                // Sucesso ao salvar no banco de dados
                                Toast.makeText(getApplicationContext(), "Imóvel cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                // Falha ao salvar no banco de dados
                                Toast.makeText(getApplicationContext(), "Erro ao cadastrar imóvel", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                // Exibir mensagem se o objeto Imovel não for válido
                Toast.makeText(getApplicationContext(), "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            // Exibir mensagem se algum campo estiver vazio
            Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    // Verificar se todos os campos estão preenchidos
    private boolean camposPreenchidos()
    {
        return  !editTextNomeProprietario.getText().toString().isEmpty() &&
                !editTextTelefoneContato.getText().toString().isEmpty() &&
                !editTextCep.getText().toString().isEmpty() &&
                !editTextDadosComplementaresEndereco.getText().toString().isEmpty() &&
                !editTextValorDiaria.getText().toString().isEmpty();
    }
}
