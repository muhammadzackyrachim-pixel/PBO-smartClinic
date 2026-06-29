package controller;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Dokter;
import model.Pasien;
import model.Pendaftaran;
import service.DokterService;
import service.PasienService;
import service.PendaftaranService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormPendaftaranController {
    @FXML private ComboBox<Pasien> cbPasien;
    @FXML private ComboBox<Dokter> cbDokter;
    @FXML private DatePicker dpTanggal;
    @FXML private TextField txtWaktu, txtKeluhan;
    @FXML private ComboBox<String> cbStatus;

    private PendaftaranService service = new PendaftaranService();
    private Pendaftaran pendaftaran;

    @FXML public void initialize() {
        cbPasien.getItems().addAll(new PasienService().getAll());
        cbDokter.getItems().addAll(new DokterService().getAll());
        cbStatus.getItems().addAll("Menunggu", "Diperiksa", "Selesai");
        dpTanggal.setValue(LocalDate.now());
    }

    public void setModeTambah() {
        pendaftaran = new Pendaftaran();
        txtWaktu.setText(LocalTime.now().toString().substring(0, 5));
        txtKeluhan.clear();
        cbStatus.setValue("Menunggu");
    }

    @FXML private void handleSimpan() {
        if (cbPasien.getValue() == null || cbDokter.getValue() == null) { AlertUtil.warning("Pilih pasien & dokter!"); return; }
        if (ValidationUtil.isEmpty(txtKeluhan.getText())) { AlertUtil.warning("Keluhan wajib diisi!"); return; }

        pendaftaran.setPasien(cbPasien.getValue());
        pendaftaran.setDokter(cbDokter.getValue());
        pendaftaran.setTanggalDaftar(dpTanggal.getValue());
        pendaftaran.setWaktuDaftar(LocalTime.parse(txtWaktu.getText() + ":00"));
        pendaftaran.setKeluhan(txtKeluhan.getText());
        pendaftaran.setStatus(cbStatus.getValue());

        if (service.save(pendaftaran)) {
            AlertUtil.success("Data disimpan");
            ((Stage) cbPasien.getScene().getWindow()).close();
        }
    }

    @FXML private void handleBatal() {
        ((Stage) cbPasien.getScene().getWindow()).close();
    }
}