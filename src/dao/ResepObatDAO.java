package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.Obat;
import model.RekamMedis;
import model.ResepObat;

public class ResepObatDAO {

    public List<ResepObat> getByRekamMedisId(int rekamMedisId) {
        List<ResepObat> list = new ArrayList<>();
        String sql = "SELECT r.*, o.nama_obat, o.jenis, o.dosis, o.stok, o.harga, o.keterangan " +
                     "FROM resep_obat r LEFT JOIN obat o ON r.obat_id = o.id " +
                     "WHERE r.rekam_medis_id = ? ORDER BY r.id ASC";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rekamMedisId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ResepObat resep = new ResepObat();
                resep.setIdResep(rs.getInt("id"));

                RekamMedis rm = new RekamMedis();
                rm.setIdRekamMedis(rekamMedisId);
                resep.setRekamMedis(rm);

                Obat obat = new Obat(
                    rs.getInt("obat_id"),
                    rs.getString("nama_obat"),
                    rs.getString("jenis"),
                    rs.getString("dosis"),
                    rs.getInt("stok"),
                    rs.getDouble("harga"),
                    rs.getString("keterangan")
                );
                resep.setObat(obat);
                resep.setJumlah(rs.getInt("jumlah"));
                resep.setAturanPakai(rs.getString("aturan_pakai"));
                list.add(resep);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(ResepObat r) {
        String sqlInsert = "INSERT INTO resep_obat (rekam_medis_id, obat_id, jumlah, aturan_pakai) VALUES (?,?,?,?)";
        String sqlUpdateStok = "UPDATE obat SET stok = stok - ? WHERE id = ? AND stok >= ?";
        Connection conn = null;
        try {
            conn = DBConnection.connect();
            conn.setAutoCommit(false);

            // Kurangi stok obat
            try (PreparedStatement psStok = conn.prepareStatement(sqlUpdateStok)) {
                psStok.setInt(1, r.getJumlah());
                psStok.setInt(2, r.getObat().getIdObat());
                psStok.setInt(3, r.getJumlah());
                int affected = psStok.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    return false; // Stok tidak cukup
                }
            }

            // Insert resep
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setInt(1, r.getRekamMedis().getIdRekamMedis());
                ps.setInt(2, r.getObat().getIdObat());
                ps.setInt(3, r.getJumlah());
                ps.setString(4, r.getAturanPakai());
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public boolean delete(int id) {
        // Kembalikan stok obat terlebih dahulu
        String sqlGetResep = "SELECT obat_id, jumlah FROM resep_obat WHERE id=?";
        String sqlReturnStok = "UPDATE obat SET stok = stok + ? WHERE id = ?";
        String sqlDelete = "DELETE FROM resep_obat WHERE id=?";
        Connection conn = null;
        try {
            conn = DBConnection.connect();
            conn.setAutoCommit(false);

            int obatId = 0, jumlah = 0;
            try (PreparedStatement ps = conn.prepareStatement(sqlGetResep)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    obatId = rs.getInt("obat_id");
                    jumlah = rs.getInt("jumlah");
                }
            }

            // Kembalikan stok
            try (PreparedStatement ps = conn.prepareStatement(sqlReturnStok)) {
                ps.setInt(1, jumlah);
                ps.setInt(2, obatId);
                ps.executeUpdate();
            }

            // Hapus resep
            try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
