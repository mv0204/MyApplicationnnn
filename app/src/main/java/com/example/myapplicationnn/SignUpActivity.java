package com.example.myapplicationnn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import com.example.myapplicationnn.databinding.ActivitySignUpBinding;
import com.example.myapplicationnn.models.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    // Write a message to the database
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setTitle("Signing UP");
        dialog.setMessage("creating account");
        binding.buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                mAuth.createUserWithEmailAndPassword(binding.ETEmailAddress.getText().toString(), binding.ETPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                dialog.dismiss();
                                if (task.isSuccessful()) {
                                    users users=new users(binding.ETName.getText().toString(),binding.ETEmailAddress.getText().toString(),
                                            binding.ETPassword.getText().toString());
                                    String id=task.getResult().getUser().getUid();
                                    database.getReference().child("users").child(id).setValue(users);
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
//                                manisha@gmail.com
//                                manisha123
                            }
                        });
            }
        });
        binding.textViewAlreadyAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });

    }
}