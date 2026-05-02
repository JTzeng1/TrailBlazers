package com.example.trailblazers;

import static android.view.Gravity.apply;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trailblazers.database.TrailDatabase;
import com.example.trailblazers.database.entities.User;
import com.example.trailblazers.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TrailDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = TrailDatabase.getDatabase(this);

        binding.loginButton.setOnClickListener(v -> verifyUser());
    }

    private void verifyUser() {
        String username = binding.usernameEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if (username.isEmpty()) {
            toastMaker("Username cannot be blank.");
            return;
        }

        if (password.isEmpty()) {
            toastMaker("Password cannot be blank.");
            return;
        }

        User user = database.userDao().login(username, password);

        if (user != null) {
            SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putInt("loggedInUser", user.getUserID());
            editor.apply();

            startActivity(new Intent(this, LandingPageActivity.class));
            finish();
        } else {
            toastMaker("Invalid username or password.");
        }
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}