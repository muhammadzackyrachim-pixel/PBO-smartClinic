package model;

public class Poli {
    private int idPoli;
    private String namaPoli;
    private String keterangan;

    public Poli() {}

    public Poli(int idPoli, String namaPoli, String keterangan) {
        this.idPoli = idPoli;
        this.namaPoli = namaPoli;
        this.keterangan = keterangan;
    }

    public int getIdPoli() { return idPoli; }
    public void setIdPoli(int idPoli) { this.idPoli = idPoli; }
    public String getNamaPoli() { return namaPoli; }
    public void setNamaPoli(String namaPoli) { this.namaPoli = namaPoli; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    @Override
    public String toString() { return namaPoli; }
}
