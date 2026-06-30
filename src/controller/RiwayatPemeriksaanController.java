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
    private List<Pemeriksaan> allFilteredData = new ArrayList<>();
    
    private final int DATA_PER_PAGE = 50;
    private int currentPage = 1;
    private int totalPages = 1;

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
        
        txtCari.textProperty().addListener((obs, oldVal, newVal) -> filterData());
        dpTanggal.valueProperty().addListener((obs, oldVal, newVal) -> filterData());
        loadData();
    }

    private void filterData() {
        allFilteredData.clear();
        String keyword = txtCari.getText() != null ? txtCari.getText().toLowerCase() : "";
        LocalDate date = dpTanggal.getValue();
        
        for (Pemeriksaan p : service.getAll()) {
            boolean matchName = p.getPasien() != null && p.getPasien().getNama().toLowerCase().contains(keyword);
            boolean matchDate = (date == null) || (p.getTanggalPeriksa() != null && p.getTanggalPeriksa().equals(date));
            
            if (matchName && matchDate) {
                allFilteredData.add(p);
            }
        }
        
        totalPages = (int) Math.ceil((double) allFilteredData.size() / DATA_PER_PAGE);
        if (totalPages == 0) totalPages = 1;
        currentPage = 1;
        
        updateTablePage();
    }
    
    private void updateTablePage() {
        lblHalaman.setText("Halaman " + currentPage + " / " + totalPages);
        
        boolean showPrev = currentPage > 1;
        btnPrev.setVisible(showPrev);
        btnPrev.setManaged(showPrev);
        
        boolean showNext = currentPage < totalPages;
        btnNext.setVisible(showNext);
        btnNext.setManaged(showNext);
        
        int fromIndex = (currentPage - 1) * DATA_PER_PAGE;
        int toIndex = Math.min(fromIndex + DATA_PER_PAGE, allFilteredData.size());
        
        list.clear();
        if (fromIndex < allFilteredData.size()) {
            list.addAll(allFilteredData.subList(fromIndex, toIndex));
        }
        tableRiwayat.setItems(list);
    }
    
    @FXML public void handlePrevPage() {
        if (currentPage > 1) {
            currentPage--;
            updateTablePage();
        }
    }
    
    @FXML public void handleNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updateTablePage();
        }
    }
    
    @FXML public void handleReset() {
        txtCari.clear();
        dpTanggal.setValue(null);
    }
    
    @FXML public void loadData() { filterData(); }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableRiwayat.getScene().getWindow(), "/view/dashboard.fxml");
    }
}