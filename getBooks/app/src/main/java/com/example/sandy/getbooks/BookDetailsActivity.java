package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.getbooks.Models.Book;


public class BookDetailsActivity extends AppCompatActivity {

    final static int[] view = {R.id.textViewTitle, R.id.textViewID, R.id.textViewAuthor, R.id.textViewISBN, R.id.textViewPrice, R.id.textViewQty};
    final static String[] key = {"Title", "BookID", "Author", "ISBN", "Price", "Stock"};
    private String bookId;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);


        final String item = getIntent().getExtras().getString("BookID");

        bookId = item;

        new AsyncTask<String, Void, Book>() {

            @Override
            protected Book doInBackground(String... params) {
                return Book.getBook(item);
            }

            @Override
            protected void onPostExecute(Book result) {
                for (int i = 0; i < view.length; i++) {
                    TextView tvTitle = findViewById(view[i]);
                    if (i == view.length - 2) {
                        double x = Double.parseDouble(result.get(key[i]));
                        tvTitle.setText("$" + String.format("%.2f", x));
                    } else if (i == view.length - 1) {
                        tvTitle.setText("Qty: " + result.get(key[i]));
                    } else
                        tvTitle.setText(result.get(key[i]));
                }
            }
        }.execute(item);

        ImageView iv = findViewById(R.id.indvBookImage);
        Bitmap bt = getIntent().getParcelableExtra("bitmap");
        iv.setImageBitmap(bt);

        TextView cat = findViewById(R.id.textViewCategory);
        cat.setText(getIntent().getExtras().getString("category"));

        if (getIntent().hasExtra("com.example.sandy.getbooks")) {
            setContentView(R.layout.activity_bookdetails);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editbtn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnEdit) {
            Intent intent = new Intent(BookDetailsActivity.this, EditBookActivity.class);
            intent.putExtra("BookID", bookId);
            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        finish();
//    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, BrowseActivity.class));
        finish();
    }

}
