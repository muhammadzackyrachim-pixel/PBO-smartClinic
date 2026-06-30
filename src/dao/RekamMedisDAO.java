package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.Dokter;
import model.Pasien;
import model.RekamMedis;

public class RekamMedisDAO {

    public List<RekamMedis> getAll() {
        List<RekamMedis> list = new ArrayList<>();
        String sql = "SELECT rm.*, pas.nama as nama_pasien, pas.umur as umur_pasien, pas.gender as gender_pasien, pas.alamat as alamat_pasien, dok.nama as nama_dokter, dok.spesialisasi as spesialisasi_dokter " +
                     "FROM rekam_medis rm " +
                     "LEFT JOIN pasien pas ON rm.pasien_id = pas.id " +
                     "LEFT JOIN dokter dok ON rm.dokter_id = dok.id " +
                     "ORDER BY rm.id ASC";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                RekamMedis rm = new RekamMedis();
                rm.setIdRekamMedis(rs.getInt("id"));
                rm.setTanggalPeriksa(rs.getDate("tanggal_periksa") != null ? rs.getDate("tanggal_periksa").toLocalDate() : null);
                rm.setDiagnosis(rs.getString("diagnosis"));
                rm.setTindakan(rs.getString("tindakan"));
                rm.setCatatan(rs.getString("catatan"));
                
                Pasien pasien = new Pasien();
                pasien.setIdPasien(rs.getInt("pasien_id"));
                pasien.setNama(rs.getString("nama_pasien"));
                pasien.setUmur(rs.getInt("umur_pasien"));
                pasien.setGender(rs.getString("gender_pasien"));
                pasien.setAlamat(rs.getString("alamat_pasien"));
                rm.setPasien(pasien);
                
                Dokter dokter = new Dokter();
                dokter.setIdDokter(rs.getInt("dokter_id"));
                dokter.setNama(rs.getString("nama_dokter"));
                dokter.setSpesialisasi(rs.getString("spesialisasi_dokter"));
                rm.setDokter(dokter);
                
                list.add(rm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<RekamMedis> getAllPaginated(int limit, int offset, String keyword) {
        List<RekamMedis> list = new ArrayList<>();
        String sql = "SELECT rm.*, pas.nama as nama_pasien, pas.umur as umur_pasien, pas.gender as gender_pasien, pas.alamat as alamat_pasien, dok.nama as nama_dokter, dok.spesialisasi as spesialisasi_dokter " +
                     "FROM rekam_medis rm " +
                     "LEFT JOIN pasien pas ON rm.pasien_id = pas.id " +
                     "LEFT JOIN dokter dok ON rm.dokter_id = dok.id " +
                     "WHERE pas.nama LIKE ? " +
                     "ORDER BY rm.id ASC LIMIT ? OFFSET ?";
                     
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, searchPattern);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RekamMedis rm = new RekamMedis();
                    rm.setIdRekamMedis(rs.getInt("id"));
                    rm.setTanggalPeriksa(rs.getDate("tanggal_periksa") != null ? rs.getDate("tanggal_periksa").toLocalDate() : null);
                    rm.setDiagnosis(rs.getString("diagnosis"));
                    rm.setTindakan(rs.getString("tindakan"));
                    rm.setCatatan(rs.getString("catatan"));
                    
                    Pasien pasien = new Pasien();
                    pasien.setIdPasien(rs.getInt("pasien_id"));
                    pasien.setNama(rs.getString("nama_pasien"));
                    pasien.setUmur(rs.getInt("umur_pasien"));
                    pasien.setGender(rs.getString("gender_pasien"));
                    pasien.setAlamat(rs.getString("alamat_pasien"));
                    rm.setPasien(pasien);
                    
                    Dokter dokter = new Dokter();
                    dokter.setIdDokter(rs.getInt("dokter_id"));
                    dokter.setNama(rs.getString("nama_dokter"));
                    dokter.setSpesialisasi(rs.getString("spesialisasi_dokter"));
                    rm.setDokter(dokter);
                    
                    list.add(rm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(RekamMedis rm) {
        String sql = "INSERT INTO rekam_medis (pendaftaran_id, pasien_id, dokter_id, tanggal_periksa, diagnosis, tindakan, catatan) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rm.getPendaftaran() != null ? rm.getPendaftaran().getIdPendaftaran() : 0);
            ps.setInt(2, rm.getPasien().getIdPasien());
            ps.setInt(3, rm.getDokter().getIdDokter());
            ps.setDate(4, Date.valueOf(rm.getTanggalPeriksa()));
            ps.setString(5, rm.getDiagnosis());
            ps.setString(6, rm.getTindakan());
            ps.setString(7, rm.getCatatan());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotal() {
        String sql = "SELECT COUNT(*) as total FROM rekam_medis";
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

    public int getTotal(String keyword) {
        String sql = "SELECT COUNT(*) as total FROM rekam_medis rm " +
                     "LEFT JOIN pasien pas ON rm.pasien_id = pas.id " +
                     "WHERE pas.nama LIKE ?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}