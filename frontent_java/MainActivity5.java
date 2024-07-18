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

public class MainActivity5 extends AppCompatActivity implements ClientCallBack {

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void ButtonClickedHome(View view){
        Intent intent2 = new Intent(this, MainActivity2.class);
        Intent intent = getIntent();
        intent2.putExtra("user",intent.getStringExtra("user"));
        intent2.putExtra("name",intent.getStringExtra("name"));
        startActivity(intent2);
    }

    public void submitButton1(View view){

        List<String> list = new ArrayList<>();
        Intent intent = getIntent();
        list.add(intent.getStringExtra("user"));
        list.add(intent.getStringExtra("name"));
        list.add(intent.getStringExtra("option"));

        EditText e = findViewById(R.id.editTextDate3);
        String dates = e.getText().toString();
        dates = dates + "-" ;

        e = findViewById(R.id.editTextDate4);
        dates += e.getText().toString();
        list.add(dates);


        Client client = MainActivity.getclient() ;
        client.makeData(list,this) ;
    }

    @Override
    public void onCalculationResult(String response) {
        Log.d("MainActivity5", "Calculation result: " + response);
        TextView responseTextView = findViewById(R.id.textView7) ;
        mainHandler.post(() -> responseTextView.setText(response)) ;
    }
    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.getclient().setExit();
        startActivity(intent);
    }
}