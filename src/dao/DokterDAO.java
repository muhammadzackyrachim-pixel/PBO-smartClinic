package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.Dokter;

public class DokterDAO {

    public List<Dokter> getAll() {
        List<Dokter> list = new ArrayList<>();
        String sql = "SELECT * FROM dokter ORDER BY id ASC";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Dokter(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("spesialisasi"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Dokter d) {
        String sql = "INSERT INTO dokter (nama, spesialisasi, no_hp, alamat, email) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNama());
            ps.setString(2, d.getSpesialisasi());
            ps.setString(3, d.getNoHP());
            ps.setString(4, d.getAlamat());
            ps.setString(5, d.getEmail());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Dokter d) {
        String sql = "UPDATE dokter SET nama=?, spesialisasi=?, no_hp=?, alamat=?, email=? WHERE id=?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getNama());
            ps.setString(2, d.getSpesialisasi());
            ps.setString(3, d.getNoHP());
            ps.setString(4, d.getAlamat());
            ps.setString(5, d.getEmail());
            ps.setInt(6, d.getIdDokter());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM dokter WHERE id=?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotal() {
        String sql = "SELECT COUNT(*) as total FROM dokter";
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