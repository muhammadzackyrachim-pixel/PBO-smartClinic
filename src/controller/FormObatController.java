package controller;

import javafx.fxml.FXML;
import controller.DashboardController;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Obat;
import service.ObatService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormObatController {
    @FXML private TextField txtId, txtNama, txtJenis, txtDosis, txtStok, txtHarga;
    private ObatService service = new ObatService();
    private Obat obat;

    public void setModeTambah() {
        obat = new Obat();
        txtId.setText("Auto"); txtNama.clear(); txtJenis.clear();
        txtDosis.clear(); txtStok.clear(); txtHarga.clear();
    }

    public void setModeEdit(Obat o) {
        this.obat = o;
        txtId.setText(String.valueOf(o.getIdObat()));
        txtNama.setText(o.getNamaObat()); txtJenis.setText(o.getJenis());
        txtDosis.setText(o.getDosis()); txtStok.setText(String.valueOf(o.getStok()));
        txtHarga.setText(String.valueOf(o.getHarga()));
    }

    @FXML private void handleSimpan() {
        if (ValidationUtil.isEmpty(txtNama.getText())) { AlertUtil.warning("Nama wajib diisi!"); return; }
        obat.setNamaObat(txtNama.getText());
        obat.setJenis(txtJenis.getText());
        obat.setDosis(txtDosis.getText());
        obat.setStok(Integer.parseInt(txtStok.getText()));
        obat.setHarga(Double.parseDouble(txtHarga.getText()));

        if (service.save(obat)) {
            AlertUtil.success("Data disimpan");
            DashboardController.getInstance().setCenterContent("/view/obat.fxml");
        }
    }

    @FXML private void handleBatal() {
        DashboardController.getInstance().setCenterContent("/view/obat.fxml");
    }
}