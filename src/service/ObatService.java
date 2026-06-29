package service;

import java.util.List;

import dao.ObatDAO;
import model.Obat;

public class ObatService {
    private ObatDAO dao = new ObatDAO();

    public List<Obat> getAll() { return dao.getAll(); }
    public boolean save(Obat o) { return (o.getIdObat() == 0) ? dao.insert(o) : dao.update(o); }
    public boolean delete(int id) { return dao.delete(id); }
    public int getTotal() { return dao.getTotal(); }
}