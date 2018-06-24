package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.getbooks.Models.Book;
import com.example.sandy.getbooks.Models.Category;

import java.util.List;

public class EditBookActivity extends AppCompatActivity {

    BrowseBooksAdapter adapter;
    private String BookID;
    private Book book;
    private ProgressBar progressBar;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbook);
        adapter = new BrowseBooksAdapter();
        BookID = getIntent().getExtras().getString("BookID");
        progressBar=(ProgressBar) findViewById(R.id.progressBar_edit);
        progressBar.setVisibility(View.GONE);

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Book b = Book.getBook(params[0]);
                return Book.getPhoto(b.get("ISBN"));
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                iv.setImageBitmap(result);
            }
        }.execute(BookID);

        new AsyncTask<String, Void, Book>() {

            @Override
            protected Book doInBackground(String... params) {
                return Book.getBook(params[0]);
            }

            @Override
            protected void onPostExecute(Book result) {
                TextView tvISBN = (TextView) findViewById(R.id.textViewISBN);
                tvISBN.setText(result.get("ISBN"));

                EditText etAuthor = (EditText) findViewById(R.id.editViewAuthor);
                etAuthor.setText(result.get("Author"));


                EditText etTitle = (EditText) findViewById(R.id.evTitle);
                etTitle.setText(result.get("Title"));


                EditText etPrice = (EditText) findViewById(R.id.editViewPrice);
                etPrice.setText(result.get("Price"));

                EditText etQty = (EditText) findViewById(R.id.editViewQty);
                etQty.setText(result.get("Stock"));
            }
        }.execute(BookID);



        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                Book b = Book.getBook(params[0]);
                Category c = Category.getCategory(b.get("CategoryID"));
                return c.get("Name");
            }

            @Override
            protected void onPostExecute(String result) {
                TextView iv = (TextView) findViewById(R.id.textViewCategory);
                iv.setText(result);
            }
        }.execute(BookID);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_savecancel, menu);
        return true;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.btnSave) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String BookID = getIntent().getExtras().getString("BookID");
            new AsyncTask<String, Void, Book>() {

                @Override
                protected Book doInBackground(String... params) {
                    return Book.getBook(params[0]);
                }

                @Override
                protected void onPostExecute(Book result) {

                    EditText etAuthor = (EditText) findViewById(R.id.editViewAuthor);
                    EditText etTitle = (EditText) findViewById(R.id.evTitle);
                    EditText etPrice = (EditText) findViewById(R.id.editViewPrice);
                    EditText etQty = (EditText) findViewById(R.id.editViewQty);

                    result.remove("Author");
                    result.put("Author", etAuthor.getText().toString());

                    result.remove("Title");
                    result.put("Title", etTitle.getText().toString());

                    result.remove("Price");
                    result.put("Price", etPrice.getText().toString());

                    result.remove("Stock");
                    result.put("Stock", etQty.getText().toString());
                    Book.updateBook(result);
                }
            }.execute(BookID);

            Toast.makeText(this, "Update Success", Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
            finish();
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.btnCancel) {
            finish();
            startActivity(new Intent(this, BrowseActivity.class));

            return true;
        }
            return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBackPressed() {
        final Intent intent=new Intent(this, BookDetailsActivity.class);
//        intent.putExtra("BookID", BookID);

        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                book=Book.getBook(BookID);
                return Book.getPhoto(book.get("ISBN"));
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                intent.putExtra("BookID",  book.get("BookID"));
                intent.putExtra("bitmap", result);

                if(progressBar.isShown()){
                    progressBar.setVisibility(View.GONE);}
                finish();
            }
        }.execute();

        new AsyncTask<Void, Void, Category>() {
            @Override
            protected Category doInBackground(Void... params) {
                return Category.getCategory(book.get("CategoryID"));
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Category result) {
                intent.putExtra("category", result.get("Name"));
                startActivity(intent);

                if(progressBar.isShown()){
                    progressBar.setVisibility(View.GONE);}
                finish();
            }
        }.execute();

    }

}
