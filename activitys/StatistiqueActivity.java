package com.roonit.jikinge.activitys;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.R;
import com.roonit.jikinge.utils.BasicActivity;

import java.util.HashMap;
import java.util.Map;

public class StatistiqueActivity extends BasicActivity {

    private EditText mortV,atteintV,familleV,suspectV,gueriV;
    private Button publierV;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        firebaseFirestore=FirebaseFirestore.getInstance();

        mortV=(EditText)findViewById(R.id.nombre_mort);
        atteintV=(EditText)findViewById(R.id.nombre_cas_atteint);
        familleV=(EditText)findViewById(R.id.nombre_famille_effecte);
        suspectV=(EditText)findViewById(R.id.nombre_cas_suspect);
        gueriV=(EditText)findViewById(R.id.nombre_cas_gueri);
        publierV=(Button) findViewById(R.id.poster);

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

                    int deceder=Integer.parseInt(mortV.getText().toString());
                    int confirmer=Integer.parseInt(atteintV.getText().toString());
                    int probable=Integer.parseInt(familleV.getText().toString());
                    int suspect=Integer.parseInt(suspectV.getText().toString());
                    int gueri=Integer.parseInt(gueriV.getText().toString());

                    Map<String,Integer> statistique=new HashMap<>();
                    statistique.put("deceder",deceder);
                    statistique.put("confirmer",confirmer);
                    statistique.put("probable",probable);
                    statistique.put("suspect",suspect);
                    statistique.put("gueri",gueri);

                    firebaseFirestore.collection("statistique").add(statistique).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(StatistiqueActivity.this,"",Toast.LENGTH_LONG).show();
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


    }
}
