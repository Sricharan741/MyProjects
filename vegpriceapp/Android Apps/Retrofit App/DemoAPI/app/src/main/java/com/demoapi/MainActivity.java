package com.demoapi;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int[] imageId={R.drawable.ic_android,R.drawable.ic_audio,R.drawable.ic_sun,R.drawable.ic_android,R.drawable.ic_audio};

        final ArrayList<ExampleItem> exampleList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/shivashashank/JsonApi/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful())
                {
                    //mTextView1.setText("Code: "+ response.code());
                    Toast.makeText(MainActivity.this,""+ response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Post> posts = response.body();
                int i=0;
                for(Post post : posts)
                {
                    exampleList.add(new ExampleItem(imageId[i++],""+post.getTitle(),""+post.getBody()));
                }
                mRecyclerView = findViewById(R.id.recyclerView);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mAdapter = new ExampleAdapter(exampleList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //mTextView1.setText(t.getMessage());
                Toast.makeText(MainActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
