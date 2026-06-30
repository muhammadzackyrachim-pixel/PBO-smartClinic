package service;

import java.util.List;

import dao.ResepObatDAO;
import model.ResepObat;

public class ResepObatService {
    private ResepObatDAO dao = new ResepObatDAO();

    public List<ResepObat> getByRekamMedisId(int rekamMedisId) { return dao.getByRekamMedisId(rekamMedisId); }
    public boolean save(ResepObat r) { return dao.insert(r); }
    public boolean delete(int id) { return dao.delete(id); }
}
