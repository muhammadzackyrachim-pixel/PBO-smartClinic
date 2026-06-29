package service;
import java.util.List;
import dao.PemeriksaanDAO;
import model.Pemeriksaan;

public class PemeriksaanService {
    private PemeriksaanDAO dao = new PemeriksaanDAO();
    public List<Pemeriksaan> getAll() { return dao.getAll(); }
    public List<Pemeriksaan> getByPasien(int id) { return dao.getByPasien(id); }
    public boolean save(Pemeriksaan p) { return dao.insert(p); }
    public boolean delete(int id) { return dao.delete(id); }
}