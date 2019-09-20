package id.sera5.niat.data;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AyatGetter {
    private static AyatGetter object;
    private OkHttpClient client;
    private String reponse;
    private int number;

    public static synchronized AyatGetter getInstance() {
        if(object == null) object = new AyatGetter();
        return object;
    }

    private AyatGetter() {
        client = new OkHttpClient();
    }

    private void request(String url, Callback cb) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(cb);
    }

    public void getAyat(int surat, int ayat,  Callback cb) {
        String url = String.format(Locale.US, "https://api.banghasan.com/quran/format/json/surat/%d/ayat/%d", surat, ayat);

        request(url, cb);
    }
}
