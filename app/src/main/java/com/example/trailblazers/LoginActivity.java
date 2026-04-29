package com.example.trailblazers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.trailblazers.database.TrailRepository;
import com.example.trailblazers.database.entities.User;
import com.example.trailblazers.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private TrailRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TrailRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(v -> {
            verifyUser();
        });

    }


    private void verifyUser() {
        String username = binding.usernameEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username cannot be blank.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {

            if (user != null) {
                if (password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                    finish();

                } else {
                    toastMaker("Invalid password.");

                }

            } else {
                toastMaker("Username not found.");
            }

        });

    }


    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }

}