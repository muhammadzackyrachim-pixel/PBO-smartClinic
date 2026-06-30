package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Poli;
import service.PoliService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormPoliController {

    @FXML private TextField txtId;
    @FXML private TextField txtNamaPoli;
    @FXML private TextField txtKeterangan;

    private Poli poli;
    private PoliService service = new PoliService();

    public void setModeTambah() {
        poli = new Poli();
        txtId.setText("Auto");
        txtNamaPoli.clear();
        txtKeterangan.clear();
    }

    public void setModeEdit(Poli p) {
        this.poli = p;
        txtId.setText(String.valueOf(p.getIdPoli()));
        txtNamaPoli.setText(p.getNamaPoli());
        txtKeterangan.setText(p.getKeterangan());
    }

    @FXML
    private void handleSimpan() {
        if (ValidationUtil.isEmpty(txtNamaPoli.getText())) {
            AlertUtil.warning("Nama poli harus diisi!");
            return;
        }

        poli.setNamaPoli(txtNamaPoli.getText().trim());
        poli.setKeterangan(txtKeterangan.getText().trim());

        if (service.save(poli)) {
            AlertUtil.success("Data poli berhasil disimpan!");
            ((Stage) txtNamaPoli.getScene().getWindow()).close();
        } else {
            AlertUtil.error("Gagal menyimpan data poli!");
        }
    }

    @FXML
    private void handleBatal() {
        ((Stage) txtNamaPoli.getScene().getWindow()).close();
    }
}
