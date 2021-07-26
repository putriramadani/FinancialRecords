package id.ac.polman.astra.kelompok2.financialrecords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;

import id.ac.polman.astra.kelompok2.financialrecords.R;

public class DashboardTabFragment extends Fragment {

    private View objectDTF;
    private FirebaseAuth firebaseAuth;

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
}
