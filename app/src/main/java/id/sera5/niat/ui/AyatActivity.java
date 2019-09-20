package id.sera5.niat.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sera5.niat.R;

public class AyatActivity extends AppCompatActivity {

    @BindView(R.id.hurufArab)
    TextView hurufArab;
    @BindView(R.id.hurufIndo)
    TextView hurufIndo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayat);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();

        if(b==null) finish();

        String label = b.getString("label");
        String arab = b.getString("arab");
        String indo = b.getString("indo");

        setTitle(label);
        hurufArab.setText(arab);
        hurufIndo.setText(indo);

        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else if(getActionBar()!=null) getActionBar().setDisplayHomeAsUpEnabled(true);
}
}
