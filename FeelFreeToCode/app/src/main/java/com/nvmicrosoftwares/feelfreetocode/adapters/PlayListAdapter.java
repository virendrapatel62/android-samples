package com.nvmicrosoftwares.feelfreetocode.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nvmicrosoftwares.feelfreetocode.PlayList;
import com.nvmicrosoftwares.feelfreetocode.R;
import com.nvmicrosoftwares.feelfreetocode.Vedio;
import com.nvmicrosoftwares.feelfreetocode.VediosActivity;

import java.util.List;
import java.util.PriorityQueue;

public class PlayListAdapter extends RecyclerView.Adapter {

    private Context context;
    private PlayList[] playLists ;
    public PlayListAdapter(Context context , PlayList[] playLists){
        this.context = context;
        this.playLists = playLists;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final RequestQueue queue = Volley.newRequestQueue(context);
        View v =  (View)(LayoutInflater.from(context).inflate(R.layout.playlist_layout , viewGroup , false));
        final PlayListViewHolder holder = new PlayListViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.43.73:8080/FeelFreeToCode/getvedios.do?name="+((PlayListViewHolder)holder).title.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent i = new Intent(context , VediosActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("response" , response);
                        context.startActivity(i);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context , "Error" , Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

                queue.add(stringRequest);
                Toast.makeText(context, "Clicked On : "+((PlayListViewHolder)holder).title.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PlayListViewHolder holder = (PlayListViewHolder)viewHolder;
        holder.title.setText(playLists[i].getName());
//        holder.description.setText(playLists[i].getDescription());
        holder.lectureCount.setText("Lectures : "+playLists[i].getLectureCount()+"");
        Glide.with(context).load(playLists[i].getImgURL()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return playLists.length;
    }

    public class PlayListViewHolder extends  RecyclerView.ViewHolder{

        ImageView thumbnail ;
        TextView title ;
//        TextView description;
        TextView lectureCount;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);

//            description = itemView.findViewById(R.id.playlist_description);
            thumbnail = itemView.findViewById(R.id.playlist_thumnail);
            title = itemView.findViewById(R.id.playlist_name);
            lectureCount = itemView.findViewById(R.id.lecture_count);
        }

    }
}
