package model;

public class ResepObat {
    private int idResep;
    private RekamMedis rekamMedis;
    private Obat obat;
    private int jumlah;
    private String aturanPakai;

    public ResepObat() {}

    public ResepObat(int idResep, RekamMedis rekamMedis, Obat obat, int jumlah, String aturanPakai) {
        this.idResep = idResep;
        this.rekamMedis = rekamMedis;
        this.obat = obat;
        this.jumlah = jumlah;
        this.aturanPakai = aturanPakai;
    }

    public int getIdResep() { return idResep; }
    public void setIdResep(int idResep) { this.idResep = idResep; }
    public RekamMedis getRekamMedis() { return rekamMedis; }
    public void setRekamMedis(RekamMedis rekamMedis) { this.rekamMedis = rekamMedis; }
    public Obat getObat() { return obat; }
    public void setObat(Obat obat) { this.obat = obat; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public String getAturanPakai() { return aturanPakai; }
    public void setAturanPakai(String aturanPakai) { this.aturanPakai = aturanPakai; }
}