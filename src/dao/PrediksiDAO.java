package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.Pasien;
import model.Prediksi;

public class PrediksiDAO {

    public List<Prediksi> getAll() {
        List<Prediksi> list = new ArrayList<>();
        String sql = "SELECT pr.*, pas.nama as nama_pasien FROM prediksi pr " +
                     "LEFT JOIN pasien pas ON pr.pasien_id = pas.id ORDER BY pr.id DESC";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prediksi p = new Prediksi();
                p.setIdPrediksi(rs.getInt("id"));
                p.setHasil(rs.getString("hasil"));
                p.setTanggalPrediksi(rs.getDate("tanggal_prediksi") != null ? rs.getDate("tanggal_prediksi").toLocalDate() : null);
                
                Pasien pasien = new Pasien();
                pasien.setIdPasien(rs.getInt("pasien_id"));
                pasien.setNama(rs.getString("nama_pasien"));
                p.setPasien(pasien);
                
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Prediksi p) {
        String sql = "INSERT INTO prediksi (pasien_id, pregnancies, glucose, blood_pressure, skin_thickness, insulin, bmi, pedigree, age, hasil, tanggal_prediksi) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getPasien().getIdPasien());
            ps.setInt(2, p.getPregnancies());
            ps.setDouble(3, p.getGlucose());
            ps.setDouble(4, p.getBloodPressure());
            ps.setDouble(5, p.getSkinThickness());
            ps.setDouble(6, p.getInsulin());
            ps.setDouble(7, p.getBmi());
            ps.setDouble(8, p.getPedigree());
            ps.setInt(9, p.getAge());
            ps.setString(10, p.getHasil());
            ps.setDate(11, Date.valueOf(p.getTanggalPrediksi()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Prediksi getLatestByPasienId(int pasienId) {
        String sql = "SELECT * FROM prediksi WHERE pasien_id = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pasienId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prediksi p = new Prediksi();
                    p.setIdPrediksi(rs.getInt("id"));
                    p.setPregnancies(rs.getInt("pregnancies"));
                    p.setGlucose(rs.getDouble("glucose"));
                    p.setBloodPressure(rs.getDouble("blood_pressure"));
                    p.setSkinThickness(rs.getDouble("skin_thickness"));
                    p.setInsulin(rs.getDouble("insulin"));
                    p.setBmi(rs.getDouble("bmi"));
                    p.setPedigree(rs.getDouble("pedigree"));
                    p.setAge(rs.getInt("age"));
                    p.setHasil(rs.getString("hasil"));
                    p.setTanggalPrediksi(rs.getDate("tanggal_prediksi") != null ? rs.getDate("tanggal_prediksi").toLocalDate() : null);
                    return p;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotal() {
        String sql = "SELECT COUNT(*) as total FROM prediksi";
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