package id.sera5.niat.ui.activities;

import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sera5.niat.R;

public class StartedActivity extends BaseActivity {

    @BindView(R.id.btnMulai)
    Button btnMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);
        ButterKnife.bind(this);

        btnMulai.setOnClickListener(view -> launchActivity(StartedActivity.this, MainActivity.class));
    }
}
