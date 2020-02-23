package com.feelfreetocode.findbestinstitute.adapters;

import android.content.Context;
import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feelfreetocode.findbestinstitute.R;

import org.w3c.dom.Text;

public class InstituteHomeOptionsAdapter extends BaseAdapter {

    private String[] options ;
    private Integer[] showProgress =  new Integer[]{4, 4, 4, 0} ;
    private String[] notifications = new String[]{"" , "" , "" , ""};
    int[] images ;
    private Context context;
    public InstituteHomeOptionsAdapter(Context context  , String[] options , int[] images){
        this.images = images;
        this.options = options;
        this.context = context;
    }
    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int position) {
        return options[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String[] getNotifications(){
        return  notifications;
    }

    public Integer[] getShowProgress() {
        return showProgress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = (View)LayoutInflater.from(context).inflate(R.layout.institute_home_layout , null);
        TextView title = v.findViewById(R.id.title);
        ImageView image= v.findViewById(R.id.icon);
        image.setImageResource(images[position]);
        TextView noti = v.findViewById(R.id.notification_Coount);
        ProgressBar progressBar= v.findViewById(R.id.loadind);
        noti.setText(notifications[position]);
        progressBar.setVisibility(showProgress[position]);
        title.setText(options[position]);
        if(!notifications[position].isEmpty()){
            noti.setPadding(12,12,12,12);
        }
        return v;
    }
}
