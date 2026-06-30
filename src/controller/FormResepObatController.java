package controller;

import javafx.fxml.FXML;
import controller.DashboardController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Obat;
import model.RekamMedis;
import model.ResepObat;
import service.ObatService;
import service.ResepObatService;
import util.AlertUtil;
import util.ValidationUtil;

public class FormResepObatController {

    @FXML private ComboBox<Obat> cbObat;
    @FXML private TextField txtJumlah;
    @FXML private TextField txtAturanPakai;

    private RekamMedis rekamMedis;
    private ResepObatService resepService = new ResepObatService();
    private ObatService obatService = new ObatService();

    public void setRekamMedis(RekamMedis rm) {
        this.rekamMedis = rm;
        loadObat();
    }

    private void loadObat() {
        cbObat.getItems().clear();
        cbObat.getItems().addAll(obatService.getAll());
    }

    @FXML
    private void handleSimpan() {
        if (cbObat.getValue() == null) {
            AlertUtil.warning("Pilih obat terlebih dahulu!");
            return;
        }
        if (ValidationUtil.isEmpty(txtJumlah.getText()) || !ValidationUtil.isNumeric(txtJumlah.getText())) {
            AlertUtil.warning("Jumlah harus diisi dengan angka!");
            return;
        }
        if (ValidationUtil.isEmpty(txtAturanPakai.getText())) {
            AlertUtil.warning("Aturan pakai harus diisi!");
            return;
        }

        int jumlah = Integer.parseInt(txtJumlah.getText().trim());
        if (jumlah <= 0) {
            AlertUtil.warning("Jumlah harus lebih dari 0!");
            return;
        }

        Obat obatDipilih = cbObat.getValue();
        if (jumlah > obatDipilih.getStok()) {
            AlertUtil.warning("Stok obat tidak mencukupi! Stok tersedia: " + obatDipilih.getStok());
            return;
        }

        ResepObat resep = new ResepObat();
        resep.setRekamMedis(rekamMedis);
        resep.setObat(obatDipilih);
        resep.setJumlah(jumlah);
        resep.setAturanPakai(txtAturanPakai.getText().trim());

        if (resepService.save(resep)) {
            AlertUtil.success("Resep obat berhasil disimpan!");
            DashboardController.getInstance().setCenterContent("/view/resep_obat.fxml");
        } else {
            AlertUtil.error("Gagal menyimpan resep! Periksa stok obat.");
        }
    }

    @FXML
    private void handleBatal() {
        DashboardController.getInstance().setCenterContent("/view/resep_obat.fxml");
    }
}
