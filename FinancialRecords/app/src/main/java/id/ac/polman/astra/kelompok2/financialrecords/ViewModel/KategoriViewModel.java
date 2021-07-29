package id.ac.polman.astra.kelompok2.financialrecords.ViewModel;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.Entity.UserEntity;
import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.ResponseModel;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Preference;

public class KategoriViewModel extends ViewModel {
    private final static String TAG = KategoriViewModel.class.getSimpleName();
    private FirebaseAuth firebaseAuth;

    public LiveData<ResponseModel> signUp(Activity activity, KategoriModel signUpModel) {
        MutableLiveData<ResponseModel> signUpLiveData = new MutableLiveData<>();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Log.e("Login", firebaseUser.getEmail() + "Kategori");

        if (signUpModel.getNamakategori().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Category is Empty"));
        else {
            Log.e("Running", "running");
            //FirebaseAuthHelper.signUp(activity, signUpModel.getEmail(), signUpModel.getPassword()).observe((LifecycleOwner) activity, responseModel -> {

            Log.e(TAG, "kategori: "+ "response" );
//                if (responseModel.isSuccess()) {
                    Log.e(TAG, "kategori: "+ "success" );
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    db.collection("user")
                    .document(firebaseUser.getEmail()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           DocumentSnapshot document = task.getResult();
                           Map<String, Object> user = new HashMap<>();

                           String pem = signUpModel.getNamakategori();
                           String jenis = signUpModel.getJenis();
                           Log.e(TAG, "JENISSSSS: "+ jenis + "nama: "+ pem );

                           List<String> pemasukan = new ArrayList<>();
                           List<String> pengeluaran = new ArrayList<>();

                           List<String> listpem = (List<String>) document.get("pemasukan");
                           for(int i=0;i<listpem.size();i++){
                               pemasukan.add(i,listpem.get(i));
                           }

                           List<String> listpen = (List<String>) document.get("pengeluaran");
                           for(int i=0;i<listpen.size();i++){
                               pengeluaran.add(i,listpen.get(i));
                           }

                           if(jenis.equals("Pemasukan")){
                               int indexpem = listpem.size();
                               Log.e(TAG, "indexpem: "+ indexpem +"size: "+listpem.get(0));
                               pemasukan.add(indexpem,pem);
                           }
                           else if(jenis.equals("Pengeluaran"))
                           {
                               int indexpem = listpen.size();
                               Log.e(TAG, "indexpem: "+ indexpem +"size: "+listpen.get(0));

                               pengeluaran.add(indexpem,pem);

                           }
                           user.put("pemasukan", pemasukan);
                           user.put("pengeluaran", pengeluaran);
                           db.collection("user").document(firebaseUser.getEmail())
                                   .update(user).addOnSuccessListener(doc -> {
                               new Preference(activity).setUser(new UserEntity(
                                       pemasukan,
                                       pengeluaran
                               ));
                               signUpLiveData.postValue(new ResponseModel(true, "Success"));
                           }).addOnFailureListener(e -> {
                               signUpLiveData.postValue(new ResponseModel(false, e.getLocalizedMessage()));
                               Log.e(TAG, "ERROR"+ e.getLocalizedMessage());
                           });
                       }
                   });

                //}
//                else
//                    signUpLiveData.postValue(responseModel);
            //});
        }
        return signUpLiveData;
    }

}
