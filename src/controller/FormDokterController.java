package controller;

import javafx.fxml.FXML;
import controller.DashboardController;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Dokter;
import model.Poli;
import service.DokterService;
import service.PoliService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormDokterController {
    @FXML private TextField txtId, txtNama, txtNoHP, txtEmail;
    @FXML private ComboBox<String> cbSpesialisasi;
    
    private DokterService service = new DokterService();
    private PoliService poliService = new PoliService();
    private Dokter dokter;

    @FXML public void initialize() {
        cbSpesialisasi.getItems().clear();
        for (Poli p : poliService.getAll()) {
            cbSpesialisasi.getItems().add(p.getNamaPoli());
        }
    }

    public void setModeTambah() {
        dokter = new Dokter();
        txtId.setText("Auto"); txtNama.clear(); cbSpesialisasi.setValue(null);
        txtNoHP.clear(); txtEmail.clear();
    }

    public void setModeEdit(Dokter d) {
        this.dokter = d;
        txtId.setText(String.valueOf(d.getIdDokter()));
        txtNama.setText(d.getNama()); 
        cbSpesialisasi.setValue(d.getSpesialisasi());
        txtNoHP.setText(d.getNoHP()); txtEmail.setText(d.getEmail());
    }

    @FXML private void handleSimpan() {
        if (ValidationUtil.isEmpty(txtNama.getText())) { AlertUtil.warning("Nama wajib diisi!"); return; }
        if (cbSpesialisasi.getValue() == null) { AlertUtil.warning("Spesialisasi wajib dipilih!"); return; }
        
        dokter.setNama(txtNama.getText());
        dokter.setSpesialisasi(cbSpesialisasi.getValue());
        dokter.setNoHP(txtNoHP.getText());
        dokter.setEmail(txtEmail.getText());

        if (service.save(dokter)) {
            AlertUtil.success("Data disimpan");
            DashboardController.getInstance().setCenterContent("/view/dokter.fxml");
        }
    }

    @FXML private void handleBatal() {
        DashboardController.getInstance().setCenterContent("/view/dokter.fxml");
    }
}