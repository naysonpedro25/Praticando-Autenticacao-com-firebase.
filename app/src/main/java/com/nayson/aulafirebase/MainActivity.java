package com.nayson.aulafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nayson.aulafirebase.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


       initClicks();


    }

    public void initClicks(){
        binding.login.setOnClickListener(v->{
            validData();
        });

        binding.linkRegistrar.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });

        binding.linkRecuperar.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), ForgotActivity.class));
            finish();
        });
    }
    public void validData(){
        String email = binding.email.getText().toString();
        String senha = binding.senha.getText().toString();

        if(!email.isEmpty() && !senha.isEmpty()){
            loginUser(email, senha);
            binding.progressCircular.setVisibility(View.VISIBLE);
        }else{
            binding.email.setError("Digite um endereço de e-mail!");
            binding.senha.setError("Digite sua senha");
//            Snackbar.make(binding.getRoot(),"", Snackbar.LENGTH_INDEFINITE).show();
        }
    }
    private void loginUser(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }else{
                    Log.i("TAGFDS", task.getException().getMessage());
                    Snackbar.make(binding.getRoot(),FirebaseHelper.validError(task.getException().getMessage()), Snackbar.LENGTH_INDEFINITE).show();
                    binding.progressCircular.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


}