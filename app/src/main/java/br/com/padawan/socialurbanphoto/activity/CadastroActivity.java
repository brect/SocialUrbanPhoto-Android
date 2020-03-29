package br.com.padawan.socialurbanphoto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import br.com.padawan.socialurbanphoto.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonCadastrar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        configuracaoInicial();
    }

    private void configuracaoInicial() {
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        progressBar = findViewById(R.id.progressBar);
    }
}
