package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.Date;
import java.util.List;

public class LaporanModel {

    private int jenis_kategori;
    private int jumlah;
    private String kategori;
    private String keterangan;
    private Date tanggal;
    private int total_pemasukan;
    private int total_pengeluaran;
    private int selisih;

    public LaporanModel(int jenis_kategori, int jumlah, String kategori, String keterangan, Date tanggal) {
        this.jenis_kategori = jenis_kategori;
        this.jumlah = jumlah;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public LaporanModel(int total_pemasukan, int total_pengeluaran, int selisih) {
        this.total_pemasukan = total_pemasukan;
        this.total_pengeluaran = total_pengeluaran;
        this.selisih = selisih;
    }

    public LaporanModel(){

    }

    public LaporanModel(int listjumlah) {
        this.total_pemasukan = jumlah;
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

    public int getTotal_pemasukan() {
        return total_pemasukan;
    }

    public void setTotal_pemasukan(int total_pemasukan) {
        this.total_pemasukan = total_pemasukan;
    }

    public int getTotal_pengeluaran() {
        return total_pengeluaran;
    }

    public void setTotal_pengeluaran(int total_pengeluaran) {
        this.total_pengeluaran = total_pengeluaran;
    }

    public int getSelisih() {
        return selisih;
    }

    public void setSelisih(int selisih) {
        this.selisih = selisih;
    }

}

