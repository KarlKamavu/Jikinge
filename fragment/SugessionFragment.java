package com.roonit.jikinge.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private SuggestionAdaptor suggestionRecyclerAdapter;






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

        return view;
    }

}
