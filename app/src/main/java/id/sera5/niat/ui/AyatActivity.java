package id.sera5.niat.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import id.sera5.niat.utils.CommonUtils;

public class AyatActivity extends BaseActivity {

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
        if(b != null) {
            String label = b.getString("label");
            String arab = b.getString("arab");
            String indo = b.getString("indo");

            setView(label, arab, indo);
        } else randomAyat();

        setBackButton();
    }

    private void setView(String label, String arab, String indo) {
        setTitle(label);
        hurufArab.setText(arab);
        hurufIndo.setText(indo);
    }

    private void randomAyat() {
        String url = String.format(Locale.US, "http://api.alquran.cloud/v1/surah/%d/editions/quran-uthmani,id.indonesian", CommonUtils.getRandomNumberInRange(2, Constants.JUMLAH_SURAT));

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

                setView(label, teksArab, teksIndo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            //aa
            //bb
        }));
    }
}
