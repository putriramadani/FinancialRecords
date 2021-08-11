package id.ac.polman.astra.kelompok2.financialrecords.ViewModel;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.Entity.UserEntity;
import id.ac.polman.astra.kelompok2.financialrecords.model.ResponseModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.SignUpModel;
import id.ac.polman.astra.kelompok2.financialrecords.utils.FirebaseAnalyticsHelper;
import id.ac.polman.astra.kelompok2.financialrecords.utils.FirebaseAuthHelper;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Validation;
import id.ac.polman.astra.kelompok2.financialrecords.utils.Preference;

public class SignUpViewModel extends ViewModel {
    private final static String TAG = SignUpViewModel.class.getSimpleName();
    private FirebaseAuth firebaseAuth;

    public LiveData<ResponseModel> signUp(Activity activity, SignUpModel signUpModel) {
        MutableLiveData<ResponseModel> signUpLiveData = new MutableLiveData<>();

        FirebaseAuthHelper.getInstance();

        FirebaseAnalyticsHelper analytics = new FirebaseAnalyticsHelper(activity);
        analytics.logEventUserLogin(signUpModel.getEmail());

        if (signUpModel.getNama().isEmpty() && signUpModel.getAlamat().isEmpty() &&
                signUpModel.getEmail().isEmpty() && signUpModel.getPassword().isEmpty() &&signUpModel.getRePassword().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Data cannot be Empty"));
        else if (signUpModel.getNama().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Name is Empty"));
        else if (signUpModel.getAlamat().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Address is Empty"));
        else if (signUpModel.getEmail().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Email is Empty"));
        else if (signUpModel.getPassword().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Password is Empty"));
        else if (signUpModel.getRePassword().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Re Password is Empty"));
        else if (!Validation.matchEmail(signUpModel.getEmail()))
            signUpLiveData.postValue(new ResponseModel(false, "Invalid Email"));
        else if (!signUpModel.getRePassword().equals(signUpModel.getPassword()))
            signUpLiveData.postValue(new ResponseModel(false, "Password and Re Password is different"));
        else {
            Log.e("Running", "running");
            FirebaseAuthHelper.signUp(activity, signUpModel.getEmail(), signUpModel.getPassword()).observe((LifecycleOwner) activity, responseModel -> {
                Log.e(TAG, "signUp: "+ "response" );
                if (responseModel.isSuccess()) {
                    Log.e(TAG, "signUp: "+ "success" );
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> user = new HashMap<>();
                    user.put("email", signUpModel.getEmail());
                    user.put("nama", signUpModel.getNama());
                    user.put("alamat", signUpModel.getAlamat());
                    List<String> pemasukan = new ArrayList<>();
                    pemasukan.add("gaji");
                    List<String> pengeluaran = new ArrayList<>();
                    pengeluaran.add("Makan");
                    pengeluaran.add("Transportasi");
                    user.put("pemasukan", pemasukan);
                    user.put("pengeluaran", pengeluaran);
                    user.put("saldo", 0);

                    db.collection("user").document(signUpModel.getEmail())
                            .set(user).addOnSuccessListener(doc -> {
                        new Preference(activity).setUser(new UserEntity(
                                signUpModel.getEmail(),
                                signUpModel.getPassword(),
                                signUpModel.getNama(),
                                signUpModel.getAlamat(),
                                pemasukan,
                                pengeluaran,
                                0
                        ));
                        analytics.logUser(signUpModel.getEmail());
                        signUpLiveData.postValue(new ResponseModel(true, "Success"));
                    }).addOnFailureListener(e -> {
                        signUpLiveData.postValue(new ResponseModel(false, e.getLocalizedMessage()));
                    });
                }
                else
                    signUpLiveData.postValue(responseModel);
            });
        }
        return signUpLiveData;
    }

    public LiveData<ResponseModel> editProfile(Activity activity, SignUpModel signUpModel) {
        MutableLiveData<ResponseModel> editProfileLiveData = new MutableLiveData<>();

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseAuthHelper.getInstance();

        FirebaseAnalyticsHelper analytics = new FirebaseAnalyticsHelper(activity);
        analytics.logEventUserLogin(signUpModel.getEmail());

        if (signUpModel.getNama().isEmpty())
            editProfileLiveData.postValue(new ResponseModel(false, "Name is Empty"));
        else if (signUpModel.getAlamat().isEmpty())
            editProfileLiveData.postValue(new ResponseModel(false, "Address is Empty"));
        else {
            Log.e("Running", "running");
            //FirebaseAuthHelper.signUp(activity, signUpModel.getEmail(), signUpModel.getPassword()).observe((LifecycleOwner) activity, responseModel -> {
                Log.e(TAG, "signUp: "+ "response" );
                //if (responseModel.isSuccess()) {
                    Log.e(TAG, "signUp: "+ "success" );
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    db.collection("user")
                    .document(firebaseUser.getEmail()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            Map<String, Object> user = new HashMap<>();

                            String nama = signUpModel.getNama();
                            String alamat = signUpModel.getAlamat();

                            Log.e("CEKKKKKKKKKKKKK", "nama: "+ nama +"alamat: "+alamat);

                            //edit data
                            user.put("nama", nama);
                            user.put("alamat", alamat);

                            db.collection("user").document(firebaseUser.getEmail())
                                    .update(user).addOnSuccessListener(doc -> {
                                new Preference(activity).setUser(new UserEntity(
                                        nama,
                                        alamat
                                ));
                                editProfileLiveData.postValue(new ResponseModel(true, "Success"));
                            }).addOnFailureListener(e -> {
                                editProfileLiveData.postValue(new ResponseModel(false, e.getLocalizedMessage()));
                                Log.e(TAG, "ERROR"+ e.getLocalizedMessage());
                            });
                        }
                    });
//                else
//                    signUpLiveData.postValue(responseModel);
//            });
        }
        return editProfileLiveData;
    }
}
