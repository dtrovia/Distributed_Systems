package com.example.tryvol2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String name;
    private static Client client;
    private static Thread clientThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Start the client thread
        client = new Client();
        clientThread = new Thread(client);
        clientThread.start();


    }
    public void onButtonClicked(View view) {
        EditText e = findViewById(R.id.editTextText2);
        name = e.getText().toString();
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("user","m");
        intent.putExtra("name",name);
        Log.d("1111111",intent.getStringExtra("user"));
        startActivity(intent);
    }
    public void onButtonClicked2(View view) {
        EditText e = findViewById(R.id.editTextText2);
        name = e.getText().toString();
        Intent intent = new Intent(this, MainActivity3.class);
        intent.putExtra("user","t");
        intent.putExtra("name",name);
        startActivity(intent);

    }
    public static Client getclient(){return client;}



}