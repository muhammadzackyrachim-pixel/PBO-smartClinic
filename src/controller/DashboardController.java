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
    @FXML private TableView<Pasien> tableDashboard;
    @FXML private TableColumn<Pasien, Integer> colId;
    @FXML private TableColumn<Pasien, String> colNama;
    @FXML private TableColumn<Pasien, Integer> colUmur;
    @FXML private TableColumn<Pasien, String> colGender;
    @FXML private TableColumn<Pasien, String> colAlamat;
    @FXML private TableColumn<Pasien, Void> colAksi;
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

        colId.setCellValueFactory(new PropertyValueFactory<>("idPasien"));
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colUmur.setCellValueFactory(new PropertyValueFactory<>("umur"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));

        setupKolomAksi();
        tableDashboard.setEditable(true);
        loadData();
        
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

    private void setupKolomAksi() {
        colAksi.setCellFactory(param -> new TableCell<Pasien, Void>() {
            private final Button btnEdit = new Button("Edit");
            private final Button btnDelete = new Button("Hapus");
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