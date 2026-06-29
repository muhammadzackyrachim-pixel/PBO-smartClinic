package model;
import java.time.LocalDate;

public class Pemeriksaan {
    private int idPemeriksaan;
    private Pasien pasien;
    private Dokter dokter;
    private LocalDate tanggalPeriksa;
    private String tekananDarah;
    private double suhuTubuh;
    private double beratBadan;
    private double tinggiBadan;
    private String catatan;

    public Pemeriksaan() {}
    // Getters & Setters (Generate otomatis di IDE Anda)
    public int getIdPemeriksaan() { return idPemeriksaan; }
    public void setIdPemeriksaan(int idPemeriksaan) { this.idPemeriksaan = idPemeriksaan; }
    public Pasien getPasien() { return pasien; }
    public void setPasien(Pasien pasien) { this.pasien = pasien; }
    public Dokter getDokter() { return dokter; }
    public void setDokter(Dokter dokter) { this.dokter = dokter; }
    public LocalDate getTanggalPeriksa() { return tanggalPeriksa; }
    public void setTanggalPeriksa(LocalDate tanggalPeriksa) { this.tanggalPeriksa = tanggalPeriksa; }
    public String getTekananDarah() { return tekananDarah; }
    public void setTekananDarah(String tekananDarah) { this.tekananDarah = tekananDarah; }
    public double getSuhuTubuh() { return suhuTubuh; }
    public void setSuhuTubuh(double suhuTubuh) { this.suhuTubuh = suhuTubuh; }
    public double getBeratBadan() { return beratBadan; }
    public void setBeratBadan(double beratBadan) { this.beratBadan = beratBadan; }
    public double getTinggiBadan() { return tinggiBadan; }
    public void setTinggiBadan(double tinggiBadan) { this.tinggiBadan = tinggiBadan; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}