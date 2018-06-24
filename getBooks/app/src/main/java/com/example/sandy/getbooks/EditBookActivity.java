package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.getbooks.Models.Book;

import org.w3c.dom.Text;

import java.util.List;

public class EditBookActivity extends AppCompatActivity {



    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbook);

        String BookID = getIntent().getExtras().getString("BookID");

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
            protected void onPostExecute(Book result)
            {
                TextView tvISBN = (TextView) findViewById(R.id.textViewISBN);
                tvISBN.setText(result.get("ISBN"));

                EditText etAuthor = (EditText) findViewById(R.id.editViewAuthor);
                etAuthor.setText(result.get("Author"));


                EditText etTitle = (EditText) findViewById(R.id.editViewTitle);
                etTitle.setText(result.get("Title"));


                EditText etPrice = (EditText) findViewById(R.id.editViewPrice);
                etPrice.setText(result.get("Price"));
            }
        }.execute(BookID);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_savebtn, menu);
        return true;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String BookID = getIntent().getExtras().getString("BookID");
        new AsyncTask<String, Void, Book>() {

            @Override
            protected Book doInBackground(String... params) {
                return Book.getBook(params[0]);
            }

            @Override
            protected void onPostExecute(Book result)
            {

                EditText etAuthor = (EditText) findViewById(R.id.editViewAuthor);
                EditText etTitle = (EditText) findViewById(R.id.editViewTitle);
                EditText etPrice = (EditText) findViewById(R.id.editViewPrice);

                result.remove("Author");
                result.put("Author", etAuthor.getText().toString());
                Book.updateBook(result);


            }
        }.execute(BookID);

        finish();
            return true;
    }
}
