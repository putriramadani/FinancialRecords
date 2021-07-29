package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.List;

public class KategoriModel {

    private String namakategori;
    private String jenis;
    private String key;
    private String pemasukan;
    private List<String> pengeluaran;

    public KategoriModel(){

    }

    public KategoriModel(String namakategori, String jenis){
        this.namakategori = namakategori;
        this.jenis = jenis;
    }

    public KategoriModel(String pemasukan){
        this.pemasukan = pemasukan;
    }
//    public KategoriModel(List<String> pemasukan, List<String> pengeluaran){
//        this.pemasukan = pemasukan;
//        this.pengeluaran = pengeluaran;
//    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(String pemasukan) {
        this.pemasukan = pemasukan;
    }

    public List<String> getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(List<String> pengeluaran) {
        this.pengeluaran = pengeluaran;
    }


}

