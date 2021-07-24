package id.ac.polman.astra.kelompok2.financialrecords.model;

public class SignUpModel {
    private String email;
    private String password;
    private String rePassword;
    private String alamat;
    private String nama;

    public SignUpModel(String nama, String email, String password, String rePassword, String alamat) {
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
        this.alamat = alamat;
        this.nama = nama;
    }

    public SignUpModel(){
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

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

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
