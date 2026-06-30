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
import model.Poli;
import service.PoliService;
import util.AlertUtil;
import util.SceneUtil;

public class PoliController implements Initializable {
    @FXML private TableView<Poli> tablePoli;
    @FXML private TableColumn<Poli, Integer> colId;
    @FXML private TableColumn<Poli, String> colNama, colKeterangan;

    private PoliService service = new PoliService();
    ObservableList<Poli> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPoli"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaPoli"));
        colKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        list.addAll(service.getAll());
        tablePoli.setItems(list);
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_poli.fxml"));
            Stage stage = SceneUtil.createModal(loader, "Tambah Poli", 600, 300);
            FormPoliController ctrl = loader.getController();
            ctrl.setModeTambah();
            stage.showAndWait();
            loadData();
        } catch (Exception e) {
            AlertUtil.error("Gagal buka form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML public void handleEdit() {
        Poli selected = tablePoli.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.warning("Pilih data yang ingin diedit!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_poli.fxml"));
            Stage stage = SceneUtil.createModal(loader, "Edit Poli", 600, 300);
            FormPoliController ctrl = loader.getController();
            ctrl.setModeEdit(selected);
            stage.showAndWait();
            loadData();
        } catch (Exception e) {
            AlertUtil.error("Gagal buka form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML public void handleHapus() {
        Poli selected = tablePoli.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.warning("Pilih data yang ingin dihapus!");
            return;
        }
        if (AlertUtil.confirm("Yakin ingin menghapus poli " + selected.getNamaPoli() + "?")) {
            if (service.delete(selected.getIdPoli())) {
                AlertUtil.success("Data berhasil dihapus!");
                loadData();
            } else {
                AlertUtil.error("Gagal menghapus data!");
            }
        }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePoli.getScene().getWindow(), "/view/dashboard.fxml");
    }
}
