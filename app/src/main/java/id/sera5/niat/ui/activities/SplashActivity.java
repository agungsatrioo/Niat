package id.sera5.niat.ui.activities;

import android.os.Bundle;
import android.os.Handler;

import id.sera5.niat.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            launchActivity(SplashActivity.this, StartedActivity.class);
            finish();
        }, 4000);
    }
}
