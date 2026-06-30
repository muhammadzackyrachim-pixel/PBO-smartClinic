package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import model.Pasien;
import service.DokterService;
import service.ObatService;
import service.PasienService;
import service.PendaftaranService;
import service.PrediksiService;
import service.RekamMedisService;
import util.SceneUtil;

public class LaporanController implements Initializable {

    @FXML private BarChart<String, Number> barChartTotal;
    @FXML private PieChart pieChartGender;
    @FXML private BarChart<String, Number> barChartPendaftaran;

    private PasienService pasienService = new PasienService();
    private DokterService dokterService = new DokterService();
    private ObatService obatService = new ObatService();
    private PendaftaranService pendaftaranService = new PendaftaranService();
    private RekamMedisService rekamMedisService = new RekamMedisService();
    private PrediksiService prediksiService = new PrediksiService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupBarChartTotal();
        setupPieChartGender();
        setupBarChartPendaftaran();
    }

    // ============================================
    // GRAFIK 1: TOTAL DATA PER MODUL (BAR CHART)
    // ============================================
    private void setupBarChartTotal() {
        barChartTotal.setTitle("Total Data Per Modul");
        
        // ❌ HAPUS: barChartTotal.setXAxis(...) dan barChartTotal.setYAxis(...)
        // Axis sudah di-set dari FXML, cukup ambil dan set labelnya saja
        
        CategoryAxis xAxis = (CategoryAxis) barChartTotal.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChartTotal.getYAxis();
        xAxis.setLabel("Modul");
        yAxis.setLabel("Jumlah");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Data");
        
        series.getData().add(new XYChart.Data<>("Pasien", pasienService.getTotal()));
        series.getData().add(new XYChart.Data<>("Dokter", dokterService.getTotal()));
        series.getData().add(new XYChart.Data<>("Obat", obatService.getTotal()));
        series.getData().add(new XYChart.Data<>("Pendaftaran", pendaftaranService.getTotal()));
        series.getData().add(new XYChart.Data<>("Rekam Medis", rekamMedisService.getTotal()));
        series.getData().add(new XYChart.Data<>("Prediksi", prediksiService.getTotal()));
        
        barChartTotal.getData().add(series);
    }

    // ============================================
    // GRAFIK 2: DISTRIBUSI GENDER PASIEN (PIE CHART)
    // ============================================
    private void setupPieChartGender() {
        if (pieChartGender.getTitle() == null || pieChartGender.getTitle().isEmpty()) {
            pieChartGender.setTitle("Distribusi Gender Pasien");
        }
        
        List<Pasien> allPasien = pasienService.getAll();
        int lakiLaki = 0;
        int perempuan = 0;
        
        for (Pasien p : allPasien) {
            if ("Laki-laki".equals(p.getGender())) {
                lakiLaki++;
            } else if ("Perempuan".equals(p.getGender())) {
                perempuan++;
            }
        }

        if (pieChartGender.getData().isEmpty()) {
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Laki-laki (" + lakiLaki + ")", lakiLaki),
                new PieChart.Data("Perempuan (" + perempuan + ")", perempuan)
            );
            pieChartGender.setData(pieChartData);
            pieChartData.get(0).getNode().setStyle("-fx-pie-color: #3b82f6;");
            pieChartData.get(1).getNode().setStyle("-fx-pie-color: #ec4899;");
        } else {
            // Update existing data to prevent UI glitches with labels
            pieChartGender.getData().get(0).setName("Laki-laki (" + lakiLaki + ")");
            pieChartGender.getData().get(0).setPieValue(lakiLaki);
            pieChartGender.getData().get(1).setName("Perempuan (" + perempuan + ")");
            pieChartGender.getData().get(1).setPieValue(perempuan);
        }
    }

    // ============================================
    // GRAFIK 3: STATUS PENDAFTARAN (BAR CHART)
    // ============================================
    private void setupBarChartPendaftaran() {
        barChartPendaftaran.setTitle("Status Pendaftaran");
        
        // ❌ HAPUS: barChartPendaftaran.setXAxis(...) dan barChartPendaftaran.setYAxis(...)
        // Axis sudah di-set dari FXML
        
        CategoryAxis xAxis = (CategoryAxis) barChartPendaftaran.getXAxis();
        NumberAxis yAxis = (NumberAxis) barChartPendaftaran.getYAxis();
        xAxis.setLabel("Status");
        yAxis.setLabel("Jumlah");

        // Hitung status dari database
        List<model.Pendaftaran> allPendaftaran = pendaftaranService.getAll();
        int menunggu = 0, diperiksa = 0, selesai = 0, dibatalkan = 0;
        
        for (model.Pendaftaran p : allPendaftaran) {
            String status = p.getStatus();
            if (status == null) status = "Menunggu";
            
            switch (status.toLowerCase()) {
                case "menunggu": menunggu++; break;
                case "diperiksa": diperiksa++; break;
                case "selesai": selesai++; break;
                case "dibatalkan": dibatalkan++; break;
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Jumlah");
        
        series.getData().add(new XYChart.Data<>("Menunggu", menunggu));
        series.getData().add(new XYChart.Data<>("Diperiksa", diperiksa));
        series.getData().add(new XYChart.Data<>("Selesai", selesai));
        series.getData().add(new XYChart.Data<>("Dibatalkan", dibatalkan));
        
        barChartPendaftaran.getData().add(series);
    }

    @FXML
    public void handleBack() {
        Stage stage = (Stage) barChartTotal.getScene().getWindow();
        SceneUtil.switchScene(stage, "/view/dashboard.fxml");
    }
    @FXML
    public void handleRefresh() {
        // Hapus data bar chart lama
        barChartTotal.getData().clear();
        barChartPendaftaran.getData().clear();
        
        // PieChart tidak di-clear, melainkan di-update nilainya di dalam fungsinya
        
        // Load ulang chart dengan data terbaru
        setupBarChartTotal();
        setupPieChartGender();
        setupBarChartPendaftaran();
    }
}