package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roonit.jikinge.R;
import com.roonit.jikinge.fragment.AlertFragment;
import com.roonit.jikinge.fragment.ConseilFragment;
import com.roonit.jikinge.fragment.SugessionFragment;
import com.roonit.jikinge.utils.BasicActivity;

import java.util.ArrayList;
import java.util.List;

public class EsperActivity extends BasicActivity {

    private BottomNavigationView navigationView_btn;
    private Toolbar mTool;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expert_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.post_actualite:
                goToActualite();
                return true;
            case R.id.statistique_post:
                goToStatisque();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToActualite(){
        Intent actualiteIntent=new Intent(EsperActivity.this,ActualityActivity.class);

        startActivity(actualiteIntent);
    }
    private void goToStatisque(){
        Intent actualiteIntent=new Intent(EsperActivity.this,StatistiqueActivity.class);
        startActivity(actualiteIntent);
    }
}
