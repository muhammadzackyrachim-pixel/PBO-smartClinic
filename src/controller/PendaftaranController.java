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
import model.Pendaftaran;
import service.PendaftaranService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;

public class PendaftaranController implements Initializable {
    @FXML private TableView<Pendaftaran> tablePendaftaran;
    @FXML private TableColumn<Pendaftaran, Integer> colId;
    @FXML private TableColumn<Pendaftaran, String> colPasien, colDokter, colTanggal, colKeluhan, colStatus;

    private PendaftaranService service = new PendaftaranService();
    ObservableList<Pendaftaran> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPendaftaran"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colDokter.setCellValueFactory(new PropertyValueFactory<>("dokter"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalDaftar"));
        colKeluhan.setCellValueFactory(new PropertyValueFactory<>("keluhan"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tablePendaftaran.setItems(list);
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_pendaftaran.fxml"));
            javafx.scene.Node node = loader.load();
            FormPendaftaranController ctrl = loader.getController();
            ctrl.setModeTambah();
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePendaftaran.getScene().getWindow(), "/view/dashboard.fxml");
    }
}