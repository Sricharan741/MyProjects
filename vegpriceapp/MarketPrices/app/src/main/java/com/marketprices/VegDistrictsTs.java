package com.marketprices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class VegDistrictsTs extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private final String [] DISTRICTS = {"Adilabad", "Hyderabad","Karimnagar","Khammam","Mahbubnagar","Nalgonda","Nizamabad","Warangal"};
    private ArrayAdapter<String> adapter;
    private static String stateName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veg_districts_ts);

        SearchView searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, DISTRICTS);

        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String titleDistrictName = (String) VegDistrictsTs.this.listView.getItemAtPosition(position);

                String UrlDistrictName = titleDistrictName.trim().toLowerCase().replaceAll("\\s","_");

                GlobalActivity.setPreviousActivity(VegDistrictsTs.this.getClass().getSimpleName());

                GlobalActivity.setRelURLAddress(stateName, UrlDistrictName, titleDistrictName);

                VegDistrictsTs.this.startActivity(new Intent(view.getContext(), GlobalActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                VegDistrictsTs.this.finish();
            }
        });
    }
    public static void setState(String stateName){
        VegDistrictsTs.stateName=stateName; //set stateName from StateActivity.java
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, StateActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}