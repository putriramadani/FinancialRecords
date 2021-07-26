package id.ac.polman.astra.kelompok2.financialrecords.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import id.ac.polman.astra.kelompok2.financialrecords.R;

public class LoginActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        mTabLayout.addTab(mTabLayout.newTab().setText("Login"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Signup"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        //mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setTranslationY(300);
        mTabLayout.setAlpha(v);
        mTabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}