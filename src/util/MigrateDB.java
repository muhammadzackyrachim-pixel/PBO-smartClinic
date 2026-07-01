package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MigrateDB {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_clinic", "root", "");
            Statement s = c.createStatement();
            System.out.println("Memulai migrasi database...");
            
            // 1. Tambah kolom tanggal_lahir
            try {
                s.execute("ALTER TABLE pasien ADD COLUMN tanggal_lahir DATE");
                System.out.println("- Kolom tanggal_lahir berhasil ditambahkan.");
            } catch (Exception e) {
                System.out.println("- Kolom tanggal_lahir mungkin sudah ada: " + e.getMessage());
            }
            
            // 2. Isi tanggal_lahir berdasarkan umur saat ini
            s.execute("UPDATE pasien SET tanggal_lahir = DATE_SUB(CURDATE(), INTERVAL umur YEAR) WHERE tanggal_lahir IS NULL");
            System.out.println("- Data umur berhasil dikonversi ke tanggal_lahir.");
            
            // 3. Hapus kolom umur
            try {
                s.execute("ALTER TABLE pasien DROP COLUMN umur");
                System.out.println("- Kolom umur berhasil dihapus.");
            } catch (Exception e) {
                System.out.println("- Kolom umur gagal dihapus atau sudah tidak ada: " + e.getMessage());
            }
            
            System.out.println("Migrasi sukses!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
