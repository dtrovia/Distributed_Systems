package com.example.tryvol2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity implements ClientCallBack {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void submitButton(View view){

        List<String> list = new ArrayList<>();
        Intent intent = getIntent();
        list.add(intent.getStringExtra("user"));
        list.add(intent.getStringExtra("name"));
        list.add(intent.getStringExtra("option"));

        EditText e = findViewById(R.id.editTextText);//roomName
        list.add(e.getText().toString());

        e = findViewById(R.id.editTextNumber);//capacity
        list.add(e.getText().toString());

        e = findViewById(R.id.editTextText3);//area
        list.add(e.getText().toString());

        e = findViewById(R.id.editTextNumberDecimal);//price
        list.add(e.getText().toString());

        e = findViewById(R.id.editTextText4);//image
        list.add(e.getText().toString());

        Client client = MainActivity.getclient() ;
        client.makeData(list,this) ;
    }

    @Override
    public void onCalculationResult(String response) {
        Log.d("MainActivity4", "Calculation result: " + response);
        Intent intent1 = new Intent(this, MainActivity8.class);
        Intent intent = getIntent();
        intent1.putExtra("user",intent.getStringExtra("user"));
        intent1.putExtra("name",intent.getStringExtra("name"));
        intent1.putExtra("option",intent.getStringExtra("option"));
        intent1.putExtra("response",response) ;
        startActivity(intent1);
    }

    public void ButtonClickedHome(View view){
        Intent intent2 = new Intent(this, MainActivity2.class);
        Intent intent = getIntent();
        intent2.putExtra("user",intent.getStringExtra("user"));
        intent2.putExtra("name",intent.getStringExtra("name"));
        startActivity(intent2);
    }
    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.getclient().setExit();
        startActivity(intent);
    }
}