package com.example.tryvol2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity8 extends AppCompatActivity {

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main8);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //response from server
        TextView responseTextView = findViewById(R.id.resultbox) ;
        String response = getIntent().getStringExtra("response");
        mainHandler.post(() -> responseTextView.setText(response));
    }

    public void ButtonClickedBack(View view){
        Intent intent2 = new Intent(this, MainActivity4.class);
        Intent intent = getIntent();
        intent2.putExtra("user",intent.getStringExtra("user"));
        intent2.putExtra("name",intent.getStringExtra("name"));
        intent2.putExtra("option",intent.getStringExtra("option"));
        startActivity(intent2);
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