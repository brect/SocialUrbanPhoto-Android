package br.com.padawan.socialurbanphoto.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.padawan.socialurbanphoto.R;
import br.com.padawan.socialurbanphoto.helper.ConfiguracaoFirebase;
import br.com.padawan.socialurbanphoto.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText editCadastroNome;
    private EditText editCadastroEmail;
    private EditText editCadastroPassword;
    private Button buttonCadastrar;
    private ProgressBar progressBar;

    private Usuario usuario;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        configuracaoInicial();

        progressBar.setVisibility(View.GONE);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarcadastroUsuario(v);
            }
        });
    }

    public void validarcadastroUsuario(View view){
        String nome = editCadastroNome.getText().toString();
        String email = editCadastroEmail.getText().toString();
        String senha = editCadastroPassword.getText().toString();

        if (!nome.isEmpty()){
            if (!email.isEmpty()){
                if (!senha.isEmpty()){

                    buttonCadastrar.setText("");
                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    cadastrar(usuario);

                }else{
                    Toast.makeText(CadastroActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(CadastroActivity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CadastroActivity.this, "Preencha o nome!", Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrar(Usuario usuario) {
        progressBar.setVisibility(View.VISIBLE);
        auth = ConfiguracaoFirebase.getAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                            configuraStartActivity();
                            finish();
                        } else {
                            progressBar.setVisibility(View.GONE);

                            String excecao = "";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                excecao = "Digite um senha mais forte!";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "Por favor, digite um e-mail válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                excecao = "Esta conta já está cadastrada";
                            }catch (Exception e){
                                excecao = "Erro ao cadastrar usuario " + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("onFailure", "onFailure: " + e);
            }
        });
    }

    private void configuraStartActivity() {
        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void configuracaoInicial() {
        editCadastroNome = findViewById(R.id.editCadastroNome);
        editCadastroEmail = findViewById(R.id.editCadastroEmail);
        editCadastroPassword = findViewById(R.id.editCadastroPassword);
        buttonCadastrar = findViewById(R.id.btnCadastrar);
        progressBar = findViewById(R.id.progressBar);

        editCadastroNome.requestFocus();
    }
}
