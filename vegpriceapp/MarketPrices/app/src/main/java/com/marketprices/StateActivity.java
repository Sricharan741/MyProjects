package com.marketprices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class StateActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listView;
    private final String [] STATES = {"Andhra Pradesh", "Telangana"};
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state_activity);

        SearchView searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, STATES);

        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id ){
                String stateName = (String) StateActivity.this.listView.getItemAtPosition(position);
                Intent intent=null;
                switch (position) {
                    case 0:
                        intent=new Intent(view.getContext(), VegDistrictsAp.class);
                        VegDistrictsAp.setState(stateName.toLowerCase().replaceAll("\\s+",""));
                        break;
                    case 1:
                        intent=new Intent(view.getContext(), VegDistrictsTs.class);
                        VegDistrictsTs.setState(stateName.toLowerCase().replaceAll("\\s+",""));
                        break;
                }
                if(intent != null){
                    StateActivity.this.startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    StateActivity.this.finish();
                }
            }

        });
    }
    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}