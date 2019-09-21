package id.sera5.niat.ui.activities;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import id.sera5.niat.R;
import id.sera5.niat.data.Constants;
import id.sera5.niat.models.Ayat;
import id.sera5.niat.ui.adapters.AyatAdapter;

public class SuratActivity extends BaseActivity {

    @BindView(R.id.rv_surat)
    RecyclerView rvSurat;

    ArrayList<Ayat> listAyat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surat);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();

        listAyat = new ArrayList<>();

        if (b != null) {
            int surat = b.getInt("surat", 0);

            showSurah(surat);
        }

        setBackButton();
    }

    private void showSurah(int surah) {
        String url = String.format(Locale.US, "http://api.alquran.cloud/v1/surah/%d/editions/quran-uthmani,id.indonesian", surah);

        AlertDialog ad = new SpotsDialog.Builder().setMessage("Menyiapkan surah...").setContext(this).build();

        ad.show();

        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, url, response -> {
            try {
                ad.dismiss();
                JSONObject obj = new JSONObject(response);
                JSONArray hasil = obj.getJSONArray("data");

                JSONObject arabic = hasil.getJSONObject(Constants.ARABIC_SECTION);
                JSONObject indo = hasil.getJSONObject(Constants.INDONESIA_SECTION);

                String namaSurat = arabic.getString("englishName");

                setTitle(String.format(Locale.US, "%s (%d)", namaSurat, surah));

                int jumlah_ayat = arabic.getInt("numberOfAyahs");

                for (int i = 0; i < jumlah_ayat; i++) {
                    String strArab = arabic.getJSONArray("ayahs").getJSONObject(i).getString("text").replace(Constants.BASMALLAH, "");
                    String strIndo = indo.getJSONArray("ayahs").getJSONObject(i).getString("text");

                    listAyat.add(new Ayat(String.valueOf(i + 1), strArab, strIndo));
                }

                rvSurat.setLayoutManager(new LinearLayoutManager(this));
                AyatAdapter listHeroAdapter = new AyatAdapter(listAyat);
                rvSurat.setAdapter(listHeroAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            //aa
            //bb
        }));
    }
}
