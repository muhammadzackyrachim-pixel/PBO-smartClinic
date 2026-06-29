package model;

public class Petugas {
    private int idPetugas;
    private String nama;
    private String username;
    private String password;
    private String role;
    private String noHP;
    private String email;

    public Petugas() {}

    public Petugas(int idPetugas, String nama, String username, String password, 
                   String role, String noHP, String email) {
        this.idPetugas = idPetugas;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.role = role;
        this.noHP = noHP;
        this.email = email;
    }

    public int getIdPetugas() { return idPetugas; }
    public void setIdPetugas(int idPetugas) { this.idPetugas = idPetugas; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getNoHP() { return noHP; }
    public void setNoHP(String noHP) { this.noHP = noHP; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return nama + " (" + role + ")";
    }
}