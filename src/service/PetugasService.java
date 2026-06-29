package service;

import java.util.List;

import dao.PetugasDAO;
import model.Petugas;

public class PetugasService {
    private PetugasDAO dao = new PetugasDAO();

    public List<Petugas> getAll() { return dao.getAll(); }
    public boolean save(Petugas p) { return (p.getIdPetugas() == 0) ? dao.insert(p) : dao.update(p); }
    public boolean delete(int id) { return dao.delete(id); }
    public int getTotal() { return dao.getTotal(); }
}