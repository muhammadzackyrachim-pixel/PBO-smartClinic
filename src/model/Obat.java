package model;

public class Obat {
    private int idObat;
    private String namaObat;
    private String jenis;
    private String dosis;
    private int stok;
    private double harga;
    private String keterangan;

    public Obat() {}

    public Obat(int idObat, String namaObat, String jenis, String dosis, int stok, double harga, String keterangan) {
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.jenis = jenis;
        this.dosis = dosis;
        this.stok = stok;
        this.harga = harga;
        this.keterangan = keterangan;
    }

    public int getIdObat() { return idObat; }
    public void setIdObat(int idObat) { this.idObat = idObat; }
    public String getNamaObat() { return namaObat; }
    public void setNamaObat(String namaObat) { this.namaObat = namaObat; }
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    @Override
    public String toString() {
        return namaObat + " (" + dosis + ")";
    }
}