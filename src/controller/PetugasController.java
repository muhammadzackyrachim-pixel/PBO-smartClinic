package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Petugas;
import service.PetugasService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;
import java.net.URL;
import java.util.ResourceBundle;

public class PetugasController implements Initializable {
    @FXML private javafx.scene.layout.HBox hboxTombol;
    @FXML private javafx.scene.control.Button btnTambah, btnEdit, btnHapus;
    @FXML private TableView<Petugas> tablePetugas;
    @FXML private TableColumn<Petugas, Integer> colId;
    @FXML private TableColumn<Petugas, String> colNama, colUsername, colRole, colNoHP, colEmail;

    private PetugasService service = new PetugasService();
    ObservableList<Petugas> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPetugas"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colNoHP.setCellValueFactory(new PropertyValueFactory<>("noHP"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tablePetugas.setItems(list);
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_petugas.fxml"));
            javafx.scene.Node node = loader.load();
            FormPetugasController ctrl = loader.getController();
            ctrl.setModeTambah();
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleEdit() {
        Petugas p = tablePetugas.getSelectionModel().getSelectedItem();
        if (p == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_petugas.fxml"));
            javafx.scene.Node node = loader.load();
            FormPetugasController ctrl = loader.getController();
            ctrl.setModeEdit(p);
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleHapus() {
        Petugas p = tablePetugas.getSelectionModel().getSelectedItem();
        if (p == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        if (AlertUtil.confirm("Yakin hapus?")) {
            service.delete(p.getIdPetugas());
            loadData();
        }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePetugas.getScene().getWindow(), "/view/dashboard.fxml");
    }
}