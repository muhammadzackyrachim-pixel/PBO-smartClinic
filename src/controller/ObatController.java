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
import model.Obat;
import service.ObatService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;

public class ObatController implements Initializable {
    @FXML private javafx.scene.layout.HBox hboxTombol;
    @FXML private javafx.scene.control.Button btnTambah, btnEdit, btnHapus;
    @FXML private TableView<Obat> tableObat;
    @FXML private TableColumn<Obat, Integer> colId, colStok;
    @FXML private TableColumn<Obat, String> colNama, colJenis, colDosis;
    @FXML private TableColumn<Obat, Double> colHarga;

    private ObatService service = new ObatService();
    ObservableList<Obat> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idObat"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaObat"));
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        colDosis.setCellValueFactory(new PropertyValueFactory<>("dosis"));
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tableObat.setItems(list);
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_obat.fxml"));
            javafx.scene.Node node = loader.load();
            FormObatController ctrl = loader.getController();
            ctrl.setModeTambah();
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleEdit() {
        Obat o = tableObat.getSelectionModel().getSelectedItem();
        if (o == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_obat.fxml"));
            javafx.scene.Node node = loader.load();
            FormObatController ctrl = loader.getController();
            ctrl.setModeEdit(o);
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleHapus() {
        Obat o = tableObat.getSelectionModel().getSelectedItem();
        if (o == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        if (AlertUtil.confirm("Yakin hapus?")) {
            service.delete(o.getIdObat());
            loadData();
        }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableObat.getScene().getWindow(), "/view/dashboard.fxml");
    }
}