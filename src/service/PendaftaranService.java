package service;

import java.util.List;

import dao.PendaftaranDAO;
import model.Pendaftaran;

public class PendaftaranService {
    private PendaftaranDAO dao = new PendaftaranDAO();

    public List<Pendaftaran> getAll() { return dao.getAll(); }
    public boolean save(Pendaftaran p) { return dao.insert(p); }
    public boolean updateStatus(int id, String status) { return dao.updateStatus(id, status); }
    public boolean updateStatusByPasien(int pasienId, String currentStatus, String newStatus) { return dao.updateStatusByPasien(pasienId, currentStatus, newStatus); }
    public int getTotal() { return dao.getTotal(); }
}