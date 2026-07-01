package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import java.util.List;
import java.util.stream.Collectors;
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
    
    @FXML private TextField txtCari;
    @FXML private DatePicker dpTanggal;
    @FXML private ComboBox<String> cbHasil;

    private List<Prediksi> allData;

    private PrediksiService service = new PrediksiService();
    ObservableList<Prediksi> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPrediksi"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPrediksi"));
        colHasil.setCellValueFactory(new PropertyValueFactory<>("hasil"));
        
        cbHasil.getItems().addAll("Semua Hasil", "RISIKO DIABETES RENDAH", "RISIKO DIABETES TINGGI");
        cbHasil.setValue("Semua Hasil");

        txtCari.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        dpTanggal.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        cbHasil.valueProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        
        loadData();
    }

    @FXML public void loadData() {
        allData = service.getAll();
        applyFilters();
    }

    private void applyFilters() {
        if (allData == null) return;
        
        String keyword = txtCari.getText() != null ? txtCari.getText().toLowerCase() : "";
        java.time.LocalDate tanggal = dpTanggal.getValue();
        String hasil = cbHasil.getValue();
        
        List<Prediksi> filtered = allData.stream()
            .filter(p -> p.getPasien() != null && p.getPasien().getNama().toLowerCase().contains(keyword))
            .filter(p -> tanggal == null || (p.getTanggalPrediksi() != null && p.getTanggalPrediksi().equals(tanggal)))
            .filter(p -> hasil == null || hasil.equals("Semua Hasil") || (p.getHasil() != null && p.getHasil().equalsIgnoreCase(hasil)))
            .collect(Collectors.toList());
            
        list.clear();
        list.addAll(filtered);
        tablePrediksi.setItems(list);
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePrediksi.getScene().getWindow(), "/view/dashboard.fxml");
    }
}