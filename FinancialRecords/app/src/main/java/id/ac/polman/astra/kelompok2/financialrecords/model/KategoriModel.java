package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.List;

public class KategoriModel {

    private String namakategori;
    private String key;

    public KategoriModel(){

    }

    public KategoriModel(String namakategori, String key){
        this.namakategori = namakategori;
        this.key = key;
    }

    public String getNamakategori() {
        return namakategori;
    }

    public void setNamakategori(String namakategori) {
        this.namakategori = namakategori;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

