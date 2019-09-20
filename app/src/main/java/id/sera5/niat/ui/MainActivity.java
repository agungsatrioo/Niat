package id.sera5.niat.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import id.sera5.niat.R;
import id.sera5.niat.data.Constants;

public class MainActivity extends AppCompatActivity
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void loadAyat() {
        String url = String.format(Locale.US, "http://api.alquran.cloud/v1/surah/%d/editions/quran-uthmani,id.indonesian", new Random().nextInt(Constants.JUMLAH_SURAT - 1));

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
                String label = String.format(Locale.US, "Surat %s ayat %d", namaSurat, random);

                goAyat.setOnClickListener(view -> {
                    Intent i = new Intent(MainActivity.this, AyatActivity.class);
                    i.putExtra("label",label);
                    i.putExtra("arab",teksArab);
                    i.putExtra("indo",teksIndo);

                    startActivity(i);
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            //aa
            //bb
        }));
    }
}
