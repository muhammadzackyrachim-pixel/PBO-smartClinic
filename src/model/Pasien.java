package model;

import java.time.LocalDate;
import java.time.Period;

public class Pasien {
    private int idPasien;
    private String nama;
    private LocalDate tanggalLahir;
    private String gender;
    private String noHP;
    private String alamat;
    private double gulaDarah;
    private double tekananDarah;

    // Constructor Kosong
    public Pasien() {}

    // Constructor Lengkap
    public Pasien(int idPasien, String nama, LocalDate tanggalLahir, String gender, 
                  String noHP, String alamat, double gulaDarah, double tekananDarah) {
        this.idPasien = idPasien;
        this.nama = nama;
        this.tanggalLahir = tanggalLahir;
        this.gender = gender;
        this.noHP = noHP;
        this.alamat = alamat;
        this.gulaDarah = gulaDarah;
        this.tekananDarah = tekananDarah;
    }

    // Getters & Setters
    public int getIdPasien() { return idPasien; }
    public void setIdPasien(int idPasien) { this.idPasien = idPasien; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public LocalDate getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(LocalDate tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public int getUmur() {
        return tanggalLahir != null ? Period.between(tanggalLahir, LocalDate.now()).getYears() : 0;
    }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getNoHP() { return noHP; }
    public void setNoHP(String noHP) { this.noHP = noHP; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public double getGulaDarah() { return gulaDarah; }
    public void setGulaDarah(double gulaDarah) { this.gulaDarah = gulaDarah; }

    public double getTekananDarah() { return tekananDarah; }
    public void setTekananDarah(double tekananDarah) { this.tekananDarah = tekananDarah; }

    @Override
    public String toString() {
        return "RM-" + idPasien + " | " + nama + " (" + gender + ", " + getUmur() + " th) - " + alamat;
    }
}