package id.sera5.niat.data;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

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

    private String request(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    public String getAyat(int surat, int ayat) {
        try {
            return request(String.format(Locale.US, "https://api.banghasan.com/quran/format/json/surat/%d/ayat/%d", surat, ayat));
        } catch (IOException e) {
            return "";
        }
    }
}
