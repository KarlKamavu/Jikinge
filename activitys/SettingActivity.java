package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
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
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends BasicActivity {
    private TextView phoneUser;
    private FloatingActionButton choisirPhotoBTN;
    private CircleImageView userAvatar;
    private TextInputLayout userNameZone;
    private TextInputLayout adresseZone;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private Uri imagePostURI=null;

    StorageReference mFirestorageREF;
    FirebaseFirestore mFirestoreREF ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        inistialiserUI();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mFirestoreREF=FirebaseFirestore.getInstance();
        mFirestorageREF=FirebaseStorage.getInstance().getReference();


        String userId=getIntent().getStringExtra("userID");
        phoneUser.setText(mUser.getPhoneNumber());

        choisirPhotoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    int result= ContextCompat.checkSelfPermission(SettingActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setMinCropResultSize(512,512)
                                .setAspectRatio(1,1)
                                .start(SettingActivity.this);
                    } else {
                        ActivityCompat.requestPermissions(SettingActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }

                }
            }
        });
    }

    private void inistialiserUI() {
        phoneUser=(TextView)findViewById(R.id.phone_setting);
        choisirPhotoBTN=(FloatingActionButton)findViewById(R.id.choisirImage);
        userAvatar=(CircleImageView)findViewById(R.id.avatar_setting);
        userNameZone=(TextInputLayout) findViewById(R.id.user_name_setting);
        adresseZone=(TextInputLayout)findViewById(R.id.addresse_setting);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imagePostURI  = result.getUri();
                userAvatar.setImageURI(imagePostURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadDataUser(){

        showProgressDialog("Creation Compte","En cours de finalisation ...");

        String timestamp= UUID.randomUUID().toString();
        StorageReference localStorage=mFirestorageREF.child("user_image").child(timestamp+".jpg");

        localStorage.putFile(imagePostURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){

                    String name=userNameZone.getEditText().getText().toString();
                    String addresse=adresseZone.getEditText().getText().toString();
                    String phone=mUser.getPhoneNumber();
                    String imageUrl=task.getResult().getStorage().getDownloadUrl().toString();

                    Map<String,String> userMap= new HashMap<>();
                    userMap.put("role","user");
                    userMap.put("name",name);
                    userMap.put("addresse",addresse);
                    userMap.put("phone",phone);
                    userMap.put("image",imageUrl);
                    mFirestoreREF.collection("Users").add(userMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                hideProgressDialog();
                                goToPostActivity();
                            }else {
                                hideProgressDialog();
                            }
                        }
                    });
                }

            }
        });

    }

    private void goToPostActivity(){
        Intent postSuggIntent=new Intent(SettingActivity.this,PostActivity.class);
        startActivity(postSuggIntent);
        finish();
    }
    public void finaliserV(View view) {
        uploadDataUser();
    }



}
