package id.sera5.niat.ui.activities.misc;

import android.os.Bundle;

import id.sera5.niat.R;
import id.sera5.niat.ui.activities.BaseActivity;

public class PengetahuanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengetahuan);

        setTitle("Pengetahuan");

        setBackButton();
    }
}
