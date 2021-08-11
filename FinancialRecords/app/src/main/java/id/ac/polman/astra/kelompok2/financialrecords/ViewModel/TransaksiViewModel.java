package id.ac.polman.astra.kelompok2.financialrecords.ViewModel;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.ResponseModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.TransaksiModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.DashboardTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Preference;

public class TransaksiViewModel extends ViewModel {
    private final static String TAG = TransaksiViewModel.class.getSimpleName();
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private DashboardTabFragment mDashboardTabFragment;

    public LiveData<ResponseModel> saveTransaksi(Activity activity, TransaksiModel transaksiModel){
        MutableLiveData<ResponseModel> saveTransaksiLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        if (transaksiModel.equals(null)){
            saveTransaksiLiveData.postValue(new ResponseModel(false, "Transaksi is Empty"));
        } else {
            if (transaksiModel.getKategori().isEmpty()){
                saveTransaksiLiveData.postValue(new ResponseModel(false, "Kategori is Empty"));
            } else if (transaksiModel.getJumlah() == 0) {
                saveTransaksiLiveData.postValue(new ResponseModel(false, "Jumlah cannot be 0"));
            } else if (transaksiModel.getJenis_kategori() == 0){
                saveTransaksiLiveData.postValue(new ResponseModel(false, "jenis kategori is empty"));
            } else if (transaksiModel.getKeterangan().isEmpty()) {
                saveTransaksiLiveData.postValue(new ResponseModel(false, "Keterangan is Empty"));
            } else {
                Map<String, Object> transaksi = new HashMap<>();
                transaksi.put("jenis_kategori", transaksiModel.getJenis_kategori());
                transaksi.put("jumlah", transaksiModel.getJumlah());
                transaksi.put("kategori", transaksiModel.getKategori());
                transaksi.put("keterangan", transaksiModel.getKeterangan());
                transaksi.put("tanggal", new Date());

                db.collection("user").document(firebaseUser.getEmail())
                        .collection("Laporan").add(transaksi).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        saveTransaksiLiveData.postValue(new ResponseModel(true, "Success"));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        saveTransaksiLiveData.postValue(new ResponseModel(true, e.getMessage()));
                    }
                });
            }
        }
        DashboardModel dashboardModel = mDashboardTabFragment.getDashboardModel();
        updateSaldo(transaksiModel.getJenis_kategori(), transaksiModel.getJumlah(), dashboardModel.getSaldo());
        return saveTransaksiLiveData;
    }

    public void updateSaldo(int jenis, int total, int saldoSekarang){
        int saldo;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        if (jenis == 1){
            saldo = saldoSekarang + total;
        } else {
            saldo = saldoSekarang - total;
        }

        db.collection("user").document(firebaseUser.getEmail())
                .update("saldo", saldo).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", e.getMessage());
            }
        });
    }
}
