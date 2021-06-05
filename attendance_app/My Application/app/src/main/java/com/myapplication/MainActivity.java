package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.textView2);
        t2 = findViewById(R.id.textView4);
        t3 = findViewById(R.id.textView6);

    }
    public void check(android.view.View view){
        int present=0,absent=0;
        float required=0;
        required = Float.parseFloat(t1.getText().toString());
        present = Integer.parseInt(t2.getText().toString());
        absent = Integer.parseInt(t3.getText().toString());
        int totalClasses=present+absent;
        float attendance=(float) present/(totalClasses)*100;
        TextView t4 =findViewById(R.id.textView8);
        TextView t5 =findViewById(R.id.textView9);
        t4.setText(""+attendance);
        if(attendance<=required){
            for(int i=present;i<999;i++){
                float current=(float)i/(i+absent)*100;
                if(current>=required){
                    t5.setText("You need to attend still "+(i-present)+" classes to get "+required+" %.");
                    break;
                }
            }
        }else{
            for(int i=absent;i<999;i++){
                float current=(float)present/(present+i)*100;
                if(current<=required){
                    t5.setText("You can still bunk "+(i-absent)+" classes.");
                    break;
                }
            }
        }
    }
}
