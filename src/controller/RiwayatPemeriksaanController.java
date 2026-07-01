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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RiwayatPemeriksaanController implements Initializable {
    @FXML private TextField txtCari;
    @FXML private DatePicker dpTanggal;
    @FXML private TableView<Pemeriksaan> tableRiwayat;
    @FXML private TableColumn<Pemeriksaan, String> colPasien, colTanggal, colTensi, colCatatan;
    @FXML private TableColumn<Pemeriksaan, Double> colSuhu, colBB, colTB;
    @FXML private Label lblHalaman;
    @FXML private Button btnPrev, btnNext;

    private PemeriksaanService service = new PemeriksaanService();
    private ObservableList<Pemeriksaan> list = FXCollections.observableArrayList();
    
    private final int PAGE_SIZE = 50;
    private int currentPage = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colPasien.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getPasien() != null ? cellData.getValue().getPasien().getNama() : "-"
        ));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPeriksa"));
        colTensi.setCellValueFactory(new PropertyValueFactory<>("tekananDarah"));
        colSuhu.setCellValueFactory(new PropertyValueFactory<>("suhuTubuh"));
        colBB.setCellValueFactory(new PropertyValueFactory<>("beratBadan"));
        colTB.setCellValueFactory(new PropertyValueFactory<>("tinggiBadan"));
        colCatatan.setCellValueFactory(new PropertyValueFactory<>("catatan"));
        
        txtCari.textProperty().addListener((obs, oldVal, newVal) -> {
            currentPage = 1;
            loadData();
        });
        dpTanggal.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentPage = 1;
            loadData();
        });
        loadData();
    }
    
    @FXML public void loadData() {
        String keyword = txtCari != null ? txtCari.getText() : "";
        LocalDate date = dpTanggal != null ? dpTanggal.getValue() : null;
        int offset = (currentPage - 1) * PAGE_SIZE;
        
        list.clear();
        list.addAll(service.getAllPaginated(PAGE_SIZE, offset, keyword, date));
        tableRiwayat.setItems(list);
        
        int totalData = service.getTotal(keyword, date);
        int totalPages = (int) Math.ceil((double) totalData / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;
        
        if (lblHalaman != null) {
            lblHalaman.setText("Halaman " + currentPage + " / " + totalPages);
            boolean showPrev = currentPage > 1;
            btnPrev.setVisible(showPrev);
            btnPrev.setManaged(showPrev);
            
            boolean showNext = currentPage < totalPages;
            btnNext.setVisible(showNext);
            btnNext.setManaged(showNext);
        }
    }
    
    @FXML public void handlePrevPage() {
        if (currentPage > 1) {
            currentPage--;
            loadData();
        }
    }
    
    @FXML public void handleNextPage() {
        String keyword = txtCari != null ? txtCari.getText() : "";
        LocalDate date = dpTanggal != null ? dpTanggal.getValue() : null;
        int totalData = service.getTotal(keyword, date);
        int totalPages = (int) Math.ceil((double) totalData / PAGE_SIZE);
        if (currentPage < totalPages) {
            currentPage++;
            loadData();
        }
    }
    
    @FXML public void handleReset() {
        txtCari.clear();
        dpTanggal.setValue(null);
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableRiwayat.getScene().getWindow(), "/view/dashboard.fxml");
    }
}