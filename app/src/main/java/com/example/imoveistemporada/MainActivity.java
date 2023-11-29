package com.example.imoveistemporada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private Button loga, registra;
    private EditText user, pass;

    final Toast[] toast = new Toast[1];
    FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loga = findViewById(R.id.login);
        registra = findViewById(R.id.registrar);

        user = findViewById(R.id.user);
        pass = findViewById(R.id.senha);


        registra.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent2 = new Intent(MainActivity.this, Registro.class);
                startActivity(intent2);
            }
        });

        loga.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = user.getText().toString();
                String password = pass.getText().toString();

                if (email.isEmpty() || password.isEmpty())
                {
                    // Se algum dos campos estiver vazio, exiba uma mensagem para o usuário
                    toast[0] = Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT);
                    toast[0].show();
                }
                else
                {
                    // Ambos os campos estão preenchidos, proceda com a autenticação
                    usuario.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                toast[0] = Toast.makeText(MainActivity.this, "Usuário autenticado", Toast.LENGTH_SHORT);
                                toast[0].show();
                                Intent intent = new Intent(MainActivity.this, Principal.class);
                                startActivity(intent);  // Mover isso aqui se a autenticação for bem-sucedida
                            }
                            else
                            {
                                toast[0] = Toast.makeText(MainActivity.this, "Usuário não cadastrado", Toast.LENGTH_SHORT);
                                toast[0].show();
                            }
                        }
                    });
                }
            }
        });
    }
}
