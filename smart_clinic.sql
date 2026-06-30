-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2026 at 12:10 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smart_clinic`
--

-- --------------------------------------------------------

--
-- Table structure for table `dokter`
--

CREATE TABLE `dokter` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `spesialisasi` varchar(100) DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `poli_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dokter`
--

INSERT INTO `dokter` (`id`, `nama`, `spesialisasi`, `no_hp`, `alamat`, `email`, `poli_id`) VALUES
(1, 'Dr. Andi', 'Umum', '08123456701', 'Jl. Kesehatan No. 1', 'andi@clinic.com', NULL),
(2, 'Dr. Rina', 'Anak', '08123456702', 'Jl. Kesehatan No. 2', 'rina@clinic.com', NULL),
(3, 'Dr. Agung', 'Poli Gigi', '0518168458', NULL, 'agung@clinic.com', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `id` int(11) NOT NULL,
  `nama_obat` varchar(100) DEFAULT NULL,
  `jenis` varchar(50) DEFAULT NULL,
  `dosis` varchar(100) DEFAULT NULL,
  `stok` int(11) DEFAULT 0,
  `harga` double DEFAULT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `obat`
--

INSERT INTO `obat` (`id`, `nama_obat`, `jenis`, `dosis`, `stok`, `harga`, `keterangan`) VALUES
(1, 'Paracetamol', 'Tablet', '500mg', 98, 5000, 'Pereda nyeri'),
(2, 'Amoxicillin', 'Kapsul', '500mg', 50, 10000, 'Antibiotik');

-- --------------------------------------------------------

--
-- Table structure for table `pasien`
--

CREATE TABLE `pasien` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `umur` int(11) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `gula_darah` double DEFAULT NULL,
  `tekanan_darah` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pasien`
--

INSERT INTO `pasien` (`id`, `nama`, `umur`, `gender`, `no_hp`, `alamat`, `gula_darah`, `tekanan_darah`) VALUES
(1, 'Ahmad', 23, 'Laki-laki', '08123456789', 'Jl. Merdeka No. 1', 90, 120),
(2, 'Siti', 20, 'Perempuan', '08123456790', 'Jl. Sudirman No. 2', 85, 110),
(3, 'Budi', 30, 'Laki-laki', '08123456791', 'Jl. Ahmad Yani No. 3', 95, 125),
(4, 'Agung', 50, 'Laki-laki', '023164985246', 'Banyumanik', 200, 250);

-- --------------------------------------------------------

--
-- Table structure for table `pemeriksaan`
--

CREATE TABLE `pemeriksaan` (
  `id` int(11) NOT NULL,
  `pasien_id` int(11) DEFAULT NULL,
  `dokter_id` int(11) DEFAULT NULL,
  `tanggal_periksa` date DEFAULT NULL,
  `tekanan_darah` varchar(50) DEFAULT NULL,
  `suhu_tubuh` double DEFAULT NULL,
  `berat_badan` double DEFAULT NULL,
  `tinggi_badan` double DEFAULT NULL,
  `catatan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pemeriksaan`
--

INSERT INTO `pemeriksaan` (`id`, `pasien_id`, `dokter_id`, `tanggal_periksa`, `tekanan_darah`, `suhu_tubuh`, `berat_badan`, `tinggi_badan`, `catatan`) VALUES
(1, 2, 2, '2026-06-30', '120/80', 36.5, 65, 170, 'jangan makanan yang mengandung minyak kotor'),
(2, 4, 1, '2026-06-30', '200/200', 34, 80, 170, 'ada alergi obat');

-- --------------------------------------------------------

--
-- Table structure for table `pendaftaran`
--

CREATE TABLE `pendaftaran` (
  `id` int(11) NOT NULL,
  `pasien_id` int(11) DEFAULT NULL,
  `dokter_id` int(11) DEFAULT NULL,
  `tanggal_daftar` date DEFAULT NULL,
  `waktu_daftar` time DEFAULT NULL,
  `keluhan` text DEFAULT NULL,
  `status` varchar(50) DEFAULT 'Menunggu'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pendaftaran`
--

INSERT INTO `pendaftaran` (`id`, `pasien_id`, `dokter_id`, `tanggal_daftar`, `waktu_daftar`, `keluhan`, `status`) VALUES
(1, 1, 1, '2026-06-30', '09:45:00', 'tenggorokan sakit', 'Menunggu'),
(2, 4, 1, '2026-06-30', '16:41:00', 'ketika menelan tenggorokan terasa sakit', 'Selesai');

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE `petugas` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`id`, `nama`, `username`, `password`, `role`, `no_hp`, `email`) VALUES
(1, 'Admin Utama', 'admin', 'admin123', 'Admin', '08123456789', 'admin@clinic.com'),
(2, 'Petugas 1', 'petugas', 'petugas123', 'Petugas', '089522477778', 'petugas@clinic.com'),
(3, 'Dokter', 'dokter', 'dokter123', 'Dokter', '09523164445', 'dokter@clinic.com');

-- --------------------------------------------------------

--
-- Table structure for table `poli`
--

CREATE TABLE `poli` (
  `id` int(11) NOT NULL,
  `nama_poli` varchar(100) DEFAULT NULL,
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `poli`
--

INSERT INTO `poli` (`id`, `nama_poli`, `keterangan`) VALUES
(1, 'Poli Umum', 'Pelayanan kesehatan umum'),
(2, 'Poli Anak', 'Pelayanan kesehatan anak'),
(3, 'Poli Gigi', 'Pelayanan kesehatan gigi dan mulut');

-- --------------------------------------------------------

--
-- Table structure for table `prediksi`
--

CREATE TABLE `prediksi` (
  `id` int(11) NOT NULL,
  `pasien_id` int(11) DEFAULT NULL,
  `pregnancies` int(11) DEFAULT NULL,
  `glucose` double DEFAULT NULL,
  `blood_pressure` double DEFAULT NULL,
  `skin_thickness` double DEFAULT NULL,
  `insulin` double DEFAULT NULL,
  `bmi` double DEFAULT NULL,
  `pedigree` double DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `hasil` varchar(100) DEFAULT NULL,
  `tanggal_prediksi` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `prediksi`
--

INSERT INTO `prediksi` (`id`, `pasien_id`, `pregnancies`, `glucose`, `blood_pressure`, `skin_thickness`, `insulin`, `bmi`, `pedigree`, `age`, `hasil`, `tanggal_prediksi`) VALUES
(1, 1, 0, 90, 120, 32, 23, 90, 45, 23, 'RISIKO DIABETES RENDAH', '2026-06-30'),
(2, 4, 0, 200, 250, 34, 50, 80, 55, 50, 'RISIKO DIABETES TINGGI', '2026-06-30');

-- --------------------------------------------------------

--
-- Table structure for table `rekam_medis`
--

CREATE TABLE `rekam_medis` (
  `id` int(11) NOT NULL,
  `pendaftaran_id` int(11) DEFAULT NULL,
  `pasien_id` int(11) DEFAULT NULL,
  `dokter_id` int(11) DEFAULT NULL,
  `tanggal_periksa` date DEFAULT NULL,
  `diagnosis` text DEFAULT NULL,
  `tindakan` text DEFAULT NULL,
  `catatan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rekam_medis`
--

INSERT INTO `rekam_medis` (`id`, `pendaftaran_id`, `pasien_id`, `dokter_id`, `tanggal_periksa`, `diagnosis`, `tindakan`, `catatan`) VALUES
(1, 1, 1, 1, '2026-06-30', 'flu	', 'pemberian obat paracetamol', 'rawat jalan'),
(2, 2, 4, 1, '2026-06-30', 'radang tenggorokan', 'konsumsi obat pereda nyeri dan minum hangat', 'rawat jalan');

-- --------------------------------------------------------

--
-- Table structure for table `resep_obat`
--

CREATE TABLE `resep_obat` (
  `id` int(11) NOT NULL,
  `rekam_medis_id` int(11) DEFAULT NULL,
  `obat_id` int(11) DEFAULT NULL,
  `jumlah` int(11) DEFAULT NULL,
  `aturan_pakai` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resep_obat`
--

INSERT INTO `resep_obat` (`id`, `rekam_medis_id`, `obat_id`, `jumlah`, `aturan_pakai`) VALUES
(1, 1, 1, 2, '3x1 setelah makan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `dokter`
--
ALTER TABLE `dokter`
  ADD PRIMARY KEY (`id`),
  ADD KEY `poli_id` (`poli_id`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pasien`
--
ALTER TABLE `pasien`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pemeriksaan`
--
ALTER TABLE `pemeriksaan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pasien_id` (`pasien_id`),
  ADD KEY `dokter_id` (`dokter_id`);

--
-- Indexes for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pasien_id` (`pasien_id`),
  ADD KEY `dokter_id` (`dokter_id`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `poli`
--
ALTER TABLE `poli`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prediksi`
--
ALTER TABLE `prediksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pasien_id` (`pasien_id`);

--
-- Indexes for table `rekam_medis`
--
ALTER TABLE `rekam_medis`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pendaftaran_id` (`pendaftaran_id`),
  ADD KEY `pasien_id` (`pasien_id`),
  ADD KEY `dokter_id` (`dokter_id`);

--
-- Indexes for table `resep_obat`
--
ALTER TABLE `resep_obat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rekam_medis_id` (`rekam_medis_id`),
  ADD KEY `obat_id` (`obat_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dokter`
--
ALTER TABLE `dokter`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `obat`
--
ALTER TABLE `obat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pasien`
--
ALTER TABLE `pasien`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pemeriksaan`
--
ALTER TABLE `pemeriksaan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `petugas`
--
ALTER TABLE `petugas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `poli`
--
ALTER TABLE `poli`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `prediksi`
--
ALTER TABLE `prediksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `rekam_medis`
--
ALTER TABLE `rekam_medis`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `resep_obat`
--
ALTER TABLE `resep_obat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dokter`
--
ALTER TABLE `dokter`
  ADD CONSTRAINT `dokter_ibfk_1` FOREIGN KEY (`poli_id`) REFERENCES `poli` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `pemeriksaan`
--
ALTER TABLE `pemeriksaan`
  ADD CONSTRAINT `pemeriksaan_ibfk_1` FOREIGN KEY (`pasien_id`) REFERENCES `pasien` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `pemeriksaan_ibfk_2` FOREIGN KEY (`dokter_id`) REFERENCES `dokter` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD CONSTRAINT `pendaftaran_ibfk_1` FOREIGN KEY (`pasien_id`) REFERENCES `pasien` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `pendaftaran_ibfk_2` FOREIGN KEY (`dokter_id`) REFERENCES `dokter` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `prediksi`
--
ALTER TABLE `prediksi`
  ADD CONSTRAINT `prediksi_ibfk_1` FOREIGN KEY (`pasien_id`) REFERENCES `pasien` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `rekam_medis`
--
ALTER TABLE `rekam_medis`
  ADD CONSTRAINT `rekam_medis_ibfk_1` FOREIGN KEY (`pendaftaran_id`) REFERENCES `pendaftaran` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rekam_medis_ibfk_2` FOREIGN KEY (`pasien_id`) REFERENCES `pasien` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rekam_medis_ibfk_3` FOREIGN KEY (`dokter_id`) REFERENCES `dokter` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `resep_obat`
--
ALTER TABLE `resep_obat`
  ADD CONSTRAINT `resep_obat_ibfk_1` FOREIGN KEY (`rekam_medis_id`) REFERENCES `rekam_medis` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `resep_obat_ibfk_2` FOREIGN KEY (`obat_id`) REFERENCES `obat` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
