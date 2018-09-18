package com.roonit.jikinge.adaptor;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roonit.jikinge.models.Message;
import com.roonit.jikinge.R;

import java.util.Date;
import java.util.List;

public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.MessageHolder>{
    List<Message> messageList;
    Context context;
    public  MessageAdaptor(){}

    public MessageAdaptor(Context context,List<Message> messageList) {
        this.messageList = messageList;
        this.context=context;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_message_item,null);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message messageData=messageList.get(position);
        if (messageData.equals(null)){

        }else {
            String auteur=messageData.getSender();
            String message=messageData.getMessage();


            holder.auteurV.setText(auteur);
            holder.messageV.setText(message);
            //date configuration
            long miliseconds=messageList.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(miliseconds)).toString();
            holder.dateV.setText(dateString);
        }


    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class  MessageHolder extends RecyclerView.ViewHolder{
        private TextView auteurV;
        private TextView messageV;
        private TextView dateV;

        View view;

        public MessageHolder(View itemView) {
            super(itemView);
            view=itemView;

            auteurV=view.findViewById(R.id.auteurV);
            messageV=view.findViewById(R.id.messageV);
            dateV=view.findViewById(R.id.date_messageV);
        }
    }
}
