package com.roonit.jikinge.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.R;
import com.roonit.jikinge.utils.BasicActivity;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BasicActivity {

    private TextInputLayout nomUtilisateurV;
    private TextInputLayout emailUtilisateurV;
    private TextInputLayout passwordUtilisateurV;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nomUtilisateurV=(TextInputLayout)findViewById(R.id.nomRV);
        emailUtilisateurV=(TextInputLayout)findViewById(R.id.emailRV);
        passwordUtilisateurV=(TextInputLayout)findViewById(R.id.passRv);

        mAuth = FirebaseAuth.getInstance();
        /*if (mAuth.getCurrentUser()!=null) {
            currentUid = mAuth.getCurrentUser().getUid();
        }*/
        mFirestore=FirebaseFirestore.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null){
            startActivity(new Intent(RegisterActivity.this,EsperActivity.class));
            finish();
        }
    }

    public void goTologin(View view){
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }

    public void creerCompte(View view) {
        String nom=nomUtilisateurV.getEditText().getText().toString().trim();
        String email=emailUtilisateurV.getEditText().getText().toString().trim();
        String pass=passwordUtilisateurV.getEditText().getText().toString().trim();

        if(nom.isEmpty()&& email.isEmpty()&&pass.isEmpty()){
            Toast.makeText(RegisterActivity.this,"",Toast.LENGTH_LONG).show();
        }else {
            signUp(nom,email,pass);
        }
    }

    private void signUp(final String nom, final String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            showProgressDialog("Création Compte","En cours de création ...");
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Register", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Map<String,String> userMap= new HashMap<>();
                            userMap.put("role","esper");
                            userMap.put("name",nom);
                            userMap.put("email",email);
                            userMap.put("image","default");
                            currentUid=user.getUid();
                            mFirestore.collection("Users").document(currentUid).set(userMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"utilisateur connecté",Toast.LENGTH_LONG).show();
                                                hideProgressDialog();
                                            }else {
                                                Toast.makeText(RegisterActivity.this,"erreur de connexion",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            hideProgressDialog();
                        }

                        // ...
                    }
                });

    }
}
