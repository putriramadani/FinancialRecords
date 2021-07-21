package id.ac.polman.astra.kelompok2.financialrecords;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import id.ac.polman.astra.kelompok2.financialrecords.model.SignUpModel;

public class SignupTabFragment extends Fragment {

    EditText mEmail, mPass, mName, mAddress, mRepass;
    Button mSignUp;
    float v = 0;
    private View objectLTF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        objectLTF = inflater.inflate(R.layout.signup_tab_fragment, container, false);

        mEmail = objectLTF.findViewById(R.id.signup_email);
        mPass = objectLTF.findViewById(R.id.signup_pass);
        mName = objectLTF.findViewById(R.id.signup_name);
        mAddress = objectLTF.findViewById(R.id.signup_address);
        mRepass = objectLTF.findViewById(R.id.signup_repass);
        mSignUp = objectLTF.findViewById(R.id.button);

        mEmail.setTranslationX(800);
        mPass.setTranslationX(800);
        mName.setTranslationX(800);
        mAddress.setTranslationX(800);
        mSignUp.setTranslationX(800);

        mEmail.setAlpha(v);
        mPass.setAlpha(v);
        mName.setAlpha(v);
        mAddress.setAlpha(v);
        mSignUp.setAlpha(v);

        mEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mAddress.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mSignUp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();


        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpViewModel viewModel = new SignUpViewModel();

                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Sign Up");
                progressDialog.setMessage("Please Wait");
                progressDialog.show();

                mEmail = objectLTF.findViewById(R.id.signup_email);
                mPass = objectLTF.findViewById(R.id.signup_pass);
                mName = objectLTF.findViewById(R.id.signup_name);
                mAddress = objectLTF.findViewById(R.id.signup_address);
                mRepass = objectLTF.findViewById(R.id.signup_repass);
                mSignUp = objectLTF.findViewById(R.id.button);
                SignUpModel signUpModel = new SignUpModel(mName.getText().toString(), mEmail.getText().toString(),
                        mPass.getText().toString(), mRepass.getText().toString(), mAddress.getText().toString());

                viewModel.signUp(getActivity(), signUpModel).observe(getActivity(), responseModel -> {
                    progressDialog.dismiss();

                    if (responseModel.isSuccess())
                        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                    else
                        new AlertDialog
                                .Builder(getActivity())
                                .setTitle("Failed")
                                .setMessage(responseModel.getMessage())
                                .show();
                });
            }
        });

        return  objectLTF;
    }
}
