package com.example.sandy.getbooks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.List;

public class EditBookActivity extends AppCompatActivity {

    private SearchView searchView;
    private String[] data = new String[]{"one","two","three"};
    private List<BooksModel> booksModel,booksModelCopy;
    private BrowseBooksAdapter browseBooksAdapter;
    private int columnNumbers;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbook);
    }
}
