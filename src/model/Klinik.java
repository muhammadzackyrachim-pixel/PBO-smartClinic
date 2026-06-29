package model;

public class Klinik {
    private int idKlinik;
    private String namaKlinik;
    private String alamat;
    private String telepon;
    private String email;

    public Klinik() {}

    public Klinik(int idKlinik, String namaKlinik, String alamat, String telepon, String email) {
        this.idKlinik = idKlinik;
        this.namaKlinik = namaKlinik;
        this.alamat = alamat;
        this.telepon = telepon;
        this.email = email;
    }

    public int getIdKlinik() { return idKlinik; }
    public void setIdKlinik(int idKlinik) { this.idKlinik = idKlinik; }
    public String getNamaKlinik() { return namaKlinik; }
    public void setNamaKlinik(String namaKlinik) { this.namaKlinik = namaKlinik; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}