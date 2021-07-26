package id.ac.polman.astra.kelompok2.financialrecords.model;

public class KategoriModel {

    private String namakategori;
    private String key;

    public KategoriModel(){

    }

    public KategoriModel(String namakategori){
        this.namakategori = namakategori;
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

