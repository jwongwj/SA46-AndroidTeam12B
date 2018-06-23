package com.example.sandy.getbooks;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private SearchView searchView;
    private String[] data = new String[]{"one","two","three"};
    private List<BooksModel> booksModel,booksModelCopy;
    private BrowseBooksAdapter browseBooksAdapter;
    private int columnNumbers;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        columnNumbers = 2;
        booksModel = new ArrayList<>();
        booksModelCopy = new ArrayList<>();

        setContentView(R.layout.activity_browse);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, columnNumbers);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerBooks);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        browseBooksAdapter = new BrowseBooksAdapter(this, booksModel);
        recyclerView.setAdapter(browseBooksAdapter);

        AddBooks(booksModel);
    }

    public void filter(String charText,List<BooksModel> originalData){
        List<BooksModel> copiedData= new ArrayList<>();
        AddBooks(copiedData); //copiedData now contains original list of data

        charText = charText.toLowerCase();

        originalData.clear();

        if (charText.length() == 0) {
            originalData.addAll(copiedData);
        } else {
            for (BooksModel item : copiedData) {
                if (item.getTitle().toLowerCase().contains(charText)|| item.getAuthor().toLowerCase().contains(charText)
                        || String.valueOf(item.getBookID()).toLowerCase().contains(charText)
                        || String.valueOf(item.getCategoryID()).toLowerCase().contains(charText)
                        || String.valueOf(item.getISBN()).toLowerCase().contains(charText)) {
                    originalData.add(item);
                }
            }
        }

        browseBooksAdapter = new BrowseBooksAdapter(this, originalData);
        recyclerView.setAdapter(browseBooksAdapter);
        browseBooksAdapter.notifyDataSetChanged();
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

    public void AddBooks(List<BooksModel> list){
        BooksModel a = new BooksModel(1, "title2", 1, "100", "test2", 10, 1);
        BooksModel b = new BooksModel(1, "title", 1, "100", "test", 10, 1);
        BooksModel c = new BooksModel(1, "title2", 1, "100", "test2", 10, 1);
        BooksModel d = new BooksModel(1, "title", 1, "100", "test", 10, 1);
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText,booksModel); //this. ???
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.searchView) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
