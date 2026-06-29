package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.Pasien;

public class PasienDAO {

    // 1. Ambil Semua Data
    public List<Pasien> getAll() {
        List<Pasien> list = new ArrayList<>();
        String sql = "SELECT * FROM pasien ORDER BY id ASC";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Pasien(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getInt("umur"),
                    rs.getString("gender"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getDouble("gula_darah"),
                    rs.getDouble("tekanan_darah")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2. Fitur Pencarian (Untuk Dashboard)
    public List<Pasien> search(String keyword) {
        List<Pasien> list = new ArrayList<>();
        // Mencari berdasarkan Nama, Alamat, atau No HP
        String sql = "SELECT * FROM pasien WHERE nama LIKE ? OR alamat LIKE ? OR no_hp LIKE ? ORDER BY id ASC";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + keyword + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Pasien(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getInt("umur"),
                    rs.getString("gender"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getDouble("gula_darah"),
                    rs.getDouble("tekanan_darah")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Tambah Data Baru
    public boolean insert(Pasien p) {
        String sql = "INSERT INTO pasien (nama, umur, gender, no_hp, alamat, gula_darah, tekanan_darah) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setInt(2, p.getUmur());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getNoHP());
            ps.setString(5, p.getAlamat());
            ps.setDouble(6, p.getGulaDarah());
            ps.setDouble(7, p.getTekananDarah());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Update Data
    public boolean update(Pasien p) {
        String sql = "UPDATE pasien SET nama=?, umur=?, gender=?, no_hp=?, alamat=?, gula_darah=?, tekanan_darah=? WHERE id=?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNama());
            ps.setInt(2, p.getUmur());
            ps.setString(3, p.getGender());
            ps.setString(4, p.getNoHP());
            ps.setString(5, p.getAlamat());
            ps.setDouble(6, p.getGulaDarah());
            ps.setDouble(7, p.getTekananDarah());
            ps.setInt(8, p.getIdPasien());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Hapus Data
    public boolean delete(int id) {
        String sql = "DELETE FROM pasien WHERE id=?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Hitung Total Data (Untuk Dashboard)
    public int getTotal() {
        String sql = "SELECT COUNT(*) as total FROM pasien";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}