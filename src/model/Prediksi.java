package model;

import java.time.LocalDate;

public class Prediksi {
    private int idPrediksi;
    private Pasien pasien;
    private int pregnancies;
    private double glucose;
    private double bloodPressure;
    private double skinThickness;
    private double insulin;
    private double bmi;
    private double pedigree;
    private int age;
    private String hasil;
    private LocalDate tanggalPrediksi;

    public Prediksi() {}

    public Prediksi(Pasien pasien, int pregnancies, double glucose, 
                   double bloodPressure, double skinThickness, double insulin,
                   double bmi, double pedigree, int age, String hasil, LocalDate tanggalPrediksi) {
        this.pasien = pasien;
        this.pregnancies = pregnancies;
        this.glucose = glucose;
        this.bloodPressure = bloodPressure;
        this.skinThickness = skinThickness;
        this.insulin = insulin;
        this.bmi = bmi;
        this.pedigree = pedigree;
        this.age = age;
        this.hasil = hasil;
        this.tanggalPrediksi = tanggalPrediksi;
    }

    public int getIdPrediksi() { return idPrediksi; }
    public void setIdPrediksi(int idPrediksi) { this.idPrediksi = idPrediksi; }
    public Pasien getPasien() { return pasien; }
    public void setPasien(Pasien pasien) { this.pasien = pasien; }
    public int getPregnancies() { return pregnancies; }
    public void setPregnancies(int pregnancies) { this.pregnancies = pregnancies; }
    public double getGlucose() { return glucose; }
    public void setGlucose(double glucose) { this.glucose = glucose; }
    public double getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(double bloodPressure) { this.bloodPressure = bloodPressure; }
    public double getSkinThickness() { return skinThickness; }
    public void setSkinThickness(double skinThickness) { this.skinThickness = skinThickness; }
    public double getInsulin() { return insulin; }
    public void setInsulin(double insulin) { this.insulin = insulin; }
    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }
    public double getPedigree() { return pedigree; }
    public void setPedigree(double pedigree) { this.pedigree = pedigree; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getHasil() { return hasil; }
    public void setHasil(String hasil) { this.hasil = hasil; }
    public LocalDate getTanggalPrediksi() { return tanggalPrediksi; }
    public void setTanggalPrediksi(LocalDate tanggalPrediksi) { this.tanggalPrediksi = tanggalPrediksi; }
}