package id.sera5.niat.ui.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import id.sera5.niat.data.DatabaseHelper;
import id.sera5.niat.utils.CommonUtils;

public class AyatActivity extends BaseActivity {

    @BindView(R.id.hurufArab)
    TextView hurufArab;
    @BindView(R.id.hurufIndo)
    TextView hurufIndo;

    int suratRandom, ayat = 0;
    @BindView(R.id.btn_save_ayat)
    Button btnSaveAyat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayat);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            suratRandom = b.getInt("surat");
            ayat = b.getInt("ayat");

            findAyat(suratRandom, ayat);
        } else {
            suratRandom = CommonUtils.getRandomNumberInRange(2, Constants.JUMLAH_SURAT);
            randomAyat(suratRandom);
        }

        setBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_surat:
                Bundle b = new Bundle();
                b.putInt("surat", suratRandom);

                launchActivity(AyatActivity.this, SuratActivity.class, b);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setView(String label, String arab, String indo) {
        setTitle(label);
        hurufArab.setText(arab);
        hurufIndo.setText(indo);
    }

    private void findAyat(int surat, int ayat) {
        String url = String.format(Locale.US, "http://api.alquran.cloud/v1/surah/%d/editions/quran-uthmani,id.indonesian", surat);

        AlertDialog ad = new SpotsDialog.Builder().setMessage("Menyiapkan ayat...").setContext(this).build();

        ad.show();

        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, url, response -> {
            try {
                int random = ayat - 1;

                ad.dismiss();
                JSONObject obj = new JSONObject(response);
                JSONArray hasil = obj.getJSONArray("data");

                JSONObject arabic = hasil.getJSONObject(Constants.ARABIC_SECTION);
                JSONObject indo = hasil.getJSONObject(Constants.INDONESIA_SECTION);

                String namaSurat = arabic.getString("englishName");

                int jumlah_ayat = arabic.getInt("numberOfAyahs") - 1;

                if (random < 0) random = new Random().nextInt(jumlah_ayat);

                String teksArab = arabic.getJSONArray("ayahs").getJSONObject(random).getString("text").replace(Constants.BASMALLAH, "");

                String teksIndo = indo.getJSONArray("ayahs").getJSONObject(random).getString("text");
                String label = String.format(Locale.US, "%s (%d): %d", namaSurat, surat, (random + 1));

                int finalRandom = random + 1;
                btnSaveAyat.setOnClickListener(view -> {
                    SQLiteDatabase mdb = getWritableDatabase();

                    ContentValues item = new ContentValues();

                    item.put(DatabaseHelper.COL_SURAT, surat);
                    item.put(DatabaseHelper.COL_AYAT, finalRandom);

                    mdb.insert(DatabaseHelper.TABLE_TALL, null, item);
                    mdb.close();

                    Toast.makeText(AyatActivity.this, "Simpan ayat berhasil.", Toast.LENGTH_LONG).show();

                    printDB();
                });

                setView(label, teksArab, teksIndo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            //aa
            //bb
        }));
    }

    private void randomAyat(int surat) {
        findAyat(surat, -1);
    }
}
