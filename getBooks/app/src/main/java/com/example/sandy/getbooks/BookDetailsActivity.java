package com.example.sandy.getbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        if(getIntent().hasExtra("com.example.sandy.getbooks")){
            //TextView tv = (TextView) findViewById(R.id.textViewTitle);
            // String text = getIntent().getExtras().getString("com.example.sandy.getbooks");
           //tv.setText(text);

            setContentView(R.layout.activity_bookdetails);
        }
    }
}
