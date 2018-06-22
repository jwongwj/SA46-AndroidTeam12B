package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class BookDetailsActivity extends AppCompatActivity {

    final static int []view = {R.id.textViewTitle, R.id.textViewID, R.id.textViewAuthor, R.id.textViewCategory, R.id.textViewISBN, R.id.textViewPrice};
    final static String []key = {"Title", "BookID", "Author", "Category", "ISBN", "Price"};
    private String bookId;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        bookId = getIntent().getExtras().getString("bookId");

        new AsyncTask<String, Void, BooksModel>() {
            @Override
            protected BooksModel doInBackground(String... params) {
                return BooksModel.getBooks(1);
            }

            @Override
            protected void onPostExecute(BooksModel result) {
//                TextView tvBID = findViewById(R.id.textViewID);
//                tvBID.setText(result.getBookID());
//
//                TextView tvTitle = findViewById(R.id.textViewTitle);
//                tvTitle.setText(result.getTitle());
//
//                TextView tvAuthor = findViewById(R.id.textViewAuthor);
//                tvAuthor.setText(result.getAuthor());
//
//                TextView tvCategory = findViewById(R.id.textViewCategory);
//                tvCategory.setText(result.getCategoryID());
//
//                TextView tvISBN = findViewById(R.id.textViewISBN);
//                tvISBN.setText(result.getISBN());
//
//                TextView tvPrice = findViewById(R.id.textViewPrice);
//                tvPrice.setText(String.valueOf(result.getPrice()));


                for (int i = 0; i < view.length; i++) {
                    TextView tv = (TextView) findViewById(view[i]);
                    tv.setText(result.getAuthor());
                }
            }
        }.execute(bookId);

       if (getIntent().hasExtra("com.example.sandy.getbooks")) {
            //TextView tv = (TextView) findViewById(R.id.textViewTitle);
            // String text = getIntent().getExtras().getString("com.example.sandy.getbooks");
            //tv.setText(text);

            setContentView(R.layout.activity_bookdetails);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editbtn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnEdit) {
            Intent intent = new Intent(BookDetailsActivity.this, EditBookActivity.class);
            intent.putExtra("bookId", bookId);
            startActivity(intent);
//            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
