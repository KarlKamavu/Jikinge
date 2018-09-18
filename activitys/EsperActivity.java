package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roonit.jikinge.R;
import com.roonit.jikinge.fragment.AlertFragment;
import com.roonit.jikinge.fragment.ConseilFragment;
import com.roonit.jikinge.fragment.PostFragment;
import com.roonit.jikinge.fragment.SugessionFragment;
import com.roonit.jikinge.utils.BasicActivity;

import java.util.ArrayList;
import java.util.List;

public class EsperActivity extends BasicActivity {

    BottomNavigationView navigationView_btn;
    Toolbar mTool;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esper);

        mTool=(Toolbar)findViewById(R.id.espertool);
        setSupportActionBar(mTool);

        mAuth=FirebaseAuth.getInstance();

        viewPager = (ViewPager) findViewById(R.id.main_espert_frame);
        addFragmentsToViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.expert_tab);
        tabLayout.setupWithViewPager(viewPager);

        mUser=mAuth.getCurrentUser();
        if (mUser==null){
            startActivity(new Intent(EsperActivity.this,LoginActivity.class));
            finish();
        }

    }
    //
    private void addFragmentsToViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new AlertFragment(), "Disc");
        adapter.addFragment(new SugessionFragment(), "Suggest");
        adapter.addFragment(new ConseilFragment(), "Conseils");
        adapter.addFragment(new PostFragment(), "Stats");

        viewPager.setAdapter(adapter);
    }

    class TabAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public TabAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
