package com.example.virtualtravelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualtravelapp.R;
import com.example.virtualtravelapp.adapter.AdminDiaDanhAdapter;
import com.example.virtualtravelapp.database.DBManager;
import com.example.virtualtravelapp.model.DiaDanh;

import java.util.ArrayList;

public class AdminHotelActivity extends AppCompatActivity {

    ListView listView;
    Button btnThem;
    ArrayList<DiaDanh> list;
    AdminDiaDanhAdapter adapter;
    DBManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindiadanh);
        this.setTitle("Quản lý địa điểm nghỉ dưỡng");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new DBManager(this);
        listView = (ListView) findViewById(R.id.lv_DiaDanh);
        btnThem = (Button) findViewById(R.id.btn_AdminDiaDanhAdd);
        list = new ArrayList<>();
        list = database.getDiaDanh();
        adapter = new AdminDiaDanhAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHotelActivity.this, InsertDoAnActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }
}
