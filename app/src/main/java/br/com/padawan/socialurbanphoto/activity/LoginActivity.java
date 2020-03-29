package br.com.padawan.socialurbanphoto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.padawan.socialurbanphoto.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewSingin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configuracaoInicial();

        textViewSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro();
            }
        });
    }

    private void configuracaoInicial() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSingin = findViewById(R.id.textViewSingin);
        progressBar = findViewById(R.id.progressBar);
    }

    public void abrirCadastro() {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}
