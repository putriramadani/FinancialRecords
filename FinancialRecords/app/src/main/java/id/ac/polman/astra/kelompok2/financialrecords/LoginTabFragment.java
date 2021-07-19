package id.ac.polman.astra.kelompok2.financialrecords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class LoginTabFragment extends Fragment {

    EditText mEmail, mPass;
    Button mLogin;
    TextView mForgetPass;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        mEmail = root.findViewById(R.id.email);
        mPass = root.findViewById(R.id.pass);
        mLogin = root.findViewById(R.id.button);
        mForgetPass = root.findViewById(R.id.forget_pass);

        mEmail.setTranslationX(800);
        mPass.setTranslationX(800);
        mForgetPass.setTranslationX(800);
        mLogin.setTranslationX(800);

        mEmail.setAlpha(v);
        mPass.setAlpha(v);
        mForgetPass.setAlpha(v);
        mLogin.setAlpha(v);

        mEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mForgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        mLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return  root;
    }
}
