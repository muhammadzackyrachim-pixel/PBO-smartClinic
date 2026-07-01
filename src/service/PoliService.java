package service;

import java.util.List;

import dao.PoliDAO;
import model.Poli;

public class PoliService {
    private PoliDAO dao = new PoliDAO();

    public List<Poli> getAll() { return dao.getAll(); }
    public boolean save(Poli p) { return (p.getIdPoli() == 0) ? dao.insert(p) : dao.update(p); }
    public boolean delete(int id) { return dao.delete(id); }
    public int getTotal() { return dao.getTotal(); }
    public boolean isNamaPoliExists(String namaPoli, int excludeId) { return dao.isNamaPoliExists(namaPoli, excludeId); }
}
