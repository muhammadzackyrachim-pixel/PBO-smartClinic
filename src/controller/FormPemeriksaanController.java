package controller;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import service.*;
import util.AlertUtil;
import util.ValidationUtil;

public class FormPemeriksaanController {
    @FXML private ComboBox<Pasien> cbPasien;
    @FXML private ComboBox<Dokter> cbDokter;
    @FXML private DatePicker dpTanggal;
    @FXML private TextField txtTensi, txtSuhu, txtBB, txtTB;
    @FXML private TextArea txtCatatan;

    private PemeriksaanService service = new PemeriksaanService();

    @FXML public void initialize() {
        cbPasien.getItems().addAll(new PasienService().getAll());
        cbDokter.getItems().addAll(new DokterService().getAll());
        dpTanggal.setValue(LocalDate.now());
    }

    @FXML private void handleSimpan() {
        if (cbPasien.getValue() == null || cbDokter.getValue() == null) { AlertUtil.warning("Pilih pasien & dokter!"); return; }
        if (ValidationUtil.isEmpty(txtTensi.getText())) { AlertUtil.warning("Tensi wajib diisi!"); return; }

        try {
            Pemeriksaan p = new Pemeriksaan();
            p.setPasien(cbPasien.getValue());
            p.setDokter(cbDokter.getValue());
            p.setTanggalPeriksa(dpTanggal.getValue());
            p.setTekananDarah(txtTensi.getText());
            p.setSuhuTubuh(Double.parseDouble(txtSuhu.getText()));
            p.setBeratBadan(Double.parseDouble(txtBB.getText()));
            p.setTinggiBadan(Double.parseDouble(txtTB.getText()));
            p.setCatatan(txtCatatan.getText());

            if (service.save(p)) {
                AlertUtil.success("Data pemeriksaan disimpan!");
                ((Stage) cbPasien.getScene().getWindow()).close();
            }
        } catch (NumberFormatException e) {
            AlertUtil.error("Input Suhu, BB, dan TB harus berupa angka!");
        }
    }

    @FXML private void handleBatal() { ((Stage) cbPasien.getScene().getWindow()).close(); }
}