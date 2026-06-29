package service;

import java.util.List;

import dao.PrediksiDAO;
import model.Prediksi;

public class PrediksiService {
    private PrediksiDAO dao = new PrediksiDAO();

    public List<Prediksi> getAll() { return dao.getAll(); }
    public boolean save(Prediksi p) { return dao.insert(p); }
    public int getTotal() { return dao.getTotal(); }
    public Prediksi getLatestByPasienId(int pasienId) { return dao.getLatestByPasienId(pasienId); }
}