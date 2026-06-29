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
import model.RekamMedis;
import service.RekamMedisService;
import util.AlertUtil;
import util.SceneUtil;

public class RekamMedisController implements Initializable {
    @FXML private TableView<RekamMedis> tableRekamMedis;
    @FXML private TableColumn<RekamMedis, Integer> colId;
    @FXML private TableColumn<RekamMedis, String> colPasien, colDokter, colTanggal, colDiagnosis, colTindakan;

    private RekamMedisService service = new RekamMedisService();
    ObservableList<RekamMedis> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idRekamMedis"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colDokter.setCellValueFactory(new PropertyValueFactory<>("dokter"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPeriksa"));
        colDiagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        colTindakan.setCellValueFactory(new PropertyValueFactory<>("tindakan"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tableRekamMedis.setItems(list);
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_rekam_medis.fxml"));
            Stage stage = SceneUtil.createModal(loader, "Tambah Rekam Medis", 800, 400);
            FormRekamMedisController ctrl = loader.getController();
            ctrl.setModeTambah();
            stage.showAndWait();
            loadData();
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableRekamMedis.getScene().getWindow(), "/view/dashboard.fxml");
    }
}