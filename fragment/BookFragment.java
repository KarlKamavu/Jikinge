package com.roonit.jikinge.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.roonit.jikinge.models.Conseil;
import com.roonit.jikinge.R;
import com.roonit.jikinge.adaptor.coursRecyclerAdaptor;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {

    //declaration
    private List<Conseil>  mConseilPost;
    private RecyclerView mlist;
    private coursRecyclerAdaptor adapter;
    private FirebaseFirestore firebaseFirestore;

    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_book, container, false);
        mConseilPost=new ArrayList<>();
        mlist=(RecyclerView)view.findViewById(R.id.list_post_cours);

        adapter=new coursRecyclerAdaptor(getContext(),mConseilPost);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mlist.setLayoutManager( linearLayoutManager);
        mlist.setAdapter(adapter);
        mlist.setHasFixedSize(true);
        firebaseFirestore=FirebaseFirestore.getInstance();
        Query query=firebaseFirestore.collection("conseil").orderBy("timestamp",Query.Direction.DESCENDING);
        query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc:documentSnapshots.getDocumentChanges()){
                    if (doc.getType()==DocumentChange.Type.ADDED){
                        Conseil conseil=doc.getDocument().toObject(Conseil.class);
                        mConseilPost.add(conseil);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
