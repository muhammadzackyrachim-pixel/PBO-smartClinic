package model;

public class Dokter {
    private int idDokter;
    private String nama;
    private String spesialisasi;
    private String noHP;
    private String alamat;
    private String email;

    public Dokter() {}

    public Dokter(int idDokter, String nama, String spesialisasi, String noHP, String alamat, String email) {
        this.idDokter = idDokter;
        this.nama = nama;
        this.spesialisasi = spesialisasi;
        this.noHP = noHP;
        this.alamat = alamat;
        this.email = email;
    }

    public int getIdDokter() { return idDokter; }
    public void setIdDokter(int idDokter) { this.idDokter = idDokter; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getSpesialisasi() { return spesialisasi; }
    public void setSpesialisasi(String spesialisasi) { this.spesialisasi = spesialisasi; }
    public String getNoHP() { return noHP; }
    public void setNoHP(String noHP) { this.noHP = noHP; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return nama + " (" + spesialisasi + ")";
    }
}