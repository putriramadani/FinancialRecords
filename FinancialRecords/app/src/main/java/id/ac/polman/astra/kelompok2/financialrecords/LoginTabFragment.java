package id.ac.polman.astra.kelompok2.financialrecords;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabFragment extends Fragment {

    private EditText mEmail, mPass;
    private Button mLogin;
    private TextView mForgetPass;
    float v = 0;

    private View objectLTF;

    //ProgressBar
    //private ProgressBar mProgressBar;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    public LoginTabFragment(){
        //required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);
        objectLTF = inflater.inflate(R.layout.login_tab_fragment, container, false);
        //Log.e("Login", "view");
        attachToXML();
        mEmail = objectLTF.findViewById(R.id.email);
        mPass = objectLTF.findViewById(R.id.pass);
        mLogin = objectLTF.findViewById(R.id.button);
        mForgetPass = objectLTF.findViewById(R.id.forget_pass);

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
            mForgetPass = objectLTF.findViewById(R.id.forget_pass);

            //mProgressBar = objectLTF.findViewById(R.id.loginPB);
            //Log.e("Login", "xml2");
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //validate data
                    //Log.e("Login", "button");
                    validateData();
                    //mProgressBar.setVisibility(View.VISIBLE);
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUser() {
        //check if user is already loggedin
        //if already logged in then open profile activity
        //Log.e("Login", "checkUser");
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //Log.e("Login", firebaseUser.getEmail());
        if (firebaseUser != null){
            //user is already loggedin
            //Log.e("Login", "!= null");
            //startActivity(new Intent(getActivity(), DashboardActivity.class));
            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void validateData(){
        //Log.e("Login", "validateData");
        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()){
            //email format is invalid, dont proceed further
            mEmail.setError("Invalid email format");
        }
        else if (TextUtils.isEmpty(mPass.getText().toString())){
            //no password is entered
            mEmail.setError("Enter password");
        }
        else {
            //data is valid, now continue firebase signup
            firebaseLogin();
        }
    }

    private void firebaseLogin() {
        //Log.e("Login", "firebaseLogin");
        firebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPass.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //login success
                        //getuserinfo
                        Log.e("Login", "sukses");
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(getContext(), "LoggedIn\n"+email, Toast.LENGTH_SHORT).show();

                        //open profile activity
                        startActivity(new Intent(getActivity().getApplicationContext(), DashboardActivity.class));

                        getActivity().finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //login failed, get and show error message
                        //mProgressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}