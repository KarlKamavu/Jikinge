package com.roonit.jikinge.fragment;


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
import com.roonit.jikinge.models.Post;
import com.roonit.jikinge.adaptor.PostRecyclerAdapter;
import com.roonit.jikinge.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView listPostMessageView;
    private List<Post> listOfPost;
    private FirebaseFirestore firebaseFirestore;
    private PostRecyclerAdapter postRecyclerAdapter;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Boolean isFirstPageLoaded=true;

    public HomeFragment() {
        // Required empty public constructor
    }
    DocumentSnapshot lastVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        listOfPost=new ArrayList<>();
        listPostMessageView=view.findViewById(R.id.list_post_message);

        postRecyclerAdapter=new PostRecyclerAdapter(getContext(),listOfPost);

        listPostMessageView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listPostMessageView.setAdapter(postRecyclerAdapter);
        listPostMessageView.setHasFixedSize(true);

        listPostMessageView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Boolean reachedBottom=!recyclerView.canScrollVertically(-1);
                if (reachedBottom){
                    loadMorePost();
                }
            }
        });

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        if (mUser!=null){

            firebaseFirestore=FirebaseFirestore.getInstance();
            Query firstQuery=firebaseFirestore.collection("posts").orderBy("timestamp",Query.Direction.DESCENDING).limit(25);
            firstQuery.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (!documentSnapshots.isEmpty()){
                        if (isFirstPageLoaded) {
                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        }
                        for(DocumentChange docs:documentSnapshots.getDocumentChanges()){
                            if (docs.getType()==DocumentChange.Type.ADDED){
                                Post post=docs.getDocument().toObject(Post.class);

                                if (isFirstPageLoaded){
                                    listOfPost.add(post);
                                }else {
                                    listOfPost.add(0,post);
                                }

                                postRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                        isFirstPageLoaded=false;
                    }


                }
            });
        }

        // Inflate the layout for this fragment
        return view;
    }
    public void loadMorePost(){
        Query firstQuery=firebaseFirestore.collection("posts")
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
                          Post post=docs.getDocument().toObject(Post.class);
                          listOfPost.add(post);
                          postRecyclerAdapter.notifyDataSetChanged();
                      }
                  }
              }

            }
        });
    }



}
