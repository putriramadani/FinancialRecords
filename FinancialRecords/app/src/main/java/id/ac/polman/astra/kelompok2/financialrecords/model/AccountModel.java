package id.ac.polman.astra.kelompok2.financialrecords.model;

import java.util.List;

public class AccountModel {
    private String email;
    private String nama;
    private List<String> pemasukan;
    private List<String> pengeluaran;
    private int saldo;

    public AccountModel(String email, String nama, List<String> pemasukan, List<String> pengeluaran, int saldo) {
        this.email = email;
        this.nama = nama;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
        this.saldo = saldo;
    }

    public AccountModel() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<String> getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(List<String> pemasukan) {
        this.pemasukan = pemasukan;
    }

    public List<String> getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(List<String> pengeluaran) {
        this.pengeluaran = pengeluaran;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
