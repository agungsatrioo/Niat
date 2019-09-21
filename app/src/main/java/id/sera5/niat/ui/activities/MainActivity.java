package id.sera5.niat.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sera5.niat.R;
import id.sera5.niat.ui.activities.misc.AboutActivity;
import id.sera5.niat.ui.activities.misc.AudioActivity;
import id.sera5.niat.ui.activities.misc.DasarHukumActivity;
import id.sera5.niat.ui.activities.misc.PengetahuanActivity;
import id.sera5.niat.ui.activities.misc.SupportUsActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.go_ayat)
    Button goAyat;
    @BindView(R.id.cbwudu)
    CheckBox cbWudu;
    @BindView(R.id.cbsunnah)
    CheckBox cbSunnah;
    @BindView(R.id.cbbasmallah)
    CheckBox cbBasmallah;
    @BindView(R.id.cbmohonAllah)
    CheckBox cbMohonAllah;

    List<CheckBox> cbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        cbList = new ArrayList<>();

        cbList.add(cbWudu);
        cbList.add(cbSunnah);
        cbList.add(cbBasmallah);
        cbList.add(cbMohonAllah);

        navView.setNavigationItemSelectedListener(this);

        goAyat.setOnClickListener(view -> gotoAyat());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_pengetahuan:
                launchActivity(MainActivity.this, PengetahuanActivity.class);
                break;
            case R.id.nav_dasarhukum:
                launchActivity(MainActivity.this, DasarHukumActivity.class);
                break;
            case R.id.nav_audio:
                launchActivity(MainActivity.this, AudioActivity.class);
                break;
            case R.id.nav_support:
                launchActivity(MainActivity.this, SupportUsActivity.class);
                break;
            case R.id.nav_about:
                launchActivity(MainActivity.this, AboutActivity.class);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void gotoAyat() {
        for (CheckBox a : cbList) {
            a.setError(null);
        }

        for (CheckBox a : cbList) {
            if (!a.isChecked()) {
                a.setError("Jangan lupa yang ini, ya.");
                return;
            }
        }

        launchActivity(MainActivity.this, AyatActivity.class);
    }
}
