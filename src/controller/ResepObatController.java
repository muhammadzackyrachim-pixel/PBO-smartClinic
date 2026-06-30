package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.RekamMedis;
import model.ResepObat;
import service.ResepObatService;
import util.AlertUtil;
import util.SceneUtil;

public class ResepObatController implements Initializable {
    @FXML private Label lblInfo;
    @FXML private TableView<ResepObat> tableResep;
    @FXML private TableColumn<ResepObat, Integer> colId;
    @FXML private TableColumn<ResepObat, String> colObat, colDosis, colAturanPakai;
    @FXML private TableColumn<ResepObat, Integer> colJumlah;

    private ResepObatService service = new ResepObatService();
    private ObservableList<ResepObat> list = FXCollections.observableArrayList();
    private RekamMedis rekamMedis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdResep()).asObject());
        colObat.setCellValueFactory(data -> new SimpleStringProperty(
            data.getValue().getObat() != null ? data.getValue().getObat().getNamaObat() : "-"));
        colDosis.setCellValueFactory(data -> new SimpleStringProperty(
            data.getValue().getObat() != null ? data.getValue().getObat().getDosis() : "-"));
        colJumlah.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getJumlah()).asObject());
        colAturanPakai.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAturanPakai()));
    }

    public void setRekamMedis(RekamMedis rm) {
        this.rekamMedis = rm;
        if (rm != null) {
            String info = "Rekam Medis #" + rm.getIdRekamMedis();
            if (rm.getPasien() != null) info += " - Pasien: " + rm.getPasien().getNama();
            if (rm.getDiagnosis() != null) info += " - Diagnosis: " + rm.getDiagnosis();
            lblInfo.setText(info);
        }
        loadData();
    }

    @FXML public void loadData() {
        list.clear();
        if (rekamMedis != null) {
            list.addAll(service.getByRekamMedisId(rekamMedis.getIdRekamMedis()));
        }
        tableResep.setItems(list);
    }

    @FXML public void handleTambah() {
        if (rekamMedis == null) {
            AlertUtil.warning("Data rekam medis tidak tersedia!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_resep_obat.fxml"));
            Stage stage = SceneUtil.createModal(loader, "Input Resep Obat", 700, 400);
            FormResepObatController ctrl = loader.getController();
            ctrl.setRekamMedis(rekamMedis);
            stage.showAndWait();
            loadData();
        } catch (Exception e) {
            AlertUtil.error("Gagal buka form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML public void handleHapus() {
        ResepObat selected = tableResep.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.warning("Pilih resep yang ingin dihapus!");
            return;
        }
        if (AlertUtil.confirm("Yakin ingin menghapus resep ini? Stok obat akan dikembalikan.")) {
            if (service.delete(selected.getIdResep())) {
                AlertUtil.success("Resep berhasil dihapus dan stok dikembalikan!");
                loadData();
            } else {
                AlertUtil.error("Gagal menghapus resep!");
            }
        }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableResep.getScene().getWindow(), "/view/rekam_medis.fxml");
    }
}
