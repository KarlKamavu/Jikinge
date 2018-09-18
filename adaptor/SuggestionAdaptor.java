package com.roonit.jikinge.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roonit.jikinge.R;
import com.roonit.jikinge.models.Suggestion;

import java.util.Date;
import java.util.List;

public class SuggestionAdaptor extends RecyclerView.Adapter<SuggestionAdaptor.SuggestionHolder> {

    private Context context;
    private List<Suggestion> suggestionList;


    public SuggestionAdaptor(Context context,List<Suggestion> suggestionList){
       this.context=context;
       this.suggestionList=suggestionList;

    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_message_item, parent, false);
        return new SuggestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionHolder holder, int position) {
        Suggestion suggestion=suggestionList.get(position);

        holder.titreSug.setText(suggestion.getTitre());
        holder.suggestionSug.setText(suggestion.getSuggestion());
        holder.auteurSug.setText(suggestion.getAuteur());

        //date configuration
        long miliseconds=suggestionList.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(miliseconds)).toString();
        holder.dateSug.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return suggestionList.size();
    }

    class SuggestionHolder extends RecyclerView.ViewHolder{
        TextView auteurSug,titreSug,suggestionSug,dateSug;
        View view;

        public SuggestionHolder(View itemView) {
            super(itemView);
            view=itemView;
            auteurSug=view.findViewById(R.id.auteur_suggestion);
            titreSug=view.findViewById(R.id.titre_suggestion);
            suggestionSug=view.findViewById(R.id.suggestion_message);
            dateSug=view.findViewById(R.id.date_suggestion);
        }
    }
}
