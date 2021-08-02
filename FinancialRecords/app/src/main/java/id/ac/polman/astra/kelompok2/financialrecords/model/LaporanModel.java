package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.Date;
import java.util.List;

public class LaporanModel {

    private int jeniskategori;
    private String jumlah;
    private String kategori;
    private String keterangan;
    private Date tanggal;

    public LaporanModel(int jeniskategori, String jumlah, String kategori, String keterangan, Date tanggal) {
        this.jeniskategori = jeniskategori;
        this.jumlah = jumlah;
        this.kategori = kategori;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }

    public LaporanModel(){

    }
}

