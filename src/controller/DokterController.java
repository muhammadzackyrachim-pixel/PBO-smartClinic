package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Dokter;
import service.DokterService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;

public class DokterController implements Initializable {
    @FXML private TableView<Dokter> tableDokter;
    @FXML private TableColumn<Dokter, Integer> colId;
    @FXML private TableColumn<Dokter, String> colNama, colSpesialisasi, colHP, colEmail;

    private DokterService service = new DokterService();
    ObservableList<Dokter> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idDokter"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colSpesialisasi.setCellValueFactory(new PropertyValueFactory<>("spesialisasi"));
        colHP.setCellValueFactory(new PropertyValueFactory<>("noHP"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tableDokter.setItems(list);
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_dokter.fxml"));
            javafx.scene.Node node = loader.load();
            FormDokterController ctrl = loader.getController();
            ctrl.setModeTambah();
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleEdit() {
        Dokter d = tableDokter.getSelectionModel().getSelectedItem();
        if (d == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_dokter.fxml"));
            javafx.scene.Node node = loader.load();
            FormDokterController ctrl = loader.getController();
            ctrl.setModeEdit(d);
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleHapus() {
        Dokter d = tableDokter.getSelectionModel().getSelectedItem();
        if (d == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        if (AlertUtil.confirm("Yakin hapus?")) {
            service.delete(d.getIdDokter());
            loadData();
        }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableDokter.getScene().getWindow(), "/view/dashboard.fxml");
    }
}