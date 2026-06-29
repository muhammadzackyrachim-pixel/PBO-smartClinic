package controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import service.*;
import util.SceneUtil;

public class RiwayatPemeriksaanController implements Initializable {
    @FXML private ComboBox<Pasien> cbPasien;
    @FXML private TableView<Pemeriksaan> tableRiwayat;
    @FXML private TableColumn<Pemeriksaan, String> colTanggal, colTensi, colCatatan;
    @FXML private TableColumn<Pemeriksaan, Double> colSuhu, colBB, colTB;

    private PemeriksaanService service = new PemeriksaanService();
    ObservableList<Pemeriksaan> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPasien.getItems().addAll(new PasienService().getAll());
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPeriksa"));
        colTensi.setCellValueFactory(new PropertyValueFactory<>("tekananDarah"));
        colSuhu.setCellValueFactory(new PropertyValueFactory<>("suhuTubuh"));
        colBB.setCellValueFactory(new PropertyValueFactory<>("beratBadan"));
        colTB.setCellValueFactory(new PropertyValueFactory<>("tinggiBadan"));
        colCatatan.setCellValueFactory(new PropertyValueFactory<>("catatan"));
    }

    @FXML public void handlePilihPasien() {
        Pasien selected = cbPasien.getValue();
        if (selected != null) {
            list.clear();
            list.addAll(service.getByPasien(selected.getIdPasien()));
            tableRiwayat.setItems(list);
        }
    }
    
    @FXML public void loadData() { handlePilihPasien(); }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableRiwayat.getScene().getWindow(), "/view/dashboard.fxml");
    }
}