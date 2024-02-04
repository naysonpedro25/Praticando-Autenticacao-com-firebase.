package com.nayson.aulafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nayson.aulafirebase.databinding.ActivityRegisterBinding;

import javax.security.auth.login.LoginException;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    FirebaseAuth auth = FirebaseHelper.getAuth();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initClicks();

    }


    public void initClicks(){
        binding.registrar.setOnClickListener(v->{
            validData();
        });

        binding.linkLogin.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        String senhaCorfimar = binding.senhaComfirmar.getText().toString();

        if(!email.isEmpty() && !senha.isEmpty() && !senhaCorfimar.isEmpty() && senhaCorfimar.equals(senha)){
            registerUser(email, senha);
            binding.progressCircular.setVisibility(View.VISIBLE);
        }else{
            Snackbar.make(binding.getRoot(),"Dados invalidos", Snackbar.LENGTH_SHORT).show();
        }
    }
    private void registerUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }else{
                    Snackbar.make(binding.getRoot(),FirebaseHelper.validError(task.getException().getMessage()), Snackbar.LENGTH_LONG).show();
                    binding.progressCircular.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


}