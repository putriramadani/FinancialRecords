package id.ac.polman.astra.kelompok2.financialrecords.Entity;

import java.util.List;

public class UserEntity {
    private String email;
    private String password;
    private String nama;
    private String alamat;
    private List<String> pemasukan;
    private List<String> pengeluaran;
    private int saldo;

    public UserEntity(String email, String password, String nama, String alamat, List<String> pemasukan, List<String> pengeluaran, int saldo) {
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.alamat = alamat;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
        this.saldo = saldo;
    }

    public UserEntity(){};

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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
