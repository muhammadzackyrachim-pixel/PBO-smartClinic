package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.DBConnection;
import model.*;

public class PemeriksaanDAO {
    public List<Pemeriksaan> getAll() {
        List<Pemeriksaan> list = new ArrayList<>();
        String sql = "SELECT p.*, pas.nama as nama_pasien, pas.tanggal_lahir as tanggal_lahir_pasien, pas.gender as gender_pasien, pas.alamat as alamat_pasien, dok.nama as nama_dokter FROM pemeriksaan p " +
                     "LEFT JOIN pasien pas ON p.pasien_id = pas.id LEFT JOIN dokter dok ON p.dokter_id = dok.id ORDER BY p.id ASC";
        try (Connection conn = DBConnection.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pemeriksaan p = new Pemeriksaan();
                p.setIdPemeriksaan(rs.getInt("id"));
                p.setTanggalPeriksa(rs.getDate("tanggal_periksa") != null ? rs.getDate("tanggal_periksa").toLocalDate() : null);
                p.setTekananDarah(rs.getString("tekanan_darah"));
                p.setSuhuTubuh(rs.getDouble("suhu_tubuh"));
                p.setBeratBadan(rs.getDouble("berat_badan"));
                p.setTinggiBadan(rs.getDouble("tinggi_badan"));
                p.setCatatan(rs.getString("catatan"));
                
                Pasien pasien = new Pasien(); 
                pasien.setIdPasien(rs.getInt("pasien_id")); 
                pasien.setNama(rs.getString("nama_pasien"));
                pasien.setTanggalLahir(rs.getDate("tanggal_lahir_pasien") != null ? rs.getDate("tanggal_lahir_pasien").toLocalDate() : null);
                pasien.setGender(rs.getString("gender_pasien"));
                pasien.setAlamat(rs.getString("alamat_pasien"));
                p.setPasien(pasien);
                Dokter dokter = new Dokter(); dokter.setIdDokter(rs.getInt("dokter_id")); dokter.setNama(rs.getString("nama_dokter"));
                p.setDokter(dokter);
                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Pemeriksaan> getByPasien(int pasienId) {
        List<Pemeriksaan> list = new ArrayList<>();
        String sql = "SELECT p.*, pas.nama as nama_pasien, pas.tanggal_lahir as tanggal_lahir_pasien, pas.gender as gender_pasien, pas.alamat as alamat_pasien, dok.nama as nama_dokter FROM pemeriksaan p " +
                     "LEFT JOIN pasien pas ON p.pasien_id = pas.id LEFT JOIN dokter dok ON p.dokter_id = dok.id " +
                     "WHERE p.pasien_id = ? ORDER BY p.tanggal_periksa ASC";
        try (Connection conn = DBConnection.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pasienId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pemeriksaan p = new Pemeriksaan();
                p.setIdPemeriksaan(rs.getInt("id"));
                p.setTanggalPeriksa(rs.getDate("tanggal_periksa").toLocalDate());
                p.setTekananDarah(rs.getString("tekanan_darah"));
                p.setSuhuTubuh(rs.getDouble("suhu_tubuh"));
                p.setBeratBadan(rs.getDouble("berat_badan"));
                p.setTinggiBadan(rs.getDouble("tinggi_badan"));
                p.setCatatan(rs.getString("catatan"));
                
                Pasien pasien = new Pasien(); 
                pasien.setIdPasien(rs.getInt("pasien_id")); 
                pasien.setNama(rs.getString("nama_pasien"));
                pasien.setTanggalLahir(rs.getDate("tanggal_lahir_pasien") != null ? rs.getDate("tanggal_lahir_pasien").toLocalDate() : null);
                pasien.setGender(rs.getString("gender_pasien"));
                pasien.setAlamat(rs.getString("alamat_pasien"));
                p.setPasien(pasien);
                Dokter dokter = new Dokter(); dokter.setIdDokter(rs.getInt("dokter_id")); dokter.setNama(rs.getString("nama_dokter"));
                p.setDokter(dokter);
                
                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Pemeriksaan p) {
        String sql = "INSERT INTO pemeriksaan (pasien_id, dokter_id, tanggal_periksa, tekanan_darah, suhu_tubuh, berat_badan, tinggi_badan, catatan) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getPasien().getIdPasien());
            ps.setInt(2, p.getDokter().getIdDokter());
            ps.setDate(3, Date.valueOf(p.getTanggalPeriksa()));
            ps.setString(4, p.getTekananDarah());
            ps.setDouble(5, p.getSuhuTubuh());
            ps.setDouble(6, p.getBeratBadan());
            ps.setDouble(7, p.getTinggiBadan());
            ps.setString(8, p.getCatatan());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM pemeriksaan WHERE id=?";
        try (Connection conn = DBConnection.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Pemeriksaan> getAllPaginated(int limit, int offset, String keyword, java.time.LocalDate date) {
        List<Pemeriksaan> list = new ArrayList<>();
        String sql = "SELECT p.*, pas.nama as nama_pasien, pas.tanggal_lahir as tanggal_lahir_pasien, pas.gender as gender_pasien, pas.alamat as alamat_pasien, dok.nama as nama_dokter FROM pemeriksaan p " +
                     "LEFT JOIN pasien pas ON p.pasien_id = pas.id LEFT JOIN dokter dok ON p.dokter_id = dok.id " +
                     "WHERE pas.nama LIKE ? ";
        if (date != null) {
            sql += "AND p.tanggal_periksa = ? ";
        }
        sql += "ORDER BY p.id ASC LIMIT ? OFFSET ?";
        
        try (Connection conn = DBConnection.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            int paramIndex = 2;
            if (date != null) {
                ps.setDate(paramIndex++, Date.valueOf(date));
            }
            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pemeriksaan p = new Pemeriksaan();
                    p.setIdPemeriksaan(rs.getInt("id"));
                    p.setTanggalPeriksa(rs.getDate("tanggal_periksa") != null ? rs.getDate("tanggal_periksa").toLocalDate() : null);
                    p.setTekananDarah(rs.getString("tekanan_darah"));
                    p.setSuhuTubuh(rs.getDouble("suhu_tubuh"));
                    p.setBeratBadan(rs.getDouble("berat_badan"));
                    p.setTinggiBadan(rs.getDouble("tinggi_badan"));
                    p.setCatatan(rs.getString("catatan"));
                    
                    Pasien pasien = new Pasien(); 
                    pasien.setIdPasien(rs.getInt("pasien_id")); 
                    pasien.setNama(rs.getString("nama_pasien"));
                    pasien.setTanggalLahir(rs.getDate("tanggal_lahir_pasien") != null ? rs.getDate("tanggal_lahir_pasien").toLocalDate() : null);
                    pasien.setGender(rs.getString("gender_pasien"));
                    pasien.setAlamat(rs.getString("alamat_pasien"));
                    p.setPasien(pasien);
                    Dokter dokter = new Dokter(); dokter.setIdDokter(rs.getInt("dokter_id")); dokter.setNama(rs.getString("nama_dokter"));
                    p.setDokter(dokter);
                    list.add(p);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public int getTotal(String keyword, java.time.LocalDate date) {
        String sql = "SELECT COUNT(*) as total FROM pemeriksaan p " +
                     "LEFT JOIN pasien pas ON p.pasien_id = pas.id " +
                     "WHERE pas.nama LIKE ? ";
        if (date != null) {
            sql += "AND p.tanggal_periksa = ?";
        }
        try (Connection conn = DBConnection.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            if (date != null) {
                ps.setDate(2, Date.valueOf(date));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}
