package id.sera5.niat.models;

public class History {
    public String surat, ayat, timestamp;

    public History(String surat, String ayat, String timestamp) {
        this.surat = surat;
        this.ayat = ayat;
        this.timestamp = timestamp;
    }

    public String getSurat() {
        return surat;
    }

    public void setSurat(String surat) {
        this.surat = surat;
    }

    public String getAyat() {
        return ayat;
    }

    public void setAyat(String ayat) {
        this.ayat = ayat;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
