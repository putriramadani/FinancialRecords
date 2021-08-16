package id.ac.polman.astra.kelompok2.financialrecords.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.polman.astra.kelompok2.financialrecords.model.KategoriModel;
import id.ac.polman.astra.kelompok2.financialrecords.model.SignUpModel;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.DashboardListFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.DashboardTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriDialogFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.KategoriTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.R;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.LaporanTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.PiechartTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.ProfileTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.ui.fragment.SignupTabFragment;
import id.ac.polman.astra.kelompok2.financialrecords.utils.FirebaseAuthHelper;

public class HomeActivity extends AppCompatActivity {

    //SignUpModel signUpModel;
    private FirebaseAuth firebaseAuth;
    String nama, alamat;

//    int totalpem;
//    int totalpen;

    public HomeActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DashboardTabFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .document(firebaseUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        Map<String, Object> user = new HashMap<>();

                            nama = (String) document.get("nama");
                            alamat = (String) document.get("alamat");

//                            totalpem = (int) document.getLong("saldo").intValue();
//                            totalpen = (int) document.getLong("saldo").intValue();

                        int id = item.getItemId();
                        if (id == R.id.profile_menu){
                            Fragment selectedFragment = null;
                            selectedFragment = new ProfileTabFragment(nama, alamat);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                        }
                        else if(id == R.id.piechart_menu){
                            Fragment selectedFragment = null;

                            selectedFragment = new PiechartTabFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    selectedFragment).commit();
                        }
                        else if (id == R.id.logout_menu){
                            firebaseAuth = FirebaseAuth.getInstance();
                            firebaseAuth.signOut();
                            signOutUser();
                        }
                    }
                });
        return true;
    }

    private void signOutUser(){
        Intent ma = new Intent(HomeActivity.this, LoginActivity.class);
        ma.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ma);
        finish();

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
                            selectedFragment = new LaporanTabFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}