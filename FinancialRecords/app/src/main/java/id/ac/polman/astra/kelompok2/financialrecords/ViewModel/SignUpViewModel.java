<<<<<<< HEAD:FinancialRecords/app/src/main/java/id/ac/polman/astra/kelompok2/financialrecords/ui/fragment/SignUpViewModel.java
package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;
||||||| 66d6862:FinancialRecords/app/src/main/java/id/ac/polman/astra/kelompok2/financialrecords/SignUpViewModel.java
package id.ac.polman.astra.kelompok2.financialrecords;
=======
package id.ac.polman.astra.kelompok2.financialrecords.ViewModel;
>>>>>>> main:FinancialRecords/app/src/main/java/id/ac/polman/astra/kelompok2/financialrecords/ViewModel/SignUpViewModel.java

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public LiveData<ResponseModel> signUp(Activity activity, SignUpModel signUpModel) {
        MutableLiveData<ResponseModel> signUpLiveData = new MutableLiveData<>();

        FirebaseAuthHelper.getInstance();

        FirebaseAnalyticsHelper analytics = new FirebaseAnalyticsHelper(activity);
        analytics.logEventUserLogin(signUpModel.getEmail());

        if (signUpModel.getEmail().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Email is Empty"));
        else if (signUpModel.getAlamat().isEmpty())
            signUpLiveData.postValue(new ResponseModel(false, "Address is Empty"));
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
}
