package id.sera5.niat.models;

public class Ayat {
    private String number;
    private String arab;
    private String indo;

    public Ayat(String number, String arab, String indo) {
        this.number = number;
        this.arab = arab;
        this.indo = indo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArab() {
        return arab;
    }

    public void setArab(String arab) {
        this.arab = arab;
    }

    public String getIndo() {
        return indo;
    }

    public void setIndo(String indo) {
        this.indo = indo;
    }
}
