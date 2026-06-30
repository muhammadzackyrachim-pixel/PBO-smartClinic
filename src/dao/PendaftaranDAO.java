package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.Dokter;
import model.Pasien;
import model.Pendaftaran;

public class PendaftaranDAO {

    public List<Pendaftaran> getAll() {
        List<Pendaftaran> list = new ArrayList<>();
        String sql = "SELECT p.*, pas.nama as nama_pasien, pas.umur as umur_pasien, pas.gender as gender_pasien, pas.alamat as alamat_pasien, dok.nama as nama_dokter " +
                     "FROM pendaftaran p " +
                     "LEFT JOIN pasien pas ON p.pasien_id = pas.id " +
                     "LEFT JOIN dokter dok ON p.dokter_id = dok.id " +
                     "ORDER BY p.id ASC";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pendaftaran p = new Pendaftaran();
                p.setIdPendaftaran(rs.getInt("id"));
                p.setTanggalDaftar(rs.getDate("tanggal_daftar") != null ? rs.getDate("tanggal_daftar").toLocalDate() : null);
                p.setWaktuDaftar(rs.getTime("waktu_daftar") != null ? rs.getTime("waktu_daftar").toLocalTime() : null);
                p.setKeluhan(rs.getString("keluhan"));
                p.setStatus(rs.getString("status"));
                
                Pasien pasien = new Pasien();
                pasien.setIdPasien(rs.getInt("pasien_id"));
                pasien.setNama(rs.getString("nama_pasien"));
                pasien.setUmur(rs.getInt("umur_pasien"));
                pasien.setGender(rs.getString("gender_pasien"));
                pasien.setAlamat(rs.getString("alamat_pasien"));
                p.setPasien(pasien);
                
                Dokter dokter = new Dokter();
                dokter.setIdDokter(rs.getInt("dokter_id"));
                dokter.setNama(rs.getString("nama_dokter"));
                p.setDokter(dokter);
                
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Pendaftaran p) {
        String sql = "INSERT INTO pendaftaran (pasien_id, dokter_id, tanggal_daftar, waktu_daftar, keluhan, status) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getPasien().getIdPasien());
            ps.setInt(2, p.getDokter().getIdDokter());
            ps.setDate(3, Date.valueOf(p.getTanggalDaftar()));
            ps.setTime(4, Time.valueOf(p.getWaktuDaftar()));
            ps.setString(5, p.getKeluhan());
            ps.setString(6, p.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE pendaftaran SET status=? WHERE id=?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusByPasien(int pasienId, String currentStatus, String newStatus) {
        String sql = "UPDATE pendaftaran SET status=? WHERE pasien_id=? AND status=?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, pasienId);
            ps.setString(3, currentStatus);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotal() {
        String sql = "SELECT COUNT(*) as total FROM pendaftaran";
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