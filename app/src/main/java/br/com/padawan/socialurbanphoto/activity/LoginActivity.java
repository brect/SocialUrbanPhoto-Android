package br.com.padawan.socialurbanphoto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.padawan.socialurbanphoto.R;
import br.com.padawan.socialurbanphoto.helper.ConfiguracaoFirebase;
import br.com.padawan.socialurbanphoto.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editLoginEmail;
    private EditText editLoginPassword;
    private Button buttonLogin;
    private TextView textViewSingin;
    private ProgressBar progressBar;

    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificarLoginUsuario();
        configuracaoInicial();

        textViewSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro();
            }
        });


        progressBar.setVisibility(View.GONE);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailUsuario = editLoginEmail.getText().toString();
                String pwUsuario = editLoginPassword.getText().toString();

                if (!emailUsuario.isEmpty()) {
                    if (!pwUsuario.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setEmail(emailUsuario);
                        usuario.setSenha(pwUsuario);
                        validarLogin(usuario);

                    } else {
                        Toast.makeText(LoginActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validarLogin(Usuario usuario) {

        progressBar.setVisibility(View.GONE);
        auth = ConfiguracaoFirebase.getAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Usuario autenticado com sucesso!", Toast.LENGTH_SHORT).show();

                    configuraStartActivity();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao realizar login!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void configuracaoInicial() {
        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPassword = findViewById(R.id.editLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSingin = findViewById(R.id.textViewSingin);
        progressBar = findViewById(R.id.progressBar);

        editLoginEmail.requestFocus();
    }

    public void abrirCadastro() {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private void verificarLoginUsuario() {
        auth = ConfiguracaoFirebase.getAuth();
        if( auth.getCurrentUser() != null ){
            configuraStartActivity();
            finish();
        }
    }

    private void configuraStartActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
