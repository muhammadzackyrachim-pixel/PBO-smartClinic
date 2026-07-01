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
import model.Pendaftaran;
import service.PendaftaranService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;

public class PendaftaranController implements Initializable {
    @FXML private javafx.scene.layout.HBox hboxTombol;
    @FXML private javafx.scene.control.Button btnTambah;
    @FXML private TableView<Pendaftaran> tablePendaftaran;
    @FXML private TableColumn<Pendaftaran, Integer> colId;
    @FXML private TableColumn<Pendaftaran, String> colPasien, colDokter, colTanggal, colKeluhan, colStatus;
    @FXML private javafx.scene.control.TextField txtSearch;
    @FXML private javafx.scene.control.Button btnPrev, btnNext;
    @FXML private javafx.scene.control.Label lblHalaman;

    private PendaftaranService service = new PendaftaranService();
    ObservableList<Pendaftaran> list = FXCollections.observableArrayList();
    private int currentPage = 1;
    private final int PAGE_SIZE = 50;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPendaftaran"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colDokter.setCellValueFactory(new PropertyValueFactory<>("dokter"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalDaftar"));
        colKeluhan.setCellValueFactory(new PropertyValueFactory<>("keluhan"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        if (txtSearch != null) {
            txtSearch.textProperty().addListener((obs, oldV, newV) -> {
                currentPage = 1;
                loadData();
            });
        }
        loadData();
    }

    @FXML public void loadData() {
        String keyword = txtSearch != null ? txtSearch.getText() : "";
        int offset = (currentPage - 1) * PAGE_SIZE;
        
        list.clear();
        list.addAll(service.getAllPaginated(PAGE_SIZE, offset, keyword));
        tablePendaftaran.setItems(list);
        
        int totalData = service.getTotal(keyword);
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
        int totalData = service.getTotal(txtSearch != null ? txtSearch.getText() : "");
        int totalPages = (int) Math.ceil((double) totalData / PAGE_SIZE);
        if (currentPage < totalPages) {
            currentPage++;
            loadData();
        }
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_pendaftaran.fxml"));
            javafx.scene.Node node = loader.load();
            FormPendaftaranController ctrl = loader.getController();
            ctrl.setModeTambah();
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tablePendaftaran.getScene().getWindow(), "/view/dashboard.fxml");
    }
}