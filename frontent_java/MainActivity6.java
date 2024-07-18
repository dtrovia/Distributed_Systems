package com.example.tryvol2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Looper;
import androidx.core.view.WindowInsetsCompat.Type;


public class MainActivity6 extends AppCompatActivity implements ClientCallBack {

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private TextView responseTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main6);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<String> list = new ArrayList<>();
        Intent intent = getIntent();
        list.add(intent.getStringExtra("user"));
        list.add(intent.getStringExtra("name"));
        list.add(intent.getStringExtra("option"));

        Client client = MainActivity.getclient() ;
        client.makeData(list,this) ;

    }

    @Override
    public void onCalculationResult(String response) {
        Log.d("MainActivity6", "Calculation result: " + response);
        responseTextView = findViewById(R.id.results);
        if(response.equals("")){
            mainHandler.post(() -> responseTextView.setText("No reservations found"));
        }else{
            mainHandler.post(() -> responseTextView.setText(response));
        }


    }

    public void onButtonClickedHome(View view){
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
