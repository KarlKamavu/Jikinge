package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
import java.util.UUID;

public class ActualityActivity extends BasicActivity {
    private EditText mTitre;
    private EditText mContenu;
    private ImageView mImage;

    private FirebaseFirestore mFirestore;
    private StorageReference mFirestarage;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private Uri imagePostURI=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actuality);

        mFirestore=FirebaseFirestore.getInstance();
        mFirestarage= FirebaseStorage.getInstance().getReference();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        initialiseView();
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    int result= ContextCompat.checkSelfPermission(ActualityActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setMinCropResultSize(512,512)
                                .setAspectRatio(1,1)
                                .start(ActualityActivity.this);
                    } else {
                        ActivityCompat.requestPermissions(ActualityActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }

                }
            }
        });
    }

    private void initialiseView() {
        mTitre=(EditText)findViewById(R.id.actualite_post_titre);
        mContenu=(EditText)findViewById(R.id.actualite_msg_post);
        mImage=(ImageView)findViewById(R.id.add_img_actualite);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imagePostURI  = result.getUri();
                mImage.setImageURI(imagePostURI);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadDataUser(){

        showProgressDialog("Création Actualité","En cours de publication ...");

        String timestamp= UUID.randomUUID().toString();
        final StorageReference localStorage=mFirestarage.child("post_image").child(timestamp+".jpg");

        localStorage.putFile(imagePostURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if(task.isSuccessful()){

                    if (mUser!=null){

                        //String imageUrl=task.getResult().getDownloadUrl().toString();



                                    String titre=mTitre.getText().toString();
                                    String contenu=mContenu.getText().toString();


                                   // Log.d("MALA",imageUrl);
                                    Map<String,Object> postMapData= new HashMap<>();
                                    postMapData.put("titre",titre);
                                    postMapData.put("contenu",contenu);
                                   // postMapData.put("image",imageUrl);
                                    postMapData.put("auteurID",mUser.getUid());
                                    postMapData.put("timestamp", FieldValue.serverTimestamp());

                                    mFirestore.collection("posts").add(postMapData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
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






                }


        });

    }

    private void goToPostActivity(){
        Intent postSuggIntent=new Intent(ActualityActivity.this,EsperActivity.class);
        startActivity(postSuggIntent);
        finish();
    }
    public void publierDonnee(View view) {
        uploadDataUser();

    }

}
