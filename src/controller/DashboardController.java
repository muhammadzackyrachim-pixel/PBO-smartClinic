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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Pasien;
import service.DokterService;
import service.ObatService;
import service.PasienService;
import service.PendaftaranService;
import service.PoliService;
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
    
    // Cards & Labels
    @FXML private VBox cardPasien, cardDokter, cardObat, cardPendaftaran, cardRekamMedis, cardPrediksi;
    @FXML private Label lblTitlePasien, lblTitleDokter, lblTitleObat, lblTitlePendaftaran, lblTitleRekamMedis, lblTitlePrediksi;

    @FXML private TableView<model.Pendaftaran> tableDashboard;
    @FXML private TableColumn<model.Pendaftaran, Integer> colId;
    @FXML private TableColumn<model.Pendaftaran, String> colNama;
    @FXML private TableColumn<model.Pendaftaran, String> colWaktu;
    @FXML private TableColumn<model.Pendaftaran, String> colPoli;
    @FXML private TableColumn<model.Pendaftaran, String> colKeluhan;
    @FXML private TableColumn<model.Pendaftaran, String> colStatus;
    @FXML private javafx.scene.layout.BorderPane mainPane;
    @FXML private javafx.scene.control.ScrollPane sidebarContainer;
    @FXML private Label lblCurrentUser;
    @FXML private Button btnDashboard, btnPasien, btnDokter, btnPetugas, btnObat, btnPoli;
    @FXML private Button btnPendaftaran, btnPemeriksaan, btnRiwayatPemeriksaan, btnRekamMedis, btnPrediksi;
    @FXML private Button btnHasilRiwayat, btnLaporan;
    @FXML private Label lblMasterData, lblTransaksi, lblLaporan;

    private javafx.scene.Node originalCenter;

    private PasienService pasienService = new PasienService();
    private DokterService dokterService = new DokterService();
    private ObatService obatService = new ObatService();
    private PendaftaranService pendaftaranService = new PendaftaranService();
    private RekamMedisService rekamMedisService = new RekamMedisService();
    private PrediksiService prediksiService = new PrediksiService();

    private static DashboardController instance;

    public DashboardController() {
        instance = this;
    }

    public static DashboardController getInstance() {
        return instance;
    }

    public void setCenterContent(javafx.scene.Node content) {
        if (mainPane != null) {
            txtCariPasien.setVisible(false);
            txtCariPasien.setManaged(false);
            mainPane.setCenter(content);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupRBAC();

        colId.setCellValueFactory(new PropertyValueFactory<>("idPendaftaran"));
        colNama.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPasien() != null ? cellData.getValue().getPasien().getNama() : ""));
        colWaktu.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getWaktuDaftar() != null ? cellData.getValue().getWaktuDaftar().toString() : ""));
        colPoli.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDokter() != null ? cellData.getValue().getDokter().getNama() : ""));
        colKeluhan.setCellValueFactory(new PropertyValueFactory<>("keluhan"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableDashboard.setEditable(false);
        
        if (txtCariPasien != null) {
            txtCariPasien.textProperty().addListener((obs, oldV, newV) -> {
                loadData();
            });
        }
        
        loadData();
        
        setupCardListeners();
        
        // Simpan konten asli bagian tengah (Dashboard)
        javafx.application.Platform.runLater(() -> {
            if (mainPane != null) {
                originalCenter = mainPane.getCenter();
            }
        });
    }

    private void setupRBAC() {
        model.Petugas user = util.SessionUtil.getCurrentUser();
        if (user != null) {
            lblCurrentUser.setText("Login sebagai: " + user.getNama() + " (" + user.getRole() + ")");
            
            String role = user.getRole().toLowerCase();
            
            // Default: sembunyikan semua
            lblMasterData.setManaged(false); lblMasterData.setVisible(false);
            btnPasien.setManaged(false); btnPasien.setVisible(false);
            btnDokter.setManaged(false); btnDokter.setVisible(false);
            btnPetugas.setManaged(false); btnPetugas.setVisible(false);
            btnObat.setManaged(false); btnObat.setVisible(false);
            btnPoli.setManaged(false); btnPoli.setVisible(false);
            
            lblTransaksi.setManaged(false); lblTransaksi.setVisible(false);
            btnPendaftaran.setManaged(false); btnPendaftaran.setVisible(false);
            btnPemeriksaan.setManaged(false); btnPemeriksaan.setVisible(false);
            btnRiwayatPemeriksaan.setManaged(false); btnRiwayatPemeriksaan.setVisible(false);
            btnRekamMedis.setManaged(false); btnRekamMedis.setVisible(false);
            btnPrediksi.setManaged(false); btnPrediksi.setVisible(false);
            
            lblLaporan.setManaged(false); lblLaporan.setVisible(false);
            btnHasilRiwayat.setManaged(false); btnHasilRiwayat.setVisible(false);
            btnLaporan.setManaged(false); btnLaporan.setVisible(false);
            
            if (role.contains("admin")) {
                lblMasterData.setManaged(true); lblMasterData.setVisible(true);
                btnPasien.setManaged(true); btnPasien.setVisible(true);
                btnDokter.setManaged(true); btnDokter.setVisible(true);
                btnPetugas.setManaged(true); btnPetugas.setVisible(true);
                btnObat.setManaged(true); btnObat.setVisible(true);
                btnPoli.setManaged(true); btnPoli.setVisible(true);
                
                lblTransaksi.setManaged(true); lblTransaksi.setVisible(true);
                btnPendaftaran.setManaged(true); btnPendaftaran.setVisible(true);
                btnPemeriksaan.setManaged(true); btnPemeriksaan.setVisible(true);
                btnRiwayatPemeriksaan.setManaged(true); btnRiwayatPemeriksaan.setVisible(true);
                btnRekamMedis.setManaged(true); btnRekamMedis.setVisible(true);
                btnPrediksi.setManaged(true); btnPrediksi.setVisible(true);
                
                lblLaporan.setManaged(true); lblLaporan.setVisible(true);
                btnHasilRiwayat.setManaged(true); btnHasilRiwayat.setVisible(true);
                btnLaporan.setManaged(true); btnLaporan.setVisible(true);
            } else if (role.contains("petugas") || role.contains("perawat") || role.contains("staff")) {
                lblMasterData.setManaged(true); lblMasterData.setVisible(true);
                btnPasien.setManaged(true); btnPasien.setVisible(true);
                
                lblTransaksi.setManaged(true); lblTransaksi.setVisible(true);
                btnPendaftaran.setManaged(true); btnPendaftaran.setVisible(true);
                btnPemeriksaan.setManaged(true); btnPemeriksaan.setVisible(true);
                btnRiwayatPemeriksaan.setManaged(true); btnRiwayatPemeriksaan.setVisible(true);
            } else if (role.contains("dokter")) {
                lblMasterData.setManaged(true); lblMasterData.setVisible(true);
                btnPasien.setManaged(true); btnPasien.setVisible(true);
                btnObat.setManaged(true); btnObat.setVisible(true); // Untuk mengecek ketersediaan obat
                
                lblTransaksi.setManaged(true); lblTransaksi.setVisible(true);
                btnPemeriksaan.setManaged(true); btnPemeriksaan.setVisible(true);
                btnRiwayatPemeriksaan.setManaged(true); btnRiwayatPemeriksaan.setVisible(true);
                btnRekamMedis.setManaged(true); btnRekamMedis.setVisible(true);
                btnPrediksi.setManaged(true); btnPrediksi.setVisible(true);
            }
        }
    }

    private void setupCardListeners() {
        final String origPasien = "Total Pasien";
        final String origDokter = "Total Dokter";
        final String origObat = "Total Obat";
        final String origPendaftaran = "Pendaftaran";
        final String origRekamMedis = "Rekam Medis";
        final String origPrediksi = "Prediksi";
        
        // Retrieve icons dynamically from FXML to avoid hardcoding emojis in Java which breaks cp1252 compilation
        final String iconPasien = lblTitlePasien.getText().split(" ")[0];
        final String iconDokter = lblTitleDokter.getText().split(" ")[0];
        final String iconObat = lblTitleObat.getText().split(" ")[0];
        final String iconPendaftaran = lblTitlePendaftaran.getText().split(" ")[0];
        final String iconRekamMedis = lblTitleRekamMedis.getText().split(" ")[0];
        final String iconPrediksi = lblTitlePrediksi.getText().split(" ")[0];

        // Mencegah JavaFX menambahkan titik tiga (...) ketika area mengecil
        lblTitlePasien.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        lblTitleDokter.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        lblTitleObat.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        lblTitlePendaftaran.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        lblTitleRekamMedis.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        lblTitlePrediksi.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);

        cardPasien.widthProperty().addListener((obs, oldVal, newVal) -> {
            lblTitlePasien.setText(newVal.doubleValue() < 120 ? iconPasien : iconPasien + " " + origPasien);
        });
        cardDokter.widthProperty().addListener((obs, oldVal, newVal) -> {
            lblTitleDokter.setText(newVal.doubleValue() < 120 ? iconDokter : iconDokter + " " + origDokter);
        });
        cardObat.widthProperty().addListener((obs, oldVal, newVal) -> {
            lblTitleObat.setText(newVal.doubleValue() < 120 ? iconObat : iconObat + " " + origObat);
        });
        cardPendaftaran.widthProperty().addListener((obs, oldVal, newVal) -> {
            lblTitlePendaftaran.setText(newVal.doubleValue() < 120 ? iconPendaftaran : iconPendaftaran + " " + origPendaftaran);
        });
        cardRekamMedis.widthProperty().addListener((obs, oldVal, newVal) -> {
            lblTitleRekamMedis.setText(newVal.doubleValue() < 120 ? iconRekamMedis : iconRekamMedis + " " + origRekamMedis);
        });
        cardPrediksi.widthProperty().addListener((obs, oldVal, newVal) -> {
            lblTitlePrediksi.setText(newVal.doubleValue() < 120 ? iconPrediksi : iconPrediksi + " " + origPrediksi);
        });
    }

    /* Kolom aksi dihapus dari Dashboard sesuai saran */

    @FXML public void handleCariPasien() {
        // Sudah digantikan oleh Live Search di textProperty listener
        loadData();
    }

    public void loadData() {
        lblTotalPasien.setText(String.valueOf(pasienService.getTotal()));
        lblTotalDokter.setText(String.valueOf(dokterService.getTotal()));
        lblTotalObat.setText(String.valueOf(obatService.getTotal()));
        lblTotalPendaftaran.setText(String.valueOf(pendaftaranService.getTotal()));
        lblTotalRekamMedis.setText(String.valueOf(rekamMedisService.getTotal()));
        lblTotalPrediksi.setText(String.valueOf(prediksiService.getTotal()));

        // Ambil data antrean hari ini
        List<model.Pendaftaran> semua = pendaftaranService.getAll();
        java.time.LocalDate hariIni = java.time.LocalDate.now();
        String keyword = txtCariPasien != null ? txtCariPasien.getText().toLowerCase() : "";
        
        List<model.Pendaftaran> hariIniList = semua.stream()
            .filter(p -> p.getTanggalDaftar() != null && p.getTanggalDaftar().equals(hariIni))
            .filter(p -> p.getStatus() != null && !p.getStatus().equalsIgnoreCase("Selesai") && !p.getStatus().equalsIgnoreCase("Batal"))
            .filter(p -> p.getPasien() != null && p.getPasien().getNama().toLowerCase().contains(keyword))
            .collect(java.util.stream.Collectors.toList());
            
        javafx.collections.ObservableList<model.Pendaftaran> obsList = javafx.collections.FXCollections.observableArrayList(hariIniList);
        tableDashboard.setItems(obsList);
    }

    // ============================================
    // SEMUA METHOD NAVIGASI (LENGKAP)
    // ============================================
    
    public void setCenterContent(String fxml) {
        try {
            txtCariPasien.setVisible(false);
            txtCariPasien.setManaged(false);
            javafx.scene.Parent root = FXMLLoader.load(getClass().getResource(fxml));
            mainPane.setCenter(root);
        } catch (java.io.IOException e) {
            AlertUtil.error("Gagal memuat halaman: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setActiveMenu(javafx.event.ActionEvent event) {
        if (sidebarContainer.getContent() instanceof javafx.scene.layout.VBox) {
            javafx.scene.layout.VBox sidebarVBox = (javafx.scene.layout.VBox) sidebarContainer.getContent();
            for (javafx.scene.Node node : sidebarVBox.getChildren()) {
                if (node instanceof javafx.scene.control.Button) {
                    node.getStyleClass().remove("active-menu-btn");
                }
            }
        }
        if (event != null && event.getSource() instanceof javafx.scene.control.Button) {
            javafx.scene.control.Button clickedBtn = (javafx.scene.control.Button) event.getSource();
            if (!clickedBtn.getStyleClass().contains("active-menu-btn")) {
                clickedBtn.getStyleClass().add("active-menu-btn");
            }
        }
    }

    @FXML private void openDashboard(javafx.event.ActionEvent event) { setActiveMenu(event);
        if (originalCenter != null) {
            txtCariPasien.setVisible(true);
            txtCariPasien.setManaged(true);
            mainPane.setCenter(originalCenter);
            loadData();
        }
    }

    @FXML
    private void toggleSidebar() {
        if (mainPane.getLeft() != null) {
            mainPane.setLeft(null);
        } else {
            mainPane.setLeft(sidebarContainer);
        }
    }

    @FXML private void openPasien(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/pasien.fxml");
    }

    @FXML private void openDokter(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/dokter.fxml");
    }

    @FXML private void openObat(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/obat.fxml");
    }

    @FXML private void openPetugas(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/petugas.fxml");
    }

    @FXML private void openPendaftaran(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/pendaftaran.fxml");
    }

    @FXML private void openRekamMedis(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/rekam_medis.fxml");
    }

    @FXML private void openPrediksi(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/prediksi.fxml");
    }

    @FXML private void openHasilRiwayat(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/hasil_riwayat.fxml");
    }

    @FXML private void openLaporan(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/laporan.fxml");
    }

    @FXML private void openPoli(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/poli.fxml");
    }

    @FXML
    private void handleLogout() {
        if (AlertUtil.confirm("Apakah Anda yakin ingin logout?")) {
            Stage stage = getStage();
            stage.setTitle("Smart Clinic - Login");
            SceneUtil.switchScene(stage, "/view/login.fxml");
        }
    }

    private Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }
    @FXML private void openPemeriksaan(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/pemeriksaan.fxml");
    }
    @FXML private void openRiwayatPemeriksaan(javafx.event.ActionEvent event) { setActiveMenu(event);
        setCenterContent("/view/riwayat_pemeriksaan.fxml");
    }
}