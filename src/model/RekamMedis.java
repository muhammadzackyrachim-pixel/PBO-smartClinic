package model;

import java.time.LocalDate;

public class RekamMedis {
    private int idRekamMedis;
    private Pendaftaran pendaftaran;
    private Pasien pasien;
    private Dokter dokter;
    private LocalDate tanggalPeriksa;
    private String diagnosis;
    private String tindakan;
    private String catatan;

    public RekamMedis() {}

    public RekamMedis(int idRekamMedis, Pendaftaran pendaftaran, Pasien pasien, 
                     Dokter dokter, LocalDate tanggalPeriksa, 
                     String diagnosis, String tindakan, String catatan) {
        this.idRekamMedis = idRekamMedis;
        this.pendaftaran = pendaftaran;
        this.pasien = pasien;
        this.dokter = dokter;
        this.tanggalPeriksa = tanggalPeriksa;
        this.diagnosis = diagnosis;
        this.tindakan = tindakan;
        this.catatan = catatan;
    }

    public int getIdRekamMedis() { return idRekamMedis; }
    public void setIdRekamMedis(int idRekamMedis) { this.idRekamMedis = idRekamMedis; }
    public Pendaftaran getPendaftaran() { return pendaftaran; }
    public void setPendaftaran(Pendaftaran pendaftaran) { this.pendaftaran = pendaftaran; }
    public Pasien getPasien() { return pasien; }
    public void setPasien(Pasien pasien) { this.pasien = pasien; }
    public Dokter getDokter() { return dokter; }
    public void setDokter(Dokter dokter) { this.dokter = dokter; }
    public LocalDate getTanggalPeriksa() { return tanggalPeriksa; }
    public void setTanggalPeriksa(LocalDate tanggalPeriksa) { this.tanggalPeriksa = tanggalPeriksa; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTindakan() { return tindakan; }
    public void setTindakan(String tindakan) { this.tindakan = tindakan; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
}