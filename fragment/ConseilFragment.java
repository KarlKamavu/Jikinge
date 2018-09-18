package com.roonit.jikinge.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.R;


import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConseilFragment extends Fragment {
    private EditText titreV;
    private EditText contenuV;
    private Button publier;
    private ProgressDialog mProgressDialog;

    private FirebaseFirestore firebaseFirestore;

    public ConseilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_conseil, container, false);
            titreV=(EditText)view.findViewById(R.id.titre_post_esper);
            contenuV=(EditText)view.findViewById(R.id.contenu_post_esper);
            publier=(Button)view.findViewById(R.id.publier);

            firebaseFirestore=FirebaseFirestore.getInstance();

            publier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showProgressDialog("Publication Conseil","Conseil en cours d'envoie");
                    String titre=titreV.getText().toString();
                    String contenu=contenuV.getText().toString();
                    Map<String, Object> conseil = new HashMap<>();

                    conseil.put("titre",titre);
                    conseil.put("contenu",contenu);
                    conseil.put("timestamp", FieldValue.serverTimestamp());


                    firebaseFirestore.collection("conseil").add(conseil).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(),"conseil posted Success",Toast.LENGTH_LONG).show();
                                hideProgressDialog();
                            }else {
                                Toast.makeText(getContext(),"conseil error",Toast.LENGTH_LONG).show();
                                hideProgressDialog();
                            }
                        }
                    });

                }
            });
        return view;
    }

    public void showProgressDialog(String titre,String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle(titre);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
