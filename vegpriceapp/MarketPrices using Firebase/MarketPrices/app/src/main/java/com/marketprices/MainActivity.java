package com.marketprices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static HashMap<String, Integer> hash=null;
    private boolean doubleBackToExitPressedOnce=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton vegButton = findViewById(R.id.imageView);

        vegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StateActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                finish();
            }
        });
        hash=getHash(); // Initializing hashmap
    }

    public static HashMap<String,Integer> getHash(){
        if(hash==null){
            hash= new HashMap<String,Integer>(){{
                put("Amaranth leaves",R.mipmap.amaranth_leaves);
                put("Ash gourd",R.mipmap.ash_gourd);
                put("Banana flower",R.mipmap.banana_flower);
                put("Beetroot",R.mipmap.beetroot);
                put("Bell Pepper (Capsicum)",R.mipmap.bell_pepper_capsicum);
                put("Bitter gourd",R.mipmap.bittergourd);
                put("Bottlegourd",R.mipmap.bottlegourd);
                put("Broad beans (fava beans, lima beans, butter beans)",R.mipmap.broad_beans);
                put("Cabbage",R.mipmap.cabbage);
                put("Carrot",R.mipmap.carrot);
                put("Cauliflower",R.mipmap.cauliflower);
                put("Cluster beans",R.mipmap.clusterbeans);
                put("Coconut (fresh)",R.mipmap.coconut);
                put("Colocasia leaves (Taro leaves)",R.mipmap.colocasia_leaves);
                put("Colocasia roots (Taro roots)",R.mipmap.colocasia_roots);
                put("Coriander leaves (Cilantro)",R.mipmap.coriander_leaves);
                put("Corn",R.mipmap.corn);
                put("Cucumber",R.mipmap.cucumber);
                put("Curry leaves",R.mipmap.curry_leaves);
                put("Dill leaves",R.mipmap.dill_leaves);
                put("Drumsticks",R.mipmap.drumstick_leaves);
                put("Eggplant (Brinjal or Aubergine)",R.mipmap.eggplant);
                put("Elephant Yam",R.mipmap.elephant_yam);
                put("Fenugreek leaves",R.mipmap.fenugreek_leaves);
                put("French Beans (Green beans)",R.mipmap.french_beans);
                put("Garlic",R.mipmap.garlic);
                put("Ginger",R.mipmap.ginger);
                put("Green chili",R.mipmap.green_chilli);
                put("Green onion (Scallian or Spring onion)",R.mipmap.green_onion_leaves);
                put("Green peas",R.mipmap.green_peas);
                put("Ivy gourd",R.mipmap.ivy_gourd);
                put("Jackfruit",R.mipmap.jackfruit);
                put("Lemon (Lime)",R.mipmap.lemon);
                put("Mint leaves",R.mipmap.mint_leaves);
                put("Mushroom",R.mipmap.mushroom);
                put("Mustard leaves",R.mipmap.mustard_leaves);
                put("Okra (Ladies finger)",R.mipmap.ladyfinger);
                put("Onion",R.mipmap.onion);
                put("Plantain (raw banana)",R.mipmap.banana);
                put("Potato",R.mipmap.potato);
                put("Pumpkin",R.mipmap.pumpkin);
                put("Radish (Daikon)",R.mipmap.radish);
                put("Ridge gourd",R.mipmap.ridge_gourd);
                put("Shallot (pearl onion)",R.mipmap.shallot_onion);
                put("Snake gourd",R.mipmap.snake_gourd);
                put("Sorrel leaves",R.mipmap.sorrel_leaves);
                put("Spinach",R.mipmap.spinach);
                put("Sweet potato",R.mipmap.sweet_potato);
                put("Tomato",R.mipmap.tomato);
            }};
        }
        return hash;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int itemId = item.getItemId();

        if (itemId == R.id.about) {
            startActivity(new Intent(this, About.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void aboutButton(View view)
    {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
        finish();
    }
    public void exitButton(@SuppressWarnings("unused") View view)
    {
        super.onBackPressed();
    }
    public void shareButton(View view)
    {
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareSubject="Take a look at \"Market Prices in TS & AP\"";
        String shareBody="https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(shareIntent,"Share using"));
    }
    public void rateButton(View view)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
    }
    @Override
    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
