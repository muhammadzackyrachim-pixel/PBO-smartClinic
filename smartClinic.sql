CREATE DATABASE IF NOT EXISTS smart_clinic;
USE smart_clinic;

-- Tabel Pasien
CREATE TABLE IF NOT EXISTS pasien(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    umur INT,
    gender VARCHAR(20),
    no_hp VARCHAR(20),
    alamat TEXT,
    gula_darah DOUBLE,
    tekanan_darah DOUBLE
);

-- Tabel Dokter
CREATE TABLE IF NOT EXISTS dokter(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    spesialisasi VARCHAR(100),
    no_hp VARCHAR(20),
    alamat TEXT,
    email VARCHAR(100)
);

-- Tabel Obat
CREATE TABLE IF NOT EXISTS obat(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama_obat VARCHAR(100),
    jenis VARCHAR(50),
    dosis VARCHAR(100),
    stok INT DEFAULT 0,
    harga DOUBLE,
    keterangan TEXT
);

-- Tabel Petugas
CREATE TABLE IF NOT EXISTS petugas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    role VARCHAR(50),
    no_hp VARCHAR(20),
    email VARCHAR(100)
);

-- Tabel Pendaftaran
CREATE TABLE IF NOT EXISTS pendaftaran(
    id INT AUTO_INCREMENT PRIMARY KEY,
    pasien_id INT,
    dokter_id INT,
    tanggal_daftar DATE,
    waktu_daftar TIME,
    keluhan TEXT,
    status VARCHAR(50) DEFAULT 'Menunggu',
    FOREIGN KEY (pasien_id) REFERENCES pasien(id) ON DELETE CASCADE,
    FOREIGN KEY (dokter_id) REFERENCES dokter(id) ON DELETE SET NULL
);

-- Tabel Rekam Medis
CREATE TABLE IF NOT EXISTS rekam_medis(
    id INT AUTO_INCREMENT PRIMARY KEY,
    pendaftaran_id INT,
    pasien_id INT,
    dokter_id INT,
    tanggal_periksa DATE,
    diagnosis TEXT,
    tindakan TEXT,
    catatan TEXT,
    FOREIGN KEY (pendaftaran_id) REFERENCES pendaftaran(id) ON DELETE CASCADE,
    FOREIGN KEY (pasien_id) REFERENCES pasien(id) ON DELETE CASCADE,
    FOREIGN KEY (dokter_id) REFERENCES dokter(id) ON DELETE SET NULL
);

-- Tabel Prediksi
CREATE TABLE IF NOT EXISTS prediksi(
    id INT AUTO_INCREMENT PRIMARY KEY,
    pasien_id INT,
    pregnancies INT,
    glucose DOUBLE,
    blood_pressure DOUBLE,
    skin_thickness DOUBLE,
    insulin DOUBLE,
    bmi DOUBLE,
    pedigree DOUBLE,
    age INT,
    hasil VARCHAR(100),
    tanggal_prediksi DATE,
    FOREIGN KEY (pasien_id) REFERENCES pasien(id) ON DELETE CASCADE
);

-- Data Dummy
INSERT INTO pasien(nama, umur, gender, no_hp, alamat, gula_darah, tekanan_darah) VALUES 
('Ahmad', 23, 'Laki-laki', '08123456789', 'Jl. Merdeka No. 1', 90.0, 120.0),
('Siti', 20, 'Perempuan', '08123456790', 'Jl. Sudirman No. 2', 85.0, 110.0),
('Budi', 30, 'Laki-laki', '08123456791', 'Jl. Ahmad Yani No. 3', 95.0, 125.0);

INSERT INTO dokter(nama, spesialisasi, no_hp, alamat, email) VALUES 
('Dr. Andi', 'Umum', '08123456701', 'Jl. Kesehatan No. 1', 'andi@clinic.com'),
('Dr. Rina', 'Anak', '08123456702', 'Jl. Kesehatan No. 2', 'rina@clinic.com');

INSERT INTO obat(nama_obat, jenis, dosis, stok, harga, keterangan) VALUES 
('Paracetamol', 'Tablet', '500mg', 100, 5000, 'Pereda nyeri'),
('Amoxicillin', 'Kapsul', '500mg', 50, 10000, 'Antibiotik');

INSERT INTO petugas(nama, username, password, role, no_hp, email) VALUES 
('Admin Utama', 'admin', 'admin123', 'Admin', '08123456789', 'admin@clinic.com'),
('Perawat 1', 'perawat1', 'perawat123', 'Perawat', '08123456790', 'perawat1@clinic.com');