package id.ac.polman.astra.kelompok2.financialrecords.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import id.ac.polman.astra.kelompok2.financialrecords.model.LaporanModel;

public class LaporanViewModel extends ViewModel {
    private final String TAG = LaporanViewModel.class.getSimpleName();
    private List<LaporanModel> mLaporanModels;
    private FirebaseAuth firebaseAuth;

    public LaporanViewModel() {}

    public List<LaporanModel> getLaporanModels(){
        if (mLaporanModels == null)
        {
            mLaporanModels = new ArrayList<>();
            loadLaporanModels();
        }
        Log.e(TAG, "getDashboardModels: "+ mLaporanModels.size());
        return mLaporanModels;
    }

    private void loadLaporanModels(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Log.e("Login", firebaseUser.getEmail() + "Dashboard");

        if (!firebaseUser.getEmail().isEmpty())
        {
            db.collection("user").document(firebaseUser.getEmail())
                    .collection("laporan").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult())
                                {
                                    LaporanModel laporanModel = new LaporanModel();
                                    laporanModel.setJenis_kategori(document.getLong("jenis_kategori").intValue());
                                    Log.e("Login", "jenis kategori :" + laporanModel.getJenis_kategori());

                                    laporanModel.setJumlah((document.getLong("jumlah").intValue()));
                                    Log.e("Login", "jumlah :" + laporanModel.getJumlah());

                                    laporanModel.setKategori(document.getString("kategori"));
                                    Log.e("Login", "kategori :" + laporanModel.getKategori());

                                    laporanModel.setTanggal(document.getDate("tanggal"));
                                    Log.e("Login", "tanggal :" + laporanModel.getTanggal());

                                    mLaporanModels.add(laporanModel);
                                    Log.e(TAG, "onComplete: " + mLaporanModels.size() );
                                }
                            }
                            else
                                Log.e(TAG, "Error getting document ", task.getException() );
                        }
                    });
        }
    }

}
