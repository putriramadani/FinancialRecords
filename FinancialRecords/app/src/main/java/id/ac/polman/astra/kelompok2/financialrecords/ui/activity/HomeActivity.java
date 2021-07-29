package id.ac.polman.astra.kelompok2.financialrecords.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.DashboardListFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.DashboardTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.SignupTabFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DashboardListFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_kategori:
                                selectedFragment = new KategoriTabFragment();
                                break;
                        case R.id.nav_dashboard:
                            selectedFragment = new DashboardTabFragment();
                            break;
                        case R.id.nav_laporan:
                            selectedFragment = new SignupTabFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}