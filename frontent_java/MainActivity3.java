package com.example.tryvol2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Button;


import java.util.ArrayList;
import java.util.List;



public class MainActivity3 extends AppCompatActivity implements ClientCallBack{

   private List<String> data = new ArrayList<>();
   private String selectedOption;
   private Handler mainHandler = new Handler(Looper.getMainLooper());
   private List<String> results = new ArrayList<>();
   private int i=3;
   private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedOption = parentView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }
    public void onButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.getclient().setExit();
        startActivity(intent);
    }
    public void onButtonSearchClicked(View view){
        //call client and request search
        data.add("exit");
        List<String> proxeiro = new ArrayList<>();
        Intent intent = getIntent();
        proxeiro.add(intent.getStringExtra("user"));
        proxeiro.add(intent.getStringExtra("name"));
        proxeiro.add("1");
        proxeiro.addAll(data);
        Client client = MainActivity.getclient() ;
        client.makeData(proxeiro,this) ;
    }
    public void onButtonFilterClicked(View view){
        data.add(selectedOption);
        EditText e = findViewById(R.id.editTextText8);
        String value = e.getText().toString();
        data.add(value);
        counter++;
        TextView t = findViewById(R.id.textView8);
        t.setText("NumofFilters "+counter);
    }

    @Override
    public void onCalculationResult(String response) {

        List<String> resultList = Serializer.deserializeList(response);
        // Assuming you have a ScrollView in your layout file with id scrollview
        ScrollView scrollView = findViewById(R.id.results);
        // Assuming you have a LinearLayout inside the ScrollView with id linear_layout
        LinearLayout linearLayout = scrollView.findViewById(R.id.linearResults);

        Context co = this;

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                linearLayout.removeAllViews();

                for (String result : resultList) {
                    Button button = new Button(co);
                    button.setText(result);

                    button.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1 = new Intent(co, MainActivity10.class);
                            //pass info
                            Intent intent = getIntent();
                            intent1.putExtra("user",intent.getStringExtra("user"));
                            intent1.putExtra("name",intent.getStringExtra("name"));
                            Button clickedButton = (Button) v;
                            intent1.putExtra("roomName",clickedButton.getText().toString());
                            startActivity(intent1);
                        }
                    });
                    linearLayout.addView(button);
                }

                // Set the content description for the ScrollView
                scrollView.setContentDescription("Results list");
            }

        });

    }

    


}