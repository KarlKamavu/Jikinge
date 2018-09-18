package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.R;
import com.roonit.jikinge.fragment.BookFragment;
import com.roonit.jikinge.fragment.HomeFragment;
import com.roonit.jikinge.fragment.StatisFragment;
import com.roonit.jikinge.utils.BasicActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BasicActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    FloatingActionButton addPost;
    private BottomNavigationView mBottomNav;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String currentUID;

    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Epuka Ebola");

        firebaseFirestore=FirebaseFirestore.getInstance();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        addFragmentsToViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        addPost=(FloatingActionButton)findViewById(R.id.floatingActionButton4);




        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();



        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser!=null){
                    startActivity(new Intent(MainActivity.this,PostActivity.class));
                }else{
                   signIn();

                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_principale,menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOut:
                signOut();
                return true;

            case R.id.mode_esper:
                startActivity(new Intent(MainActivity.this,EsperActivity.class));
                return true;
            case R.id.compte:
                startActivity(new Intent(MainActivity.this,AccountActivity.class));
                return true;
                default:
                    return false;
        }
    }


    private void addFragmentsToViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Accueil");
        adapter.addFragment(new BookFragment(), "Conseils");
        adapter.addFragment(new StatisFragment(), "Statistiques");
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

    private void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(

                new AuthUI.IdpConfig.PhoneBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                GotoSettings(user);

            } else {

            }
        }
    }
    public void GotoSettings(FirebaseUser user){
        String userID=user.getUid();
        Intent settingIntent=new Intent(MainActivity.this,SettingActivity.class);
        settingIntent.putExtra("userID",userID);
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingIntent);
        finish();

    }






}
