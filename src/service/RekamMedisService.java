package service;

import java.util.List;

import dao.RekamMedisDAO;
import model.RekamMedis;

public class RekamMedisService {
    private RekamMedisDAO dao = new RekamMedisDAO();

    public List<RekamMedis> getAll() { return dao.getAll(); }
    public boolean save(RekamMedis rm) { return dao.insert(rm); }
    public int getTotal() { return dao.getTotal(); }
}