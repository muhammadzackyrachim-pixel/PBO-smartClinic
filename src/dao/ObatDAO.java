package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import model.Obat;

public class ObatDAO {

    public List<Obat> getAll() {
        List<Obat> list = new ArrayList<>();
        String sql = "SELECT * FROM obat ORDER BY id ASC";
        try (Connection conn = DBConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Obat(
                    rs.getInt("id"),
                    rs.getString("nama_obat"),
                    rs.getString("jenis"),
                    rs.getString("dosis"),
                    rs.getInt("stok"),
                    rs.getDouble("harga"),
                    rs.getString("keterangan")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Obat o) {
        String sql = "INSERT INTO obat (nama_obat, jenis, dosis, stok, harga, keterangan) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getNamaObat());
            ps.setString(2, o.getJenis());
            ps.setString(3, o.getDosis());
            ps.setInt(4, o.getStok());
            ps.setDouble(5, o.getHarga());
            ps.setString(6, o.getKeterangan());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Obat o) {
        String sql = "UPDATE obat SET nama_obat=?, jenis=?, dosis=?, stok=?, harga=?, keterangan=? WHERE id=?";
        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getNamaObat());
            ps.setString(2, o.getJenis());
            ps.setString(3, o.getDosis());
            ps.setInt(4, o.getStok());
            ps.setDouble(5, o.getHarga());
            ps.setString(6, o.getKeterangan());
            ps.setInt(7, o.getIdObat());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM obat WHERE id=?";
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
        String sql = "SELECT COUNT(*) as total FROM obat";
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