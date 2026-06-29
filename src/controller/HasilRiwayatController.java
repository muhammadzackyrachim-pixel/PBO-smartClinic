package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Prediksi;
import service.PrediksiService;
import util.SceneUtil;

public class HasilRiwayatController implements Initializable {
    @FXML private TableView<Prediksi> tablePrediksi;
    @FXML private TableColumn<Prediksi, Integer> colId;
    @FXML private TableColumn<Prediksi, String> colPasien, colTanggal, colHasil;

    private PrediksiService service = new PrediksiService();
    ObservableList<Prediksi> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPrediksi"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPrediksi"));
        colHasil.setCellValueFactory(new PropertyValueFactory<>("hasil"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tablePrediksi.setItems(list);
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePrediksi.getScene().getWindow(), "/view/dashboard.fxml");
    }
}