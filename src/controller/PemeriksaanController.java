package controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Pemeriksaan;
import service.PemeriksaanService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;

public class PemeriksaanController implements Initializable {
    @FXML private javafx.scene.layout.HBox hboxTombol;
    @FXML private javafx.scene.control.Button btnTambah, btnHapus;
    @FXML private TableView<Pemeriksaan> tablePemeriksaan;
    @FXML private TableColumn<Pemeriksaan, Integer> colId;
    @FXML private TableColumn<Pemeriksaan, String> colPasien, colDokter, colTanggal, colTensi, colCatatan;
    @FXML private TableColumn<Pemeriksaan, Double> colSuhu, colBB, colTB;
    
    private PemeriksaanService service = new PemeriksaanService();
    ObservableList<Pemeriksaan> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPemeriksaan"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien")); // Memanggil toString()
        colDokter.setCellValueFactory(new PropertyValueFactory<>("dokter"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPeriksa"));
        colTensi.setCellValueFactory(new PropertyValueFactory<>("tekananDarah"));
        colSuhu.setCellValueFactory(new PropertyValueFactory<>("suhuTubuh"));
        colBB.setCellValueFactory(new PropertyValueFactory<>("beratBadan"));
        colTB.setCellValueFactory(new PropertyValueFactory<>("tinggiBadan"));
        colCatatan.setCellValueFactory(new PropertyValueFactory<>("catatan"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear(); list.addAll(service.getAll()); tablePemeriksaan.setItems(list);
    }

    @FXML public void handleTambah() {
        DashboardController.getInstance().setCenterContent("/view/form_pemeriksaan.fxml");
    }

    @FXML public void handleHapus() {
        Pemeriksaan p = tablePemeriksaan.getSelectionModel().getSelectedItem();
        if (p == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        if (AlertUtil.confirm("Yakin hapus data pemeriksaan ini?")) {
            service.delete(p.getIdPemeriksaan()); loadData();
        }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePemeriksaan.getScene().getWindow(), "/view/dashboard.fxml");
    }
}