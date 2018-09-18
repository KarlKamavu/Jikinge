package com.roonit.jikinge.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roonit.jikinge.R;
import com.roonit.jikinge.utils.BasicActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends BasicActivity {

    private TextView userName;
    private  TextView userEmail;
    private CircleImageView userAvatar;
    private RecyclerView userListerMessage;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Toolbar toolbar=(Toolbar)findViewById(R.id.account_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mon Compte");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userAvatar=(CircleImageView)findViewById(R.id.profile_image);
        userEmail=(TextView)findViewById(R.id.userEmail);
        userName=(TextView)findViewById(R.id.userName);



        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

    }

    @Override
    protected void onStart() {
        super.onStart();


        if (mUser!=null){
            userName.setText(mUser.getDisplayName());
            userEmail.setText(mUser.getEmail());
            Picasso.get()
                    .load(mUser.getPhotoUrl()).placeholder(R.drawable.profile)
                    .into(userAvatar);
        }

    }
}
