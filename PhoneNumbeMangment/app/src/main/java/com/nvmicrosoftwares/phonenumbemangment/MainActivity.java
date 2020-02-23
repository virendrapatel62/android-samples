package com.nvmicrosoftwares.phonenumbemangment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> numbers ;
    ArrayList<String> types;
    ListView list ;
    EditText number ;
    Spinner selectType;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAll();
        final NumberAdapter adapter = new NumberAdapter();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbers.add(number.getText().toString());
                types.add(selectType.getSelectedItem().toString());
                adapter.notifyDataSetChanged();
                number.setText("");
            }
        });

        list.setAdapter(adapter);
    }

    private void   initAll(){
        numbers = new ArrayList<>();
        types = new ArrayList<>();
        list = findViewById(R.id.list);
        save = findViewById(R.id.button);
        selectType = findViewById(R.id.type);
        number = findViewById(R.id.number);

        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("Phone");
        typeList.add("Home");
        typeList.add("Office");
        ArrayAdapter<String> tts = new ArrayAdapter<>(getApplicationContext() , android.R.layout.simple_list_item_1 , typeList);
        selectType.setAdapter(tts);
    }

    class NumberAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return numbers.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View v  = (View)inflater.inflate(R.layout.list , null);

            EditText t = v.findViewById(R.id.PhoneNumber);
            TextView type = v.findViewById(R.id.Phonetype);
            t.setEnabled(false);

            t.setText(numbers.get(position));
            type.setText(types.get(position));
            return  v;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}
