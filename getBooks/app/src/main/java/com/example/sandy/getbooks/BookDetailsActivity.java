package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.sandy.getbooks.Models.Book;


public class BookDetailsActivity extends AppCompatActivity {

    final static int []view = {R.id.textViewTitle, R.id.textViewID, R.id.textViewAuthor, R.id.textViewCategory, R.id.textViewISBN, R.id.textViewPrice};
    final static String []key = {"Title", "BookID", "Author", "Category", "ISBN", "Price"};

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);

        String item = getIntent().getExtras().getString("BookID");
        new AsyncTask<String, Void, Book>() {
            @Override
            protected Book doInBackground(String... params) {
                return Book.getBook(String.valueOf(1));
            }

            @Override
            protected void onPostExecute(Book result) {
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
                    tv.setText(result.get(key[i]));
                }
            }
        }.execute("1");

       if (getIntent().hasExtra("com.example.sandy.getbooks")) {
            //TextView tv = (TextView) findViewById(R.id.textViewTitle);
            // String text = getIntent().getExtras().getString("com.example.sandy.getbooks");
            //tv.setText(text);

            setContentView(R.layout.activity_bookdetails);
        }
    }

}
