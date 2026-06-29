package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Pasien;
import service.DokterService;
import service.ObatService;
import service.PasienService;
import service.PendaftaranService;
import service.PrediksiService;
import service.RekamMedisService;
import util.AlertUtil;
import util.SceneUtil;

public class DashboardController implements Initializable {

    @FXML private Label lblTotalPasien;
    @FXML private Label lblTotalDokter;
    @FXML private Label lblTotalObat;
    @FXML private Label lblTotalPendaftaran;
    @FXML private Label lblTotalRekamMedis;
    @FXML private Label lblTotalPrediksi;
    @FXML private TextField txtCariPasien;
    @FXML private TableView<Pasien> tableDashboard;
    @FXML private TableColumn<Pasien, Integer> colId;
    @FXML private TableColumn<Pasien, String> colNama;
    @FXML private TableColumn<Pasien, Integer> colUmur;
    @FXML private TableColumn<Pasien, String> colGender;
    @FXML private TableColumn<Pasien, String> colAlamat;
    @FXML private TableColumn<Pasien, Void> colAksi;

    private PasienService pasienService = new PasienService();
    private DokterService dokterService = new DokterService();
    private ObatService obatService = new ObatService();
    private PendaftaranService pendaftaranService = new PendaftaranService();
    private RekamMedisService rekamMedisService = new RekamMedisService();
    private PrediksiService prediksiService = new PrediksiService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPasien"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colUmur.setCellValueFactory(new PropertyValueFactory<>("umur"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

        setupKolomAksi();
        tableDashboard.setEditable(true);
        loadData();
    }

    private void setupKolomAksi() {
        colAksi.setCellFactory(param -> new TableCell<Pasien, Void>() {
            private final Button btnEdit = new Button("✏️");
            private final Button btnDelete = new Button("🗑️");
            private final HBox pane = new HBox(5, btnEdit, btnDelete);

            {
                btnEdit.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
                btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
                btnEdit.setPadding(new Insets(5, 10, 5, 10));
                btnDelete.setPadding(new Insets(5, 10, 5, 10));

                btnEdit.setOnAction(event -> {
                    Pasien pasien = getTableView().getItems().get(getIndex());
                    handleEditPasien(pasien);
                });

                btnDelete.setOnAction(event -> {
                    Pasien pasien = getTableView().getItems().get(getIndex());
                    handleDeletePasien(pasien);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    @FXML
    private void handleCariPasien() {
        String keyword = txtCariPasien.getText().trim();
        
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        List<Pasien> hasilPencarian = pasienService.search(keyword);
        
        if (hasilPencarian.isEmpty()) {
            AlertUtil.warning("Data pasien dengan kata kunci '" + keyword + "' tidak ditemukan!");
            tableDashboard.getItems().clear();
        } else {
            tableDashboard.getItems().clear();
            tableDashboard.getItems().addAll(hasilPencarian);
        }
    }

    private void handleEditPasien(Pasien pasien) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/form_pasien.fxml"));
            Stage stage = SceneUtil.createModal(loader, "Edit Pasien", 800, 500);
            FormPasienController ctrl = loader.getController();
            ctrl.setModeEdit(pasien);
            stage.showAndWait();
            loadData();
        } catch (Exception e) {
            AlertUtil.error("Gagal membuka form edit: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleDeletePasien(Pasien pasien) {
        boolean confirm = AlertUtil.confirm("Yakin ingin menghapus pasien " + pasien.getNama() + "?");
        if (confirm) {
            pasienService.delete(pasien.getIdPasien());
            AlertUtil.success("Data berhasil dihapus");
            loadData();
        }
    }

    private void loadData() {
        txtCariPasien.clear();
        
        lblTotalPasien.setText(String.valueOf(pasienService.getTotal()));
        lblTotalDokter.setText(String.valueOf(dokterService.getTotal()));
        lblTotalObat.setText(String.valueOf(obatService.getTotal()));
        lblTotalPendaftaran.setText(String.valueOf(pendaftaranService.getTotal()));
        lblTotalRekamMedis.setText(String.valueOf(rekamMedisService.getTotal()));
        lblTotalPrediksi.setText(String.valueOf(prediksiService.getTotal()));

        List<Pasien> list = pasienService.getAll();
        tableDashboard.getItems().clear();
        if (!list.isEmpty()) {
            tableDashboard.getItems().addAll(list.subList(0, Math.min(5, list.size())));
        }
    }

    // ============================================
    // SEMUA METHOD NAVIGASI (LENGKAP)
    // ============================================
    
    @FXML
    private void openPasien() {
        SceneUtil.switchScene(getStage(), "/view/pasien.fxml");
    }

    @FXML
    private void openDokter() {
        SceneUtil.switchScene(getStage(), "/view/dokter.fxml");
    }

    @FXML
    private void openObat() {
        SceneUtil.switchScene(getStage(), "/view/obat.fxml");
    }

    @FXML
    private void openPetugas() {
        SceneUtil.switchScene(getStage(), "/view/petugas.fxml");
    }

    @FXML
    private void openPendaftaran() {
        SceneUtil.switchScene(getStage(), "/view/pendaftaran.fxml");
    }

    @FXML
    private void openRekamMedis() {  // ✅ METHOD INI SEKARANG ADA
        SceneUtil.switchScene(getStage(), "/view/rekam_medis.fxml");
    }

    @FXML
    private void openPrediksi() {
        SceneUtil.switchScene(getStage(), "/view/prediksi.fxml");
    }

    @FXML
    private void openHasilRiwayat() {
        SceneUtil.switchScene(getStage(), "/view/hasil_riwayat.fxml");
    }

    @FXML
    private void openLaporan() {
        SceneUtil.switchScene(getStage(), "/view/laporan.fxml");
    }

    private Stage getStage() {
        return (Stage) tableDashboard.getScene().getWindow();
    }
}