package controller;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Pasien;
import model.Prediksi;
import service.PasienService;
import service.PrediksiService;
import util.AlertUtil;
import util.SceneUtil;
import util.ValidationUtil;

public class PrediksiController {

    @FXML private ComboBox<Pasien> cbPasien;
    @FXML private TextField txtPregnancies;
    @FXML private TextField txtGlucose;
    @FXML private TextField txtBloodPressure;
    @FXML private TextField txtSkinThickness;
    @FXML private TextField txtInsulin;
    @FXML private TextField txtBMI;
    @FXML private TextField txtPedigree;
    @FXML private TextField txtAge;
    @FXML private Label lblHasil;

    private PrediksiService prediksiService = new PrediksiService();

    @FXML
    public void initialize() {
        loadPasien();
        
        // Auto-fill umur saat pasien dipilih
        cbPasien.setOnAction(e -> {
            Pasien p = cbPasien.getValue();
            if (p != null) {
                txtAge.setText(String.valueOf(p.getUmur()));
            }
        });
    }

    @FXML
    private void handlePrediksi() {
        // Validasi input
        if (cbPasien.getValue() == null) {
            AlertUtil.warning("Pilih pasien terlebih dahulu!");
            return;
        }
        if (ValidationUtil.isEmpty(txtGlucose.getText()) || 
            ValidationUtil.isEmpty(txtBMI.getText())) {
            AlertUtil.warning("Glucose dan BMI harus diisi!");
            return;
        }

        try {
            // Ambil nilai dari form
            double glucose = Double.parseDouble(txtGlucose.getText());
            double bmi = Double.parseDouble(txtBMI.getText());
            int age = Integer.parseInt(txtAge.getText());

            // Prediksi sederhana berbasis rule
            String hasil;
            if (glucose > 140 && bmi > 30 && age > 40) {
                hasil = "RISIKO DIABETES TINGGI";
                lblHasil.setText(hasil);
                lblHasil.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-font-size: 18px;");
            } else {
                hasil = "RISIKO DIABETES RENDAH";
                lblHasil.setText(hasil);
                lblHasil.setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold; -fx-font-size: 18px;");
            }

            // Simpan hasil prediksi ke database
            Prediksi prediksi = new Prediksi();
            prediksi.setPasien(cbPasien.getValue());
            prediksi.setPregnancies(txtPregnancies.getText().isEmpty() ? 0 : 
                                   Integer.parseInt(txtPregnancies.getText()));
            prediksi.setGlucose(glucose);
            prediksi.setBloodPressure(txtBloodPressure.getText().isEmpty() ? 0 : 
                                     Double.parseDouble(txtBloodPressure.getText()));
            prediksi.setSkinThickness(txtSkinThickness.getText().isEmpty() ? 0 : 
                                     Double.parseDouble(txtSkinThickness.getText()));
            prediksi.setInsulin(txtInsulin.getText().isEmpty() ? 0 : 
                               Double.parseDouble(txtInsulin.getText()));
            prediksi.setBmi(bmi);
            prediksi.setPedigree(txtPedigree.getText().isEmpty() ? 0 : 
                                Double.parseDouble(txtPedigree.getText()));
            prediksi.setAge(age);
            prediksi.setHasil(hasil);
            prediksi.setTanggalPrediksi(LocalDate.now());

            if (prediksiService.save(prediksi)) {
                AlertUtil.success("Prediksi berhasil disimpan!");
            }

        } catch (NumberFormatException e) {
            lblHasil.setText("Format angka tidak valid!");
            lblHasil.setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
            AlertUtil.error("Mohon periksa kembali input angka Anda!");
        } catch (Exception e) {
            AlertUtil.error("Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ METHOD HANDLE BACK UNTUK TOMBOL KEMBALI
    @FXML
    private void handleBack() {
        Stage stage = (Stage) cbPasien.getScene().getWindow();
        SceneUtil.switchScene(stage, "/view/dashboard.fxml");
    }

    private void loadPasien() {
        cbPasien.getItems().clear();
        cbPasien.getItems().addAll(new PasienService().getAll());
    }
}