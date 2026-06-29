package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Dokter;
import service.DokterService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormDokterController {
    @FXML private TextField txtId, txtNama, txtSpesialisasi, txtNoHP, txtEmail;
    private DokterService service = new DokterService();
    private Dokter dokter;

    public void setModeTambah() {
        dokter = new Dokter();
        txtId.setText("Auto"); txtNama.clear(); txtSpesialisasi.clear();
        txtNoHP.clear(); txtEmail.clear();
    }

    public void setModeEdit(Dokter d) {
        this.dokter = d;
        txtId.setText(String.valueOf(d.getIdDokter()));
        txtNama.setText(d.getNama()); txtSpesialisasi.setText(d.getSpesialisasi());
        txtNoHP.setText(d.getNoHP()); txtEmail.setText(d.getEmail());
    }

    @FXML private void handleSimpan() {
        if (ValidationUtil.isEmpty(txtNama.getText())) { AlertUtil.warning("Nama wajib diisi!"); return; }
        dokter.setNama(txtNama.getText());
        dokter.setSpesialisasi(txtSpesialisasi.getText());
        dokter.setNoHP(txtNoHP.getText());
        dokter.setEmail(txtEmail.getText());

        if (service.save(dokter)) {
            AlertUtil.success("Data disimpan");
            ((Stage) txtNama.getScene().getWindow()).close();
        }
    }

    @FXML private void handleBatal() {
        ((Stage) txtNama.getScene().getWindow()).close();
    }
}