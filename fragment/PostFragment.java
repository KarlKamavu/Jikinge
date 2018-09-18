package com.roonit.jikinge.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.R;

import java.util.HashMap;
import java.util.Map;


public class PostFragment extends Fragment {

    private EditText mortV,atteintV,familleV,suspectV,gueriV;
    private Button publierV;
    private ProgressDialog mProgressDialog;

    private FirebaseFirestore firebaseFirestore;
    public PostFragment() {

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_post, container, false);

        firebaseFirestore=FirebaseFirestore.getInstance();

        mortV=view.findViewById(R.id.nombre_mort);
        atteintV=view.findViewById(R.id.nombre_cas_atteint);
        familleV=view.findViewById(R.id.nombre_famille_effecte);
        suspectV=view.findViewById(R.id.nombre_cas_suspect);
        gueriV=view.findViewById(R.id.nombre_cas_gueri);

        publierV=view.findViewById(R.id.poster);

        publierV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publierV.setEnabled(false);


                if (mortV.getText().equals(null)&&
                        atteintV.getText().equals(null)
                        &&familleV.getText().equals(null)&&
                        suspectV.getText().equals(null)&&
                        gueriV.getText().equals(null)){

                }else {
                    showProgressDialog("Rapport Statistique","Rapport en cours d'envoie");

                    int mort=Integer.parseInt(mortV.getText().toString());
                    int atteint=Integer.parseInt(atteintV.getText().toString());
                    int famille=Integer.parseInt(familleV.getText().toString());
                    int suspect=Integer.parseInt(suspectV.getText().toString());
                    int gueri=Integer.parseInt(gueriV.getText().toString());

                    Map<String,Integer> statistique=new HashMap<>();
                    statistique.put("mort",mort);
                    statistique.put("atteint",atteint);
                    statistique.put("famille",famille);
                    statistique.put("suspect",suspect);
                    statistique.put("gueri",gueri);

                    firebaseFirestore.collection("statistic").add(statistique).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(),"",Toast.LENGTH_LONG).show();
                                hideProgressDialog();
                                mortV.setText("");
                                atteintV.setText("");
                                familleV.setText("");
                                suspectV.setText("");
                                gueriV.setText("");

                                publierV.setEnabled(true);
                            }
                        }
                    });


                }



            }
        });




        return view;
    }

}
