package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.Date;

public class TransaksiModel {
    private int jenis_kategori;
    private int jumlah;
    private String kategori;
    private String keterangan;
    private Date tanggal;

    public TransaksiModel(int jenis_kategori, int jumlah, String kategori, String keterangan, Date tanggal) {
        this.jenis_kategori = jenis_kategori;
        this.jumlah = jumlah;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public int getJenis_kategori() {
        return jenis_kategori;
    }

    public void setJenis_kategori(int jenis_kategori) {
        this.jenis_kategori = jenis_kategori;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}
