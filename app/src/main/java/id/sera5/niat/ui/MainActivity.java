package id.sera5.niat.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import id.sera5.niat.R;
import id.sera5.niat.data.Constants;
import id.sera5.niat.ui.misc.AboutActivity;
import id.sera5.niat.ui.misc.AudioActivity;
import id.sera5.niat.ui.misc.DasarHukumActivity;
import id.sera5.niat.ui.misc.PengetahuanActivity;
import id.sera5.niat.ui.misc.SupportUsActivity;
import id.sera5.niat.utils.CommonUtils;

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
    @BindView(R.id.cbWudu)
    CheckBox cbWudu;
    @BindView(R.id.cbSunnah)
    CheckBox cbSunnah;
    @BindView(R.id.cbBasmallah)
    CheckBox cbBasmallah;
    @BindView(R.id.cbMohonAllah)
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

        loadAyat();
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

    void loadAyat() {
        String url = String.format(Locale.US, "http://api.alquran.cloud/v1/surah/%d/editions/quran-uthmani,id.indonesian", CommonUtils.getRandomNumberInRange(2,Constants.JUMLAH_SURAT));

        AlertDialog ad = new SpotsDialog.Builder().setMessage("Menyiapkan ayat...").setContext(this).build();

        ad.show();

        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, url, response -> {
            try {
                ad.dismiss();
                JSONObject obj = new JSONObject(response);
                JSONArray hasil = obj.getJSONArray("data");

                JSONObject arabic = hasil.getJSONObject(Constants.ARABIC_SECTION);
                JSONObject indo = hasil.getJSONObject(Constants.INDONESIA_SECTION);

                String namaSurat = arabic.getString("englishName");

                int jumlah_ayat = arabic.getInt("numberOfAyahs") - 1;

                int random = new Random().nextInt(jumlah_ayat);

                String teksArab = arabic.getJSONArray("ayahs").getJSONObject(random).getString("text");
                String teksIndo = indo.getJSONArray("ayahs").getJSONObject(random).getString("text");
                String label = String.format(Locale.US, "Surat %s ayat %d", namaSurat, (random + 1));

                if(random==1) {
                    teksArab = teksArab.replace(Constants.BASMALLAH, "");
                }

                String finalTeksArab = teksArab;

                goAyat.setOnClickListener(view -> {
                    Bundle b = new Bundle();
                    b.putString("label", label);
                    b.putString("arab", finalTeksArab);
                    b.putString("indo", teksIndo);

                    gotoAyat(b);

                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            //aa
            //bb
        }));
    }

    private void gotoAyat(Bundle b) {
        for(CheckBox a: cbList) {
            a.setError(null);
        }

        for(CheckBox a: cbList) {
            if(!a.isChecked()) {
                a.setError("Jangan lupa yang ini, ya.");
                return;
            }
        }

        launchActivity(MainActivity.this, AyatActivity.class, b);
    }
}
