package com.nayson.aulafirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.nayson.aulafirebase.databinding.ActivityForgotBinding;

public class ForgotActivity extends AppCompatActivity {
    private ActivityForgotBinding binding;
    private FirebaseAuth auth = FirebaseHelper.getAuth();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initClicks();
    }

    public void initClicks(){
        binding.enviarEmail.setOnClickListener(v->{
            validData();
        });

        binding.linkRegistrar.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });

        binding.linkLogin.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), ForgotActivity.class));
            finish();
        });
    }
    public void validData(){
        String email = binding.email.getText().toString();

        if(!email.isEmpty()){
            sendEmail(email);
            binding.progressCircular.setVisibility(View.VISIBLE);
        }else{
            Snackbar.make(binding.getRoot(),"Dados invalidos", Snackbar.LENGTH_SHORT).show();
        }


    }

    private void sendEmail(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ForgotActivity.this, "Verifique sua caixa de e-mail!", Toast.LENGTH_SHORT).show();

                }else{
                    Snackbar.make(binding.getRoot(),FirebaseHelper.validError(task.getException().getMessage()), Snackbar.LENGTH_LONG).show();
                    binding.progressCircular.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
}