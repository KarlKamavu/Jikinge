package com.roonit.jikinge.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.roonit.jikinge.R;
import com.roonit.jikinge.adaptor.PostRecyclerAdapter;
import com.roonit.jikinge.adaptor.SuggestionAdaptor;
import com.roonit.jikinge.models.Post;
import com.roonit.jikinge.models.Suggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SugessionFragment extends Fragment {

    private RecyclerView listSuggestionMessageView;
    private List<Suggestion> listOfSuggestion;
    private FirebaseFirestore firebaseFirestore;
    private SuggestionAdaptor suggestionRecyclerAdapter;



    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private Boolean isFirstPageLoaded=true;
    private DocumentSnapshot lastVisible;


    public SugessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sugession, container, false);
        listOfSuggestion=new ArrayList<>();
        listSuggestionMessageView=(RecyclerView)view.findViewById(R.id.list_suggestion);

        suggestionRecyclerAdapter=new SuggestionAdaptor(getContext(),listOfSuggestion);

        listSuggestionMessageView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listSuggestionMessageView.setAdapter(suggestionRecyclerAdapter);
        listSuggestionMessageView.setHasFixedSize(true);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        if (mUser!=null){

            firebaseFirestore=FirebaseFirestore.getInstance();
            Query firstQuery=firebaseFirestore.collection("suggestion").orderBy("timestamp",Query.Direction.DESCENDING).limit(25);
            firstQuery.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (!documentSnapshots.isEmpty()){
                        if (isFirstPageLoaded) {
                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        }
                        for(DocumentChange docs:documentSnapshots.getDocumentChanges()){
                            if (docs.getType()==DocumentChange.Type.ADDED){
                                Suggestion suggestion=docs.getDocument().toObject(Suggestion.class);

                                if (isFirstPageLoaded){
                                    listOfSuggestion.add(suggestion);
                                }else {
                                    listOfSuggestion.add(0,suggestion);
                                }

                                suggestionRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                        isFirstPageLoaded=false;
                    }


                }
            });
        }

        return view;
    }
    public void loadMorePost(){
        Query firstQuery=firebaseFirestore.collection("suggestion")
                .orderBy("timestamp",Query.Direction.DESCENDING)
                .startAfter(lastVisible)
                .limit(25);
        firstQuery.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (!documentSnapshots.isEmpty()){
                    lastVisible =documentSnapshots.getDocuments().get(documentSnapshots.size()-1);
                    for(DocumentChange docs:documentSnapshots.getDocumentChanges()){
                        if (docs.getType()==DocumentChange.Type.ADDED){
                            Suggestion post=docs.getDocument().toObject(Suggestion.class);
                            listOfSuggestion.add(post);
                            suggestionRecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }
        });
    }

}
