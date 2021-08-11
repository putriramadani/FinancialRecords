package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.HomeActivity;

public class LoginTabFragment extends Fragment {

    private EditText mEmail, mPass;
    private Button mLogin;
    private ImageView mShow;
    float v = 0;

    private View objectLTF;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    public LoginTabFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        objectLTF = inflater.inflate(R.layout.login_tab_fragment, container, false);
        attachToXML();
        mEmail = objectLTF.findViewById(R.id.email);
        mPass = objectLTF.findViewById(R.id.pass);
        mLogin = objectLTF.findViewById(R.id.button);
        mShow = objectLTF.findViewById(R.id.show_pass_btn);

//        mEmail.setTranslationX(800);
//        mPass.setTranslationX(800);
//        mForgetPass.setTranslationX(800);
//        mLogin.setTranslationX(800);

//        mEmail.setAlpha(v);
//        mPass.setAlpha(v);
//        mForgetPass.setAlpha(v);
//        mLogin.setAlpha(v);

//        mEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        mPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        mForgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        mLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return objectLTF;
    }

    private void attachToXML(){
        try {
            //Log.e("Login", "xml");
            //init firebase auth
            FirebaseApp.initializeApp(getContext());
            firebaseAuth = FirebaseAuth.getInstance();
            checkUser();

            mEmail = objectLTF.findViewById(R.id.email);
            mPass = objectLTF.findViewById(R.id.pass);
            mLogin = objectLTF.findViewById(R.id.login_button);
            mShow = objectLTF.findViewById(R.id.show_pass_btn);

            mShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                        //Show Password
                        mPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(v)).setImageResource(R.drawable.ic_baseline_remove_red_eye_24);

                        //Hide Password
                        mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }
            });

            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateData();
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void validateData(){
        if (TextUtils.isEmpty(mEmail.getText().toString()) && TextUtils.isEmpty(mPass.getText().toString())){
            mEmail.setError("email is empty");
            mPass.setError("password is empty");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches() && !TextUtils.isEmpty(mEmail.getText().toString())){
            //email format is invalid, dont proceed further
            mEmail.setError("Invalid email format");
        }
        else if (TextUtils.isEmpty(mEmail.getText().toString())){
                mEmail.setError("email is empty");
        }
        else if (TextUtils.isEmpty(mPass.getText().toString())){
            //no password is entered
            mPass.setError("password is empty");
        }
        else {
            firebaseLogin();
        }
    }

    private void firebaseLogin() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        //data is valid, now continue firebase signup

        firebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPass.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        progressDialog.setTitle("Log in");
                        progressDialog.setMessage("Please Wait");
                        progressDialog.show();

                        //getuserinfo
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        //open profile activity
                        startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
                        progressDialog.dismiss();
                        getActivity().finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //login failed, get and show error message
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}