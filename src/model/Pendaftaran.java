package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Pendaftaran {
    private int idPendaftaran;
    private Pasien pasien;
    private Dokter dokter;
    private LocalDate tanggalDaftar;
    private LocalTime waktuDaftar;
    private String keluhan;
    private String status;

    public Pendaftaran() {}

    public Pendaftaran(int idPendaftaran, Pasien pasien, Dokter dokter, 
                      LocalDate tanggalDaftar, LocalTime waktuDaftar, 
                      String keluhan, String status) {
        this.idPendaftaran = idPendaftaran;
        this.pasien = pasien;
        this.dokter = dokter;
        this.tanggalDaftar = tanggalDaftar;
        this.waktuDaftar = waktuDaftar;
        this.keluhan = keluhan;
        this.status = status;
    }

    public int getIdPendaftaran() { return idPendaftaran; }
    public void setIdPendaftaran(int idPendaftaran) { this.idPendaftaran = idPendaftaran; }
    public Pasien getPasien() { return pasien; }
    public void setPasien(Pasien pasien) { this.pasien = pasien; }
    public Dokter getDokter() { return dokter; }
    public void setDokter(Dokter dokter) { this.dokter = dokter; }
    public LocalDate getTanggalDaftar() { return tanggalDaftar; }
    public void setTanggalDaftar(LocalDate tanggalDaftar) { this.tanggalDaftar = tanggalDaftar; }
    public LocalTime getWaktuDaftar() { return waktuDaftar; }
    public void setWaktuDaftar(LocalTime waktuDaftar) { this.waktuDaftar = waktuDaftar; }
    public String getKeluhan() { return keluhan; }
    public void setKeluhan(String keluhan) { this.keluhan = keluhan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        String namaPasien = (pasien != null) ? pasien.getNama() : "Unknown";
        return "ID: " + idPendaftaran + " - " + namaPasien + " (" + tanggalDaftar + ")";
    }
}