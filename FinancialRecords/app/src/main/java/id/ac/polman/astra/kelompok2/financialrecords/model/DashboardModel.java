package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.Date;

public class DashboardModel {
    private int jenis_kategori;
    private String kategori;
    private int jumlah;
    private Date tanggal;
    private int pemasukan;
    private int pengeluaran;
    private int saldo;
    private String keterangan;

    public DashboardModel(int jenis_kategori, String kategori, int jumlah, Date tanggal, int pemasukan, int pengeluaran, int saldo, String keterangan) {
        this.jenis_kategori = jenis_kategori;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
        this.saldo = saldo;
        this.keterangan = keterangan;
    }

    public DashboardModel(){}


    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

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

    public int getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(int pemasukan) {
        this.pemasukan = pemasukan;
    }

    public int getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(int pengeluaran) {
        this.pengeluaran = pengeluaran;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
