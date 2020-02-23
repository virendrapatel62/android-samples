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
import com.nvmicrosoftwares.feelfreetocode.PlayVedio;
import com.nvmicrosoftwares.feelfreetocode.R;
import com.nvmicrosoftwares.feelfreetocode.Vedio;
import com.nvmicrosoftwares.feelfreetocode.VediosActivity;
import com.universalvideoview.UniversalVideoView;

import java.io.File;

import bg.devlabs.fullscreenvideoview.FullscreenVideoView;

public class VedioAdapter extends RecyclerView.Adapter {
    Context context ;
    Vedio[] vedios;
    FullscreenVideoView videoView;
    public VedioAdapter(Context context , Vedio[] vedios ){
        this.vedios = vedios;
          this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        final int f = i;
        View v = (View) (LayoutInflater.from(context).inflate(R.layout.vedio_layout, viewGroup, false));
        VedioHolder holder = new VedioHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        VedioHolder v = (VedioHolder)viewHolder;
        Glide.with(context).load(vedios[i].getImgURL()).into(v.thumbnail);
        v.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, i+""+vedios[i].getVedioURL(), Toast.LENGTH_SHORT).show();

                Intent it = new Intent(context , PlayVedio.class);
                it.putExtra("url" , vedios[i].getVedioURL());

                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
//                videoView.videoFile(new File(vedios[i].getVedioURL()));
            }
        });
        v.title.setText(vedios[i].getName());
    }

    @Override
    public int getItemCount() {
        return vedios.length;
    }

     class VedioHolder extends  RecyclerView.ViewHolder{

        ImageView thumbnail ;
        TextView title ;
        View view ;


        public VedioHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.vedio_thumnail);
            thumbnail.setImageResource(R.drawable.ic_launcher_background);
            title = itemView.findViewById(R.id.vedio_title);
            this.view = itemView;
        }

    }
}
