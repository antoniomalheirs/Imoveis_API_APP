package com.example.imoveistemporada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity
{
    private Button registrar, cancelar;
    private EditText user, pass;
    private Switch termos;
    final Toast[] toast = new Toast[1];
    FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registrar = findViewById(R.id.registro);
        cancelar = findViewById(R.id.cancelareg);

        user = findViewById(R.id.criauser);
        pass = findViewById(R.id.criasenha);

        termos = findViewById(R.id.termo);

        registrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = user.getText().toString();
                String password = pass.getText().toString();

                // Verifique se o Switch está marcado
                boolean concordaTermos = termos.isChecked();

                if (email.isEmpty() || password.isEmpty() || !concordaTermos)
                {
                    // Se algum dos campos estiver vazio ou o Switch não estiver marcado, exiba uma mensagem para o usuário
                    toast[0] = Toast.makeText(Registro.this, "Por favor, preencha todos os campos e concorde com os termos", Toast.LENGTH_SHORT);
                    toast[0].show();
                }
                else
                {
                    // Ambos os campos estão preenchidos e o Switch está marcado, proceda com o registro do usuário
                    usuario.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                toast[0] = Toast.makeText(Registro.this, "Registro concluído com sucesso", Toast.LENGTH_SHORT);
                                toast[0].show();
                            }
                            else
                            {
                                toast[0] = Toast.makeText(Registro.this, "Por favor, insira informações válidas e tente novamente", Toast.LENGTH_SHORT);
                                toast[0].show();
                            }
                        }
                    });
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Crie um Intent para a nova atividade que você deseja abrir
                Intent novaIntent = new Intent(Registro.this, MainActivity.class);

                // Inicie a nova atividade
                startActivity(novaIntent);

                // Feche a atividade atual
                finish();
            }
        });
    }
}