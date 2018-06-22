package com.example.sandy.getbooks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private SearchView searchView;
    private String[] data = new String[]{"one","two","three"};
    private List<BooksModel> booksModel;
    private BrowseBooksAdapter browseBooksAdapter;
    private int columnNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);


        // Attempt to launch an activity within our own app
        Button btnBrowse = (Button)findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
               Intent intent = new Intent(BrowseActivity.this,BookDetailsActivity.class);
               intent.putExtra("Rick Riordan",  "@+id/editTextAuthor");
               startActivity(intent);
            }
        });


        columnNumbers = 2;
        booksModel = new ArrayList<>();
        browseBooksAdapter = new BrowseBooksAdapter(this, booksModel);

        GridLayoutManager layoutManager = new GridLayoutManager(this, columnNumbers);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerBooks);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(browseBooksAdapter);

        AddBooks();

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

    public void AddBooks(){
        BooksModel a = new BooksModel(1, "title2", 1, "100", "test2", 10, 1);
        BooksModel b = new BooksModel(1, "title", 1, "100", "test", 10, 1);
        BooksModel c = new BooksModel(1, "title2", 1, "100", "test2", 10, 1);
        BooksModel d = new BooksModel(1, "title", 1, "100", "test", 10, 1);
        booksModel.add(a);
        booksModel.add(b);
        booksModel.add(c);
        booksModel.add(d);
        browseBooksAdapter.notifyDataSetChanged();
    }


}
