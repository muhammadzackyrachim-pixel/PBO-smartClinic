package model;

public class Pemeriksaan {
    private int idPemeriksaan;
    private RekamMedis rekamMedis;
    private String jenisPemeriksaan;
    private String hasil;
    private String nilaiRujukan;

    public Pemeriksaan() {}

    public Pemeriksaan(int idPemeriksaan, RekamMedis rekamMedis, String jenisPemeriksaan, String hasil, String nilaiRujukan) {
        this.idPemeriksaan = idPemeriksaan;
        this.rekamMedis = rekamMedis;
        this.jenisPemeriksaan = jenisPemeriksaan;
        this.hasil = hasil;
        this.nilaiRujukan = nilaiRujukan;
    }

    public int getIdPemeriksaan() { return idPemeriksaan; }
    public void setIdPemeriksaan(int idPemeriksaan) { this.idPemeriksaan = idPemeriksaan; }
    public RekamMedis getRekamMedis() { return rekamMedis; }
    public void setRekamMedis(RekamMedis rekamMedis) { this.rekamMedis = rekamMedis; }
    public String getJenisPemeriksaan() { return jenisPemeriksaan; }
    public void setJenisPemeriksaan(String jenisPemeriksaan) { this.jenisPemeriksaan = jenisPemeriksaan; }
    public String getHasil() { return hasil; }
    public void setHasil(String hasil) { this.hasil = hasil; }
    public String getNilaiRujukan() { return nilaiRujukan; }
    public void setNilaiRujukan(String nilaiRujukan) { this.nilaiRujukan = nilaiRujukan; }
}