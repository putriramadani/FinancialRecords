package id.ac.polman.astra.kelompok2.financialrecords;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    //firebase auth
    private FirebaseAuth firebaseAuth;

    Button mLogoutBtn;
    TextView mEmailTv;

    //action Bar
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //configure actionbar, tittle, back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("LogIn");

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        mLogoutBtn = findViewById(R.id.logoutBtn);

        //logout user by clicking
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        //check if user is not loggedin the move to login activity

        mEmailTv = findViewById(R.id.emailTv);

        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            //user not loggedin, move to login screen
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        }
        else{
            //user logged in, get info
            String email = firebaseUser.getEmail();
            //set to email tv
            mEmailTv.setText(email);
        }
    }
}
