package id.ac.polman.astra.kelompok2.financialrecords.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.Entity.UserEntity;
import id.ac.polman.astra.kelompok2.financialrecords.model.AccountModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.DashboardModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.ResponseModel;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Preference;

public class DashboardListViewModel extends ViewModel {
    private final String TAG = DashboardListViewModel.class.getSimpleName();
    private List<DashboardModel> mDashboardModels;
    private FirebaseAuth firebaseAuth;

    public DashboardListViewModel() {}

    public List<DashboardModel> getDashboardModels(){
        if (mDashboardModels == null)
        {
            mDashboardModels = new ArrayList<>();
            loadDashboardModels();
        }
        Log.e(TAG, "getDashboardModels: "+ mDashboardModels.size());
        return mDashboardModels;
    }

    private void loadDashboardModels(){
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
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    DashboardModel dashboardModel = new DashboardModel();
                                    dashboardModel.setJenis_kategori(document.getLong("jenis_kategori").intValue());
                                    Log.e("Login", "jenis kategori :" + dashboardModel.getJenis_kategori());
                                    dashboardModel.setJumlah((document.getLong("jumlah").intValue()));
                                    Log.e("Login", "jumlah :" + dashboardModel.getJumlah());
                                    dashboardModel.setKategori(document.getString("kategori"));
                                    Log.e("Login", "kategori :" + dashboardModel.getKategori());
                                    dashboardModel.setTanggal(document.getDate("tanggal"));
                                    Log.e("Login", "tanggal :" + dashboardModel.getTanggal());
                                    mDashboardModels.add(dashboardModel);
                                    Log.e(TAG, "onComplete: " + mDashboardModels.size() );
                                }
                            }
                            else
                                Log.e(TAG, "Error getting document ", task.getException() );
                        }
                    });
        }
    }
//    public LiveData<ResponseModel> getDashboardList(Context context, AccountModel accountModel){
//        MutableLiveData<ResponseModel> accountLiveData = new MutableLiveData<>();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Preference preference = new Preference(context);
//        UserEntity userEntity = preference.getUser();
//
//        if (!accountModel.getNama().isEmpty()){
//            Map<String, Object> account = new HashMap<>();
//            account.put("")
//        }
//    }
}
