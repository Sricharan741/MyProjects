package com.marketprices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GlobalActivity extends AppCompatActivity {
    private static String relativeURL=null;
    private VegetableAdapter vegetableAdapter=null;
    private static String title=null;
    private static String previousActivity=null;
    private static final String TAG = GlobalActivity.class.getSimpleName();
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        setTitle(title);
        init();
        fetchPriceDetails();
    }
    private void init(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalActivity.this));
        vegetableAdapter = new VegetableAdapter();
        recyclerView.setAdapter(vegetableAdapter);
    }
    private void fetchPriceDetails(){
        final long start1=System.nanoTime();

        APIService client = ServiceGenerator.createService(APIService.class);

        Single<List<VegetableItemPojo>> call = client.getPrices(relativeURL);
        android.util.Log.i("Service generation TIME",""+(System.nanoTime()-start1));
        final long start2=System.nanoTime();

        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<VegetableItemPojo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onSuccess(List<VegetableItemPojo> vegetableList) {
                android.util.Log.i("Response TIME",""+(System.nanoTime()-start2));
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                if(vegetableList!=null && vegetableList.size()!=0) {
                    vegetableAdapter.setData(vegetableList);
                }
                else{
                    Toast.makeText(GlobalActivity.this,"NO DATA FOUND (contact us)",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Toast.makeText(GlobalActivity.this,"Oops! ERROR IN FETCHING API RESPONSE. Try again(Contact Us)",Toast.LENGTH_SHORT).show();
            }
        });
        android.util.Log.i("Location","Outside callback");
    }
    public static void setRelURLAddress(String stateName,String districtName,String titleName){

        relativeURL = stateName + "/" + districtName;
        //relativeURL="json"; // testing
        //relativeURL="telangana/warangal"; //testing

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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}