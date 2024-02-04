package com.nayson.aulafirebase;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;


public class HomeActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseHelper.getAuth();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FirebaseHelper.getUser() == null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_home);

        if(FirebaseHelper.getUser() != null ){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName("Nome do usuário").setPhotoUri(Uri.parse("https://static.tvtropes.org/pmwiki/pub/images/881dc5d7_86e3_4258_84d9_89823725ef68.jpeg")).build();
            FirebaseHelper.getUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {

                        TextView t = findViewById(R.id.name_user);
                        ImageView imageView = findViewById(R.id.img_profile);
                        Glide.with(HomeActivity.this).load(profileChangeRequest.getPhotoUri()).into(imageView);
                        t.setText(profileChangeRequest.getDisplayName());
                        imageView.setVisibility(View.VISIBLE);
                        t.setVisibility(View.VISIBLE);
                        findViewById(R.id.progress_circular).setVisibility(View.GONE);
                    }else{
                        Log.i("LogErro", task.getException().getMessage());
                        Toast.makeText(HomeActivity.this, "Deu erro mano :(", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        findViewById(R.id.logout).setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

    }
}