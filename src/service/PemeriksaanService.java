package service;
import java.util.List;
import dao.PemeriksaanDAO;
import model.Pemeriksaan;

public class PemeriksaanService {
    private PemeriksaanDAO dao = new PemeriksaanDAO();
    public List<Pemeriksaan> getAll() { return dao.getAll(); }
    public List<Pemeriksaan> getByPasien(int id) { return dao.getByPasien(id); }
    public boolean save(Pemeriksaan p) { 
        boolean result = dao.insert(p);
        if (result && p.getPasien() != null) {
            new PendaftaranService().updateStatusByPasien(p.getPasien().getIdPasien(), "Menunggu", "Diperiksa");
        }
        return result;
    }
    public boolean delete(int id) { return dao.delete(id); }
    public List<Pemeriksaan> getAllPaginated(int limit, int offset, String keyword, java.time.LocalDate date) { return dao.getAllPaginated(limit, offset, keyword, date); }
    public int getTotal(String keyword, java.time.LocalDate date) { return dao.getTotal(keyword, date); }
}