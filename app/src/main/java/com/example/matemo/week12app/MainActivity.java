package com.example.matemo.week12app;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnEdit;
    ListView lvKata;
    ArrayList<String> arrKata = new ArrayList<>();
    ArrayAdapter<String> adapter;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        lvKata = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrKata);
        lvKata.setAdapter(adapter);

        registerForContextMenu(lvKata);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.itemAdd)
        {
            counter++;
            arrKata.add("Data ke "+counter);
            adapter.notifyDataSetChanged();
        }
        else if(id==R.id.itemDeleteAll)
        {
            counter=0;
            arrKata.clear();
            adapter.notifyDataSetChanged();
        }
        else if(id==R.id.itemAbout)
        {
            Intent aboutIntent = new Intent(MainActivity.this, about.class);
            startActivity(aboutIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if(id==R.id.itemDelete)
        {
            arrKata.remove(info.position);
            adapter.notifyDataSetChanged();
        }
        else if(id==R.id.itemShow)
        {
            Toast.makeText(MainActivity.this, arrKata.get(info.position), Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    public void showPopup(View view)
    {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.itemRed)
                {
                    lvKata.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_light));
                }
                else if(id==R.id.itemBlue)
                {
                    lvKata.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_blue_light));
                }
                else if(id==R.id.itemGreen)
                {
                    lvKata.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_light));
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
