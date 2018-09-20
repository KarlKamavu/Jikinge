package com.roonit.jikinge.adaptor;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.roonit.jikinge.models.Post;
import com.roonit.jikinge.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder> {

    Context context;
    List<Post> postList;
    private FirebaseFirestore firebaseFirestore;
    public PostRecyclerAdapter(Context context,List<Post> postList){
        this.context=context;
        this.postList=postList;

    }


    @Override
    public PostViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_message_item, parent, false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
    final Post post= postList.get(position);

        holder.message.setText(post.getContenu());
        holder.titre.setText(post.getTitre());
        holder.setImageView(context,post.getImage());
        String userID=postList.get(position).getAuteurID();

        firebaseFirestore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    String userName=task.getResult().get("name").toString();
                    String userAvatar=task.getResult().get("image").toString();
                    holder.setUserData(context,userName,userAvatar);
                }
            }
        });


        //date configuration
        long miliseconds=postList.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(miliseconds)).toString();
        holder.date.setText(dateString);


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public  class PostViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView titre,message;
        private ImageView imageView;
        private TextView date,userName;
        private CircleImageView useravatar;

        private ImageButton imgShare;

        public PostViewHolder(View itemView) {
            super(itemView);

            mView=itemView;
            titre=mView.findViewById(R.id.titre_post_item);
            message=mView.findViewById(R.id.blog_desc);
            date=mView.findViewById(R.id.blog_date);
            userName=mView.findViewById(R.id.username);
            useravatar=mView.findViewById(R.id.userAvatar);
        }

        public  void setImageView(Context context,String downloadImage){
            imageView=mView.findViewById(R.id.blog_image);
            //Glide.with(context).load(downloadImage).into(imageView);
            Picasso.get().load(downloadImage).into(imageView);
        }
        public void setUserData(Context context,String name, String image){
            userName.setText(name);
           // Glide.with(context).load(image).into(useravatar);
            Picasso.get().load(image).into(useravatar);
        }



    }
}
