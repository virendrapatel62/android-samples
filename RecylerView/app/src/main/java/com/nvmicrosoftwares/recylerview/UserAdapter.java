package com.nvmicrosoftwares.recylerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserAdapter extends RecyclerView.Adapter {

    private User[] users;
    private Context context;
    public UserAdapter(Context context , User[] users) {
        this.users = users;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = (View) LayoutInflater.from(context).inflate(R.layout.user_layout , viewGroup , false);
        UserViewHolder holder = new UserViewHolder(view);
        Log.e("infooo" , holder.toString());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UserViewHolder holder = (UserViewHolder)viewHolder;
        holder.id.setText(users[i].getId().toString());
        holder.name.setText(users[i].getLogin().toString());
        Glide.with(context).load("http://192.168.43.73:8080/nahalo/image.do?image=bathrooms/1/a%20(10).jpg").into(holder.image);
    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView id;
        ImageView image;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userName);
            id = itemView.findViewById(R.id.userID);
            image = itemView.findViewById(R.id.userImage);
        }
    }
}
