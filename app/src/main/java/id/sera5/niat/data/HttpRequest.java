package id.sera5.niat.data;

import java.util.Locale;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpRequest {
    private static HttpRequest object;
    private OkHttpClient client;
    private String reponse;
    private int number;

    public static synchronized HttpRequest getInstance() {
        if(object == null) object = new HttpRequest();
        return object;
    }

    private HttpRequest() {
        client = new OkHttpClient();
    }

    public void request(String url, Callback cb) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(cb);
    }

    private void getAyat(int surat, int ayat,  Callback cb) {
        String url = String.format(Locale.US, "https://api.banghasan.com/quran/format/json/surat/%d/ayat/%d", surat, ayat);

        request(url, cb);
    }
}
