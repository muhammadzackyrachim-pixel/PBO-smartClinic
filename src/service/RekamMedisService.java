package service;

import java.util.List;

import dao.RekamMedisDAO;
import model.RekamMedis;

public class RekamMedisService {
    private RekamMedisDAO dao = new RekamMedisDAO();

    public List<RekamMedis> getAll() { return dao.getAll(); }
    public List<RekamMedis> getAllPaginated(int limit, int offset, String keyword) { return dao.getAllPaginated(limit, offset, keyword); }
    public boolean save(RekamMedis rm) { 
        boolean result = dao.insert(rm);
        if (result && rm.getPasien() != null) {
            new PendaftaranService().updateStatusByPasien(rm.getPasien().getIdPasien(), "Diperiksa", "Selesai");
        }
        return result;
    }
    public int getTotal() { return dao.getTotal(); }
    public int getTotal(String keyword) { return dao.getTotal(keyword); }
}