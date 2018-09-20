package com.roonit.jikinge.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.R;
import com.roonit.jikinge.models.Suggestion;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SuggestionAdaptor extends RecyclerView.Adapter<SuggestionAdaptor.SuggestionHolder> {

    private Context context;
    private List<Suggestion> suggestionList;
    private FirebaseFirestore firebaseFirestore;

    public SuggestionAdaptor(Context context,List<Suggestion> suggestionList){
       this.context=context;
       this.suggestionList=suggestionList;

    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggestion_item_view, parent, false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new SuggestionHolder(view);
    }

    @Override
    public void onBindViewHolder(final SuggestionHolder holder, int position) {

      final Suggestion suggestion=suggestionList.get(position);

        holder.titreSug.setText(suggestion.getTitre());
        holder.suggestionSug.setText(suggestion.getSuggestion());
        //holder.auteurSug.setText(suggestion.getAuteur());

        String userId=suggestionList.get(position).getAuteurID();
        firebaseFirestore.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    String userName=task.getResult().get("name").toString();
                    String avatar=task.getResult().get("image").toString();
                    holder.auteurSug.setText(userName);
                    Picasso.get().load(avatar).placeholder(R.drawable.profile).into(holder.mAvatar);
                }else {
                    Toast.makeText(context,"Dificile de charger l'image",Toast.LENGTH_LONG).show();
                }
            }
        });

       // Picasso.get().load(suggestion.getAvatar()).placeholder(R.drawable.profile).into(holder.mAvatar);
        //date configuration
        long miliseconds=suggestionList.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(miliseconds)).toString();
        holder.dateSug.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return suggestionList.size();
    }

    static class SuggestionHolder extends RecyclerView.ViewHolder{
        TextView auteurSug,titreSug,suggestionSug,dateSug;
        CircleImageView mAvatar;
        View view;

        public SuggestionHolder(View itemView) {
            super(itemView);
            view=itemView;
            mAvatar=(CircleImageView)view.findViewById(R.id.avatar_sugg);
            auteurSug=(TextView) view.findViewById(R.id.auteur_suggestion);
            titreSug=(TextView)view.findViewById(R.id.titre_suggestion);
            suggestionSug=(TextView)view.findViewById(R.id.suggestion_message);
            dateSug=(TextView)view.findViewById(R.id.date_suggestion);
        }
    }
}
