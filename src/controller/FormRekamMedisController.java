package controller;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Dokter;
import model.Pasien;
import model.RekamMedis;
import service.DokterService;
import service.PasienService;
import service.RekamMedisService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormRekamMedisController {
    @FXML private ComboBox<Pasien> cbPasien;
    @FXML private ComboBox<Dokter> cbDokter;
    @FXML private DatePicker dpTanggal;
    @FXML private TextArea txtDiagnosis, txtTindakan, txtCatatan;

    private RekamMedisService service = new RekamMedisService();
    private RekamMedis rekamMedis;

    @FXML public void initialize() {
        cbPasien.getItems().addAll(new PasienService().getAll());
        cbDokter.getItems().addAll(new DokterService().getAll());
        dpTanggal.setValue(LocalDate.now());
    }

    public void setModeTambah() {
        rekamMedis = new RekamMedis();
        txtDiagnosis.clear(); txtTindakan.clear(); txtCatatan.clear();
    }

    @FXML private void handleSimpan() {
        if (cbPasien.getValue() == null || cbDokter.getValue() == null) { AlertUtil.warning("Pilih pasien & dokter!"); return; }
        if (ValidationUtil.isEmpty(txtDiagnosis.getText())) { AlertUtil.warning("Diagnosis wajib diisi!"); return; }

        rekamMedis.setPasien(cbPasien.getValue());
        rekamMedis.setDokter(cbDokter.getValue());
        rekamMedis.setTanggalPeriksa(dpTanggal.getValue());
        rekamMedis.setDiagnosis(txtDiagnosis.getText());
        rekamMedis.setTindakan(txtTindakan.getText());
        rekamMedis.setCatatan(txtCatatan.getText());

        if (service.save(rekamMedis)) {
            AlertUtil.success("Data disimpan");
            ((Stage) cbPasien.getScene().getWindow()).close();
        }
    }

    @FXML private void handleBatal() {
        ((Stage) cbPasien.getScene().getWindow()).close();
    }
}