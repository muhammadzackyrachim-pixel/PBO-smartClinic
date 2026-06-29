package service;

import java.util.List;

import dao.DokterDAO;
import model.Dokter;

public class DokterService {
    private DokterDAO dao = new DokterDAO();

    public List<Dokter> getAll() { return dao.getAll(); }
    public boolean save(Dokter d) { return (d.getIdDokter() == 0) ? dao.insert(d) : dao.update(d); }
    public boolean delete(int id) { return dao.delete(id); }
    public int getTotal() { return dao.getTotal(); }
}