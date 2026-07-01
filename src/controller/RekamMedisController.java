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
import model.RekamMedis;
import service.RekamMedisService;
import util.AlertUtil;
import controller.DashboardController;
import util.SceneUtil;
import controller.DashboardController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import java.io.File;
import util.DocumentUtil;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RekamMedisController implements Initializable {
    @FXML private TextField txtSearch;
    @FXML private Button btnPrev, btnNext;
    @FXML private Label lblHalaman;
    
    @FXML private javafx.scene.layout.HBox hboxTombol;
    @FXML private Button btnTambah, btnEdit, btnCetak;
    
    @FXML private TableView<RekamMedis> tableRekamMedis;
    @FXML private TableColumn<RekamMedis, Integer> colId;
    @FXML private TableColumn<RekamMedis, String> colPasien, colDokter, colTanggal, colDiagnosis, colTindakan;

    private RekamMedisService service = new RekamMedisService();
    ObservableList<RekamMedis> list = FXCollections.observableArrayList();
    
    private int currentPage = 1;
    private final int PAGE_SIZE = 50;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idRekamMedis"));
        colPasien.setCellValueFactory(new PropertyValueFactory<>("pasien"));
        colDokter.setCellValueFactory(new PropertyValueFactory<>("dokter"));
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPeriksa"));
        colDiagnosis.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        colTindakan.setCellValueFactory(new PropertyValueFactory<>("tindakan"));
        
        if (txtSearch != null) {
            txtSearch.textProperty().addListener((obs, oldV, newV) -> {
                currentPage = 1;
                loadData();
            });
        }
        
        loadData();
    }

    @FXML public void loadData() {
        String keyword = txtSearch.getText();
        int offset = (currentPage - 1) * PAGE_SIZE;
        
        list.clear();
        list.addAll(service.getAllPaginated(PAGE_SIZE, offset, keyword));
        tableRekamMedis.setItems(list);
        
        int totalData = service.getTotal(keyword);
        int totalPages = (int) Math.ceil((double) totalData / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;
        
        lblHalaman.setText("Halaman " + currentPage + " / " + totalPages);
        
        boolean showPrev = currentPage > 1;
        btnPrev.setVisible(showPrev);
        btnPrev.setManaged(showPrev);
        
        boolean showNext = currentPage < totalPages;
        btnNext.setVisible(showNext);
        btnNext.setManaged(showNext);
    }
    
    @FXML public void handlePrevPage() {
        if (currentPage > 1) {
            currentPage--;
            loadData();
        }
    }
    
    @FXML public void handleNextPage() {
        currentPage++;
        loadData();
    }

    @FXML public void handleTambah() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_rekam_medis.fxml"));
            javafx.scene.Node node = loader.load();
            FormRekamMedisController ctrl = loader.getController();
            ctrl.setModeTambah();
            DashboardController.getInstance().setCenterContent(node);
        } catch (Exception e) { AlertUtil.error("Gagal buka form"); }
    }

    @FXML public void handleInputResep() {
        RekamMedis selected = tableRekamMedis.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.warning("Pilih rekam medis terlebih dahulu!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/resep_obat.fxml"));
            Parent root = loader.load();
            ResepObatController ctrl = loader.getController();
            ctrl.setRekamMedis(selected);
            DashboardController.getInstance().setCenterContent(root);
        } catch (Exception e) {
            AlertUtil.error("Gagal membuka halaman resep: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML public void handleCetakSurat() {
        RekamMedis selected = tableRekamMedis.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.warning("Pilih rekam medis yang akan dicetak!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Simpan Surat Sakit");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("Surat_Sakit_" + selected.getPasien().getNama().replace(" ", "_") + ".pdf");
        
        File file = fileChooser.showSaveDialog(tableRekamMedis.getScene().getWindow());
        
        if (file != null) {
            if (DocumentUtil.exportSuratSakit(selected, file.getAbsolutePath())) {
                AlertUtil.success("Berhasil mencetak surat sakit ke PDF!");
            } else {
                AlertUtil.error("Gagal mencetak PDF. Pastikan file tidak sedang dibuka program lain.");
            }
        }
    }

    @FXML public void handleBack() {
        SceneUtil.switchScene((Stage) tableRekamMedis.getScene().getWindow(), "/view/dashboard.fxml");
    }
}