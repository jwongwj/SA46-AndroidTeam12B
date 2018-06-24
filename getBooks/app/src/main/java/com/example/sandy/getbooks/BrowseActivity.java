package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sandy.getbooks.Models.Book;
import com.example.sandy.getbooks.Models.Category;

import java.util.ArrayList;
import java.util.List;

import static com.example.sandy.getbooks.Models.Category.getCategory;
import static com.example.sandy.getbooks.Models.Category.listCategory;

public class BrowseActivity extends AppCompatActivity {

    private SearchView searchView;
    private List<BooksModel> booksModel,booksModelCopy;
    private List<Book> bookList;
    private List<Category> categoryList;
    private BrowseBooksAdapter browseBooksAdapter;
    private int columnNumbers;
    private RecyclerView recyclerView;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        columnNumbers = 2;
        booksModel = new ArrayList<>();
        booksModelCopy = new ArrayList<>();

        setContentView(R.layout.activity_browse);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, columnNumbers);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerBooks);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        new AsyncTask<Void, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(Void... params) {
                bookList=Book.listBook(); //can't do this.bookList?

                return Book.listBook();
            }

            @Override
            protected void onPostExecute(List<Book> result) {
                BrowseBooksAdapter adapter = new BrowseBooksAdapter(getApplicationContext(), result);
                recyclerView.setAdapter(adapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filter(newText,Book.listBook()); //this. ???
                        return true;
                    }
                });
            }
        }.execute();

        new AsyncTask<Void, Void, List<Category>>() {

            @Override
            protected List<Category> doInBackground(Void... params) {
                categoryList = Category.listCategory2();

                return Category.listCategory2();
            }
            @Override
            protected void onPostExecute(List<Category> result) {

            }
        }.execute();

    }

    public void filter(String charText,List<Book> originalData){
        List<Book> copiedData= new ArrayList<>();
        copiedData=bookList; //copiedData now contains original list of data

        charText = charText.toLowerCase();

        originalData.clear();

        if (charText.length() == 0) {
            originalData.addAll(copiedData);
        } else {
            for (Book item : copiedData) {
                String categoryName = null;

                for(Category c: categoryList){
                    if(c.get("CategoryID").equals(item.get("CategoryID"))){
                       categoryName=c.get("Name");
                   }
                }

                if (item.get("Author").toLowerCase().contains(charText)|| item.get("ISBN").toLowerCase().contains(charText)
//                        || String.valueOf(item.getBookID()).toLowerCase().contains(charText)
                        || categoryName.toLowerCase().contains(charText)
                        || String.valueOf(item.get("Title")).toLowerCase().contains(charText)) {
                    originalData.add(item);

                }
            }
        }

        browseBooksAdapter = new BrowseBooksAdapter(this, originalData);
        recyclerView.setAdapter(browseBooksAdapter);
        browseBooksAdapter.notifyDataSetChanged();
    }

    public void filterCategory(String charText,List<Book> originalData){
        List<Book> copiedData= new ArrayList<>();
        copiedData=bookList; //copiedData now contains original list of data

        charText = charText.toLowerCase();

        originalData.clear();

        if (charText.length() == 0) {
            originalData.addAll(copiedData);
        } else {
            for (Book item : copiedData) {
                String categoryName = null;

                for(Category c: categoryList){
                    if(c.get("CategoryID").equals(item.get("CategoryID"))){
                        categoryName=c.get("Name");
                    }
                }

                if (categoryName.toLowerCase().contains(charText)) {
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();

        new AsyncTask<Void, Void, List<Book>>() {
            @Override
            protected List<Book> doInBackground(Void... params) {
                bookList=Book.listBook(); //can't do this.bookList?

                return Book.listBook();
            }

            @Override
            protected void onPostExecute(List<Book> result) {
                BrowseBooksAdapter adapter = new BrowseBooksAdapter(getApplicationContext(), result);
                recyclerView.setAdapter(adapter);

                MenuItem item0 = menu.findItem(R.id.item0);
                item0.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BrowseActivity.this.filter("",Book.listBook());
                        return true;
                    }
                });

                MenuItem item2 = menu.findItem(R.id.item2);
                item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BrowseActivity.this.filterCategory("Children",Book.listBook());
                        return true;
                    }
                });

                MenuItem item3 = menu.findItem(R.id.item3);
                item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BrowseActivity.this.filterCategory("Finance",Book.listBook());
                        return true;
                    }
                });

                MenuItem item4 = menu.findItem(R.id.item4);
                item4.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BrowseActivity.this.filterCategory("Non-fiction",Book.listBook());
                        return true;
                    }
                });

                MenuItem item5 = menu.findItem(R.id.item5);
                item5.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BrowseActivity.this.filterCategory("Technical",Book.listBook());
                        return true;
                    }
                });

            }
        }.execute();

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
