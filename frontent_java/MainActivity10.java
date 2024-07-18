package com.example.tryvol2;

import android.content.Intent;
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

public class MainActivity10 extends AppCompatActivity implements ClientCallBack {

    private String option;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main10);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<String> proxeiro = new ArrayList<>();
        Intent intent = getIntent();
        proxeiro.add(intent.getStringExtra("user"));
        proxeiro.add(intent.getStringExtra("name"));
        option = "10";
        proxeiro.add(option);
        proxeiro.add(intent.getStringExtra("roomName"));

        Client client = MainActivity.getclient() ;
        client.makeData(proxeiro,this) ;

        TextView t = findViewById(R.id.textView3);
        t.setText(getIntent().getStringExtra("roomName"));
    }
    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.getclient().setExit();
        startActivity(intent);
    }
    public void BookRoom(View view){
        option = "2";
        List<String> proxeiro = new ArrayList<>();
        Intent intent = getIntent();
        proxeiro.add(intent.getStringExtra("user"));
        proxeiro.add(intent.getStringExtra("name"));
        proxeiro.add(option);
        proxeiro.add(intent.getStringExtra("roomName"));

        EditText e = findViewById(R.id.editTextDate5);
        String dates = e.getText().toString();
        dates = dates + "-" ;

        e = findViewById(R.id.editTextDate6);
        dates += e.getText().toString();
        proxeiro.add(dates);

        Client client = MainActivity.getclient() ;
        client.makeData(proxeiro,this) ;


    }
    public void RateRoom(View view){
        option = "3";
        List<String> proxeiro = new ArrayList<>();
        Intent intent = getIntent();
        proxeiro.add(intent.getStringExtra("user"));
        proxeiro.add(intent.getStringExtra("name"));
        proxeiro.add(option);
        proxeiro.add(intent.getStringExtra("roomName"));
        EditText e = findViewById(R.id.editTextNumber2);
        proxeiro.add(e.getText().toString());

        Client client = MainActivity.getclient() ;
        client.makeData(proxeiro,this) ;
    }
    public void ButtonClickedHome(View view){
        Intent intent2 = new Intent(this, MainActivity3.class);
        Intent intent = getIntent();
        intent2.putExtra("user",intent.getStringExtra("user"));
        intent2.putExtra("name",intent.getStringExtra("name"));
        startActivity(intent2);
    }

    @Override
    public void onCalculationResult(String response) {
        Log.d("10101010",response);
        TextView responseTextView;
        if(option.equals("2")){
            responseTextView = findViewById(R.id.bookresults) ;
        }else if (option.equals("3")){
            responseTextView = findViewById(R.id.rateResults) ;
        }else{
            responseTextView = findViewById(R.id.roomDetails);
        }
        mainHandler.post(() -> responseTextView.setText(response));
    }
}