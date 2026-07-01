package controller;

import javafx.fxml.FXML;
import controller.DashboardController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Pasien;
import service.PasienService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormPasienController {
    @FXML private TextField txtId, txtNama, txtNoHP, txtAlamat, txtGulaDarah, txtTekananDarah;
    @FXML private DatePicker dpTanggalLahir;
    @FXML private ComboBox<String> cbGender;

    private PasienService service = new PasienService();
    private Pasien pasien;

    @FXML public void initialize() {
        cbGender.getItems().addAll("Laki-laki", "Perempuan");
    }

    public void setModeTambah() {
        pasien = new Pasien();
        txtId.setText("Auto"); txtNama.clear(); dpTanggalLahir.setValue(null);
        txtNoHP.clear(); txtAlamat.clear(); txtGulaDarah.clear(); txtTekananDarah.clear();
        cbGender.getSelectionModel().clearSelection();
    }

    public void setModeEdit(Pasien p) {
        this.pasien = p;
        txtId.setText(String.valueOf(p.getIdPasien()));
        txtNama.setText(p.getNama()); dpTanggalLahir.setValue(p.getTanggalLahir());
        cbGender.setValue(p.getGender()); txtNoHP.setText(p.getNoHP());
        txtAlamat.setText(p.getAlamat());
        txtGulaDarah.setText(String.valueOf(p.getGulaDarah()));
        txtTekananDarah.setText(String.valueOf(p.getTekananDarah()));
    }

    @FXML private void handleSimpan() {
        if (ValidationUtil.isEmpty(txtNama.getText())) { AlertUtil.warning("Nama wajib diisi!"); return; }
        
        pasien.setNama(txtNama.getText());
        pasien.setTanggalLahir(dpTanggalLahir.getValue());
        pasien.setGender(cbGender.getValue());
        pasien.setNoHP(txtNoHP.getText());
        pasien.setAlamat(txtAlamat.getText());
        pasien.setGulaDarah(Double.parseDouble(txtGulaDarah.getText()));
        pasien.setTekananDarah(Double.parseDouble(txtTekananDarah.getText()));

        if (service.save(pasien)) {
            AlertUtil.success("Data disimpan");
            DashboardController.getInstance().setCenterContent("/view/pasien.fxml");
        } else {
            AlertUtil.error("Gagal menyimpan");
        }
    }

    @FXML private void handleBatal() {
        DashboardController.getInstance().setCenterContent("/view/pasien.fxml");
    }
}