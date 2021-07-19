package id.ac.polman.astra.kelompok2.financialrecords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {

    EditText mEmail, mPass, mName, mAddress;
    Button mSignUp;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        mEmail = root.findViewById(R.id.email);
        mPass = root.findViewById(R.id.pass);
        mName = root.findViewById(R.id.name);
        mAddress = root.findViewById(R.id.address);
        mSignUp = root.findViewById(R.id.button);

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

        return  root;
    }
}
