package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.Date;

public class DashboardModel {
    private int jenis_kategori;
    private String kategori;
    private int jumlah;
    private Date tanggal;

    public DashboardModel(int jenis_kategori, String kategori, int jumlah, Date tanggal) {
        this.jenis_kategori = jenis_kategori;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    public DashboardModel(){}

    public int getJenis_kategori() {
        return jenis_kategori;
    }

    public void setJenis_kategori(int jenis_kategori) {
        this.jenis_kategori = jenis_kategori;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}
