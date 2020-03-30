package br.com.padawan.socialurbanphoto.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import br.com.padawan.socialurbanphoto.R;
import br.com.padawan.socialurbanphoto.fragment.FeedFragment;
import br.com.padawan.socialurbanphoto.fragment.PerfilFragment;
import br.com.padawan.socialurbanphoto.fragment.PesquisaFragment;
import br.com.padawan.socialurbanphoto.fragment.PostagemFragment;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("urban photo");
        setSupportActionBar( toolbar );

        configuraBottomNavigation();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ){
            case R.id.menu_sair :
                deslogarUsuario();
                configuraLogoutActivity();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario() {
        try {
            auth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void configuraLogoutActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void configuraBottomNavigation(){
        BottomNavigationViewEx bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setTextVisibility(true);
        bottomNavigation.enableItemShiftingMode(true);

        habilitaNavegacao(bottomNavigation);
    }

    private void habilitaNavegacao(BottomNavigationViewEx navigation){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home :
                        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();
                        return true;
                    case R.id.ic_search :
                        fragmentTransaction.replace(R.id.viewPager, new PesquisaFragment()).commit();
                        return true;
                    case R.id.ic_add :
                        fragmentTransaction.replace(R.id.viewPager, new PostagemFragment()).commit();
                        return true;
                    case R.id.ic_person :
                        fragmentTransaction.replace(R.id.viewPager, new PerfilFragment()).commit();
                        return true;
                }
                return false;
            }
        });

    }
}
