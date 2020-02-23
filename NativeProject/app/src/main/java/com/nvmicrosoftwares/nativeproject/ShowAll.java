package com.nvmicrosoftwares.nativeproject;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nvmicrosoftwares.nativeproject.models.UrgentCaller;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowAll extends AppCompatActivity {

    private ListView listView ;
    private MyAdapter adapter ;
    private UrgentCaller selected ;
    ArrayList<UrgentCaller> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        listView = findViewById(R.id.list);
        adapter = new MyAdapter();

        data = Database.selectAllFromUrgentCallers(getApplicationContext());
        listView.setAdapter(adapter);
        data.add(0 , new UrgentCaller("Name" , "Number"));
        adapter.notifyDataSetChanged();
        registerForContextMenu(listView);

        if(getIntent().getStringExtra("msg")!=null){
           String ms = getIntent().getStringExtra("msg");
           Toast.makeText(getApplicationContext() , ms , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.delete_context_menu , menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete){
            AdapterView.AdapterContextMenuInfo ad = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            UrgentCaller caller = data.get(ad.position);
            if(Database.deleteFromUrgentCallers(getApplicationContext() , caller)){
                data.remove(caller);

                adapter.notifyDataSetChanged();
            }

        }
        return super.onContextItemSelected(item);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = (View) LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_layout_show_numbers , null);
            TextView nm = ((TextView)view.findViewById(R.id.name));
            nm.setText(data.get(position).getName());
            TextView num = ((TextView)view.findViewById(R.id.number));
            num.setText(data.get(position).getNumber());

            if(position==0) {
                num.setTextSize(28);
                nm.setTextSize(28);
                nm.setTextColor(Color.parseColor("#FBD28B"));
                num.setTextColor(Color.parseColor("#FBD28B"));
            }
            return view;
        }
    }

}
