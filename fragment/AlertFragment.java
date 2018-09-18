package com.roonit.jikinge.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.roonit.jikinge.models.Message;
import com.roonit.jikinge.adaptor.MessageAdaptor;
import com.roonit.jikinge.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends Fragment {

    private EditText zoneSaisie;
    private FloatingActionButton btnSend;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;
    private  List<Message> listOfMessage;
    private RecyclerView listMessage;
    private MessageAdaptor messageAdaptor;

    public AlertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =inflater.inflate(R.layout.fragment_alert, container, false);


      zoneSaisie=(EditText)view.findViewById(R.id.zone_message_saisie_alert);
      btnSend=(FloatingActionButton)view.findViewById(R.id.send_btn_alert);
      listMessage=(RecyclerView)view.findViewById(R.id.listMessage);



      firebaseFirestore=FirebaseFirestore.getInstance();
      listOfMessage=new ArrayList<>();
        messageAdaptor=new MessageAdaptor(getContext(),listOfMessage);

        listMessage.setHasFixedSize(true);
        listMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        listMessage.setAdapter(messageAdaptor);

      mAuth=FirebaseAuth.getInstance();
      mUser=mAuth.getCurrentUser();

      btnSend.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if (mUser!=null) {
                String message=zoneSaisie.getText().toString().trim();
                Map<String,Object> messageMap=new HashMap<>();
                messageMap.put("message",message);
                messageMap.put("timestamp", FieldValue.serverTimestamp());
                messageMap.put("sender",mUser.getDisplayName());

                zoneSaisie.setText("");


                firebaseFirestore.collection("message").add(messageMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(getContext()," ok message poster",Toast.LENGTH_LONG).show();
                       }else {
                           Toast.makeText(getContext()," error poste message",Toast.LENGTH_LONG).show();
                       }
                    }
                });

            }else {
                Toast.makeText(getContext(),"Error d'envoie de message", Toast.LENGTH_LONG).show();
            }

          }
      });
      Query firstQuery=firebaseFirestore.collection("message").orderBy("timestamp",Query.Direction.DESCENDING);

      firstQuery.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
          @Override
          public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

              for(DocumentChange docs:documentSnapshots.getDocumentChanges()){
                  if (docs.getType()==DocumentChange.Type.ADDED){
                      Message message=docs.getDocument().toObject(Message.class);
                      listOfMessage.add(message);
                      messageAdaptor.notifyDataSetChanged();
                  }
              }
          }
      });

      return view;
    }

}
