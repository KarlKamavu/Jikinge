package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.roonit.jikinge.R;
import com.roonit.jikinge.utils.BasicActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PostActivity extends BasicActivity {
    private static final int MAX_LENGTH =100 ;

    private EditText contenuSug,titreSug;
    private ImageView imagePost;
    private Button btnPost;


    private ProgressBar newPostProgress;
    private FirebaseFirestore mFirestore;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Toolbar toolbar=(Toolbar)findViewById(R.id.post_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Suggestions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mFirestore=FirebaseFirestore.getInstance();
        mUser= FirebaseAuth.getInstance().getCurrentUser();

        titreSug=(EditText)findViewById(R.id.suggestion_post_titre);
        contenuSug=(EditText)findViewById(R.id.suggestion_msg_post);

        btnPost=(Button) findViewById(R.id.post_btn);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressDialog("Suggestion","En cours d'envoie ...");

                Map<String, Object> suggestionData=new HashMap<>();
                suggestionData.put("titre",titreSug.getText().toString());
                suggestionData.put("suggestion",contenuSug.getText().toString());
                suggestionData.put("auteurID",mUser.getUid());
                suggestionData.put("timestamp",FieldValue.serverTimestamp());
                mFirestore.collection("suggestion").add(suggestionData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            goToPostActivity();
                            hideProgressDialog();

                        }
                    }
                });
            }
        });



    }

    private void goToPostActivity(){
        Intent postSuggIntent=new Intent(PostActivity.this,MainActivity.class);
        startActivity(postSuggIntent);
        finish();
    }



}
