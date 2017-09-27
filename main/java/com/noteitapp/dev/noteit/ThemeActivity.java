package com.noteitapp.dev.noteit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button selectorButton = (Button) findViewById(R.id.theme_button);

        GridView colorGrid = (GridView) findViewById(R.id.theme_grid);
        GridAdapter colorAdapter = new GridAdapter();
        colorGrid.setAdapter(colorAdapter);

        colorGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences sharedpreferences = getSharedPreferences("theme", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("theme", position);
                editor.commit();
                finish();

            }
        });
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 22;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.theme_list_item,parent,false);

            View v = view.findViewById(R.id.color);
            v.setBackground(getResources().getDrawable(MainActivity.gradients[position]));
            return view;
        }
    }

}
