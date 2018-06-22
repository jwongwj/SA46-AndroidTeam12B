package com.example.sandy.getbooks;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

public class BrowseActivity extends AppCompatActivity {

    private SearchView searchView;
    private String[] data = new String[]{"one","two","three"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(data[0].contains(query)){
//                    adapter.getFilter().filter(query);
                    Toast.makeText(BrowseActivity.this, "Match found", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(BrowseActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(data[0].contains(newText)){
//                    adapter.getFilter().filter(query);
                    Toast.makeText(BrowseActivity.this, "Match found", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(BrowseActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setCancelable(true)
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                        System.exit(0);
                    }
                }).create().show();
    }



}
