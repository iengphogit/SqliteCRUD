package com.avaboy.btb.crudsqlite.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.avaboy.btb.crudsqlite.R;
import com.avaboy.btb.crudsqlite.adapter.ContactAdapter;
import com.avaboy.btb.crudsqlite.model.ContactModel;
import com.avaboy.btb.crudsqlite.model.DBHandler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "my_log";
    private RecyclerView recyclerView;
    private List<ContactModel> contactModelList;
    private DBHandler dbHandler;
    private ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        dbHandler = new DBHandler(this);
        contactModelList = new ArrayList<>();
        contactModelList = dbHandler.getAllContacts();
        contactAdapter = new ContactAdapter(contactModelList);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        contactModelList.clear();
        contactModelList.addAll(dbHandler.getAllContacts());
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_add){
            doActionAdd();
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void doActionAdd(){
        Log.d(TAG, "doActionAdd: ");
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }
}
