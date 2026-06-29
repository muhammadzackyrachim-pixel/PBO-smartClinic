package service;

import java.util.List;

import dao.PasienDAO;
import model.Pasien;

public class PasienService {
    private PasienDAO dao = new PasienDAO();

    public List<Pasien> getAll() { return dao.getAll(); }
    public boolean save(Pasien p) { return (p.getIdPasien() == 0) ? dao.insert(p) : dao.update(p); }
    public boolean delete(int id) { return dao.delete(id); }
    public List<Pasien> search(String keyword) { return dao.search(keyword); }
    public int getTotal() { return dao.getTotal(); }
}