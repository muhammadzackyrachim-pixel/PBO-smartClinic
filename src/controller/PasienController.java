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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Pasien;
import service.PasienService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;

public class PasienController implements Initializable {
    @FXML private TextField txtCari;
    @FXML private TableView<Pasien> tablePasien;
    @FXML private TableColumn<Pasien, Integer> colId;
    @FXML private TableColumn<Pasien, String> colNama, colGender, colAlamat, colHP;
    @FXML private TableColumn<Pasien, Integer> colUmur;
    @FXML private TableColumn<Pasien, Double> colGula, colTekanan;

    private PasienService service = new PasienService();
    ObservableList<Pasien> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPasien"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colUmur.setCellValueFactory(new PropertyValueFactory<>("umur"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colHP.setCellValueFactory(new PropertyValueFactory<>("noHP"));
        colGula.setCellValueFactory(new PropertyValueFactory<>("gulaDarah"));
        colTekanan.setCellValueFactory(new PropertyValueFactory<>("tekananDarah"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tablePasien.setItems(list);
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_pasien.fxml"));
            javafx.scene.Node node = loader.load();
            FormPasienController ctrl = loader.getController();
            ctrl.setModeTambah();
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleEdit() {
        Pasien p = tablePasien.getSelectionModel().getSelectedItem();
        if (p == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_pasien.fxml"));
            javafx.scene.Node node = loader.load();
            FormPasienController ctrl = loader.getController();
            ctrl.setModeEdit(p);
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleHapus() {
        Pasien p = tablePasien.getSelectionModel().getSelectedItem();
        if (p == null) { AlertUtil.warning("Pilih data dulu!"); return; }
        if (AlertUtil.confirm("Yakin hapus data ini?")) {
            service.delete(p.getIdPasien());
            loadData();
        }
    }

    @FXML public void handleCari() {
        list.clear();
        list.addAll(service.search(txtCari.getText()));
        tablePasien.setItems(list);
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePasien.getScene().getWindow(), "/view/dashboard.fxml");
    }
}