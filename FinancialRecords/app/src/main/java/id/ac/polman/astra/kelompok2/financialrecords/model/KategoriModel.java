package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.List;

public class KategoriModel {

    private String namakategori;
    private String jenis;
    private int key;
    private String pemasukan;
    //private String pengeluaran;

    public KategoriModel(){

    }

    public KategoriModel(String namakategori, String jenis, int key){
        this.namakategori = namakategori;
        this.jenis = jenis;
        this.key = key;
    }

    public KategoriModel(String pemasukan, int key){
        this.pemasukan = pemasukan;
        this.key = key;
    }

    public String getNamakategori() {
        return namakategori;
    }

    public void setNamakategori(String namakategori) {
        this.namakategori = namakategori;
    }


    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(String pemasukan) {
        this.pemasukan = pemasukan;
    }

//    public String getPengeluaran() {
//        return pengeluaran;
//    }
//
//    public void setPengeluaran(String pengeluaran) {
//        this.pengeluaran = pengeluaran;
//    }


}

