package com.roonit.jikinge.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roonit.jikinge.models.Conseil;
import com.roonit.jikinge.activitys.DetailconseilActivity;
import com.roonit.jikinge.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class coursRecyclerAdaptor extends RecyclerView.Adapter<coursRecyclerAdaptor.CoursViewHolder> {
    List<Conseil> listConseil=new ArrayList<>();
    Context context;
    public coursRecyclerAdaptor(Context context,List<Conseil> listConseil){
        this.listConseil=listConseil;
        this.context=context;
    }

    @NonNull
    @Override
    public CoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cours_item,null);
        return new  CoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursViewHolder holder, int position) {
       Conseil conseil =listConseil.get(position);
       final String title=listConseil.get(position).getTitre();
       final String content=listConseil.get(position).getContenu();
       holder.titre.setText(conseil.getTitre());
       holder.contenu.setText(conseil.getContenu());
        //date configuration
        long miliseconds=listConseil.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(miliseconds)).toString();
        holder.date.setText(dateString);

       holder.view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent detailConseilIntent=new Intent(v.getContext(),DetailconseilActivity.class);
               detailConseilIntent.putExtra("titre",title);
               detailConseilIntent.putExtra("contenu",content);
                context.startActivity(detailConseilIntent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return listConseil.size();
    }

    public class  CoursViewHolder extends RecyclerView.ViewHolder{

        private View view;
        private TextView titre;
        private TextView contenu;
        private TextView date;

        public CoursViewHolder(View itemView) {
            super(itemView);
            view=itemView;

            titre=view.findViewById(R.id.titre);
            contenu=view.findViewById(R.id.contenu);
            date=view.findViewById(R.id.date);
        }



    }
}
