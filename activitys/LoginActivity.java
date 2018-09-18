package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.R;
import com.roonit.jikinge.utils.BasicActivity;

public class LoginActivity extends BasicActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseFirestore mFirestore;
    String currentUID;

    TextInputLayout e;
    TextInputLayout p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolV);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");

        e=(TextInputLayout)findViewById(R.id.emailLV);
        p=(TextInputLayout)findViewById(R.id.passwordLV);





        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        //config for firestore
        mFirestore=FirebaseFirestore.getInstance();


    }


    // cas de google account



    //cas pour email

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            hideProgressDialog();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            hideProgressDialog();
                        }

                        // ...
                    }
                });
    }






    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
          if (currentUser!=null){
              sendToMain();
          }
    }
    void updateUI(FirebaseUser user){
        if (user!=null){
            Intent mainIntent = new Intent(LoginActivity.this,EsperActivity.class);

            startActivity(mainIntent);
            finish();
        }
    }

    private void sendToMain() {

        Intent mainIntent = new Intent(LoginActivity.this, PostActivity.class);
        startActivity(mainIntent);
        finish();

    }


    // cas de email et password
    public void connecter(View view) {
        String email=e.getEditText().getText().toString().trim();
        String password=p.getEditText().getText().toString().trim();
        showProgressDialog("Login","Connexion en cours ...");
        signIn(email,password);
    }


    public void goToRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }
}
