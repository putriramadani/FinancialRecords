package id.ac.polman.astra.kelompok2.financialrecords.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.HomeActivity;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.LoginActivity;
import id.ac.polman.astra.kelompok2.financialrecords.ui.activity.ProfileActivity;

public class DashboardTabFragment extends Fragment {

    private View objectDTF;
    private FirebaseAuth firebaseAuth;
    Button mLogoutBtn;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dashboard_tab_fragment);
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        //checkUser();
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//        if (fragment == null){
//            fragment = DashboardListFragment.newInstance();
//            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectDTF = inflater.inflate(R.layout.dashboard_tab_fragment, container, false);

        return objectDTF;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        
        Fragment childFragment = new DashboardListFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, childFragment).commit();
    }

}
