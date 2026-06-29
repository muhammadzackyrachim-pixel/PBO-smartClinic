package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Petugas;
import service.PetugasService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormPetugasController {
    @FXML private TextField txtId, txtNama, txtUsername, txtPassword, txtNoHP, txtEmail;
    @FXML private ComboBox<String> cbRole;
    private PetugasService service = new PetugasService();
    private Petugas petugas;

    @FXML public void initialize() {
        cbRole.getItems().addAll("Admin", "Perawat", "Staff");
    }

    public void setModeTambah() {
        petugas = new Petugas();
        txtId.setText("Auto"); txtNama.clear(); txtUsername.clear();
        txtPassword.clear(); txtNoHP.clear(); txtEmail.clear();
        cbRole.getSelectionModel().clearSelection();
    }

    public void setModeEdit(Petugas p) {
        this.petugas = p;
        txtId.setText(String.valueOf(p.getIdPetugas()));
        txtNama.setText(p.getNama()); txtUsername.setText(p.getUsername());
        txtPassword.setText(p.getPassword()); txtNoHP.setText(p.getNoHP());
        txtEmail.setText(p.getEmail()); cbRole.setValue(p.getRole());
    }

    @FXML private void handleSimpan() {
        if (ValidationUtil.isEmpty(txtNama.getText())) { AlertUtil.warning("Nama wajib diisi!"); return; }
        petugas.setNama(txtNama.getText());
        petugas.setUsername(txtUsername.getText());
        petugas.setPassword(txtPassword.getText());
        petugas.setRole(cbRole.getValue());
        petugas.setNoHP(txtNoHP.getText());
        petugas.setEmail(txtEmail.getText());

        if (service.save(petugas)) {
            AlertUtil.success("Data disimpan");
            ((Stage) txtNama.getScene().getWindow()).close();
        }
    }

    @FXML private void handleBatal() {
        ((Stage) txtNama.getScene().getWindow()).close();
    }
}