package com.example.tryvol2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void onButtonClicked3(View view) {
        Intent intent1 = new Intent(this, MainActivity6.class);
        Intent intent = getIntent();
        intent1.putExtra("user",intent.getStringExtra("user"));
        intent1.putExtra("name",intent.getStringExtra("name"));
        intent1.putExtra("option","3");
        startActivity(intent1);
    }
    public void onButtonClicked2(View view) {
        Intent intent1 = new Intent(this, MainActivity7.class);
        Intent intent = getIntent();
        intent1.putExtra("user",intent.getStringExtra("user"));
        intent1.putExtra("name",intent.getStringExtra("name"));
        Log.d("22222222",intent1.getStringExtra("name"));
        intent1.putExtra("option","2");
        startActivity(intent1);
    }
    public void onButtonClicked1(View view) {
        Intent intent1 = new Intent(this, MainActivity4.class);
        Intent intent = getIntent();
        intent1.putExtra("user",intent.getStringExtra("user"));
        intent1.putExtra("name",intent.getStringExtra("name"));
        intent1.putExtra("option","1");
        startActivity(intent1);
    }
    public void onButtonClicked4(View view) {
        Intent intent1 = new Intent(this, MainActivity5.class);
        Intent intent = getIntent();
        intent1.putExtra("user",intent.getStringExtra("user"));
        intent1.putExtra("name",intent.getStringExtra("name"));
        intent1.putExtra("option","5");//4 exit
        startActivity(intent1);
    }

    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.getclient().setExit();
        startActivity(intent);
    }


}