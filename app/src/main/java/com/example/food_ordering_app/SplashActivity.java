package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_LENGTH = 5000; // 5 seconds
    private ProgressBar progressBar;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Update the ProgressBar over the SPLASH_DISPLAY_LENGTH
        new Thread(() -> {
            for (int progress = 0; progress <= 100; progress++) {
                int finalProgress = progress;
                handler.post(() -> progressBar.setProgress(finalProgress));
                try {
                    Thread.sleep(SPLASH_DISPLAY_LENGTH / 100); // 5 seconds divided by 100 steps
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // After completing the progress, start the HomeActivity
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Finish the Splash activity so the user can't return to it
        }).start();
    }
}
