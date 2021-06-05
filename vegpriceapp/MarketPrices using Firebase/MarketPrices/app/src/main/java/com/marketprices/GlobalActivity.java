package com.marketprices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GlobalActivity extends AppCompatActivity {
    private static String relativeURL=null;
    private static String state=null;
    private VegetableAdapter vegetableAdapter=null;
    private static String title=null;
    private static String previousActivity=null;
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final DatabaseReference reference=database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        setTitle(title);
        init();
        fetchPriceDetails();
    }
    private void fetchPriceDetails() {
        final long s1=System.nanoTime();
        reference.child("{date,state,district}").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long response_time=(System.nanoTime()-s1);
                final long start1=System.nanoTime();
                List<VegetableItem> vegetableList=new ArrayList<>();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                if(snapshot.exists()) {
                    for (DataSnapshot children : snapshot.getChildren()) {
                        VegetableItem object = children.getValue(VegetableItem.class);
                        vegetableList.add(object);
                        //android.util.Log.i("Response", "" + object.getVegname());
                    }
                    /*Type listType = new TypeToken<List<VegetableItem>>(){}.getType();
                    StringBuilder json_string =new StringBuilder(snapshot.getValue().toString());
                    json_string.setCharAt(0,'[');
                    json_string.setCharAt(json_string.length()-1,']');
                    android.util.Log.i("Response",json_string.toString());
                    List<VegetableItem> vegetableList=new Gson().fromJson(json_string.toString(),listType);*/
                    vegetableAdapter.setData(vegetableList);
                    long total_time=response_time+(System.nanoTime()-start1);
                    Log.i("TIME TAKEN(nano)",""+total_time);
                    Log.i("TIME TAKEN(seconds)",""+(float)(total_time)/1000000000);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GlobalActivity.this,"Oops! ERROR IN FETCHING RESPONSE. Try again(Contact Us)",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void init(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalActivity.this));
        vegetableAdapter = new VegetableAdapter();
        recyclerView.setAdapter(vegetableAdapter);
    }

    public static void setRelURLAddress(String stateName,String districtName,String titleName){

        relativeURL = stateName + "/" + districtName;
        state=stateName;
        //relativeURL="json"; // testing
        //relativeURL="telangana/warangal"; //testing
        relativeURL="vegetable";
        title=titleName;
    }

    public static void setPreviousActivity(String previousActivity){
        GlobalActivity.previousActivity=previousActivity; // set previousActivity
    }
    @Override
    public void onBackPressed() {
        switch (previousActivity) {
            case "VegDistrictsTs":
                startActivity(new Intent(this, VegDistrictsTs.class));
                break;
            case "VegDistrictsAp":
                startActivity(new Intent(this, VegDistrictsAp.class));
                break;
        }
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}