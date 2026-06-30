package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dao.PetugasDAO;
import model.Petugas;
import util.SceneUtil;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private PetugasDAO petugasDAO = new PetugasDAO();

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Username dan password harus diisi!");
            return;
        }

        Petugas petugas = petugasDAO.login(username, password);

        if (petugas != null) {
            lblError.setText("");
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            SceneUtil.switchScene(stage, "/view/dashboard.fxml");
        } else {
            lblError.setText("Username atau password salah!");
            txtPassword.clear();
        }
    }
}
