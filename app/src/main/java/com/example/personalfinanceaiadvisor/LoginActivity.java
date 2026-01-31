package com.example.personalfinanceaiadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoogleSignIn;
    private AdView adView;

    // For demo: a very simple credential check
    private static final String DEMO_EMAIL = "test@example.com";
    private static final String DEMO_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);
        adView = findViewById(R.id.adView);

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        btnLogin.setOnClickListener(v -> doEmailLogin());

        btnGoogleSignIn.setOnClickListener(v -> {
            // Stub for Google Sign-In.
            // To enable real Google Sign-In, create credentials in Google Cloud Console and add implementation.
            Toast.makeText(LoginActivity.this,
                    "Google Sign-In not configured in this demo. Use: test@example.com / password",
                    Toast.LENGTH_LONG).show();
        });
    }

    private void doEmailLogin() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String pwd = etPassword.getText() != null ? etPassword.getText().toString() : "";

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Enter email");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            etPassword.setError("Enter password");
            return;
        }

        // demo validation
        if (email.equals(DEMO_EMAIL) && pwd.equals(DEMO_PASSWORD)) {
            // success -> navigate
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user_email", email);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials. Use test@example.com / password", Toast.LENGTH_SHORT).show();
        }
    }
}
