package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.getbooks.Models.Book;
import com.example.sandy.getbooks.Models.Category;

import org.w3c.dom.Text;

import java.util.List;

public class BrowseBooksAdapter extends RecyclerView.Adapter<BrowseBooksAdapter.ViewHolder>{

    private Context context;
    private List<Book> booksList;
    private ProgressBar progressBar;
    private boolean imgLoadFlag =false;
    private boolean categoryLoadFlag =false;
    private boolean intentStarted =false;
//    private android.support.v7.widget.SearchView searchView;
    private Activity activity;

    public BrowseBooksAdapter(){

    }

    public BrowseBooksAdapter(Activity activity, Context context, List<Book> booksModels, ProgressBar progressBar){
        this.context = context;
        this.booksList = booksModels;
        this.progressBar = progressBar;
//        this.searchView=searchView;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView BookImage;
        public View view;
        public TextView category;
        public TextView author;
        public TextView price;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.title = (TextView) view.findViewById(R.id.bookTitle);
            this.BookImage = (ImageView) view.findViewById(R.id.bookImage);
            this.category = (TextView) view.findViewById(R.id.bookCategory);
            this.author = (TextView) view.findViewById(R.id.bookAuthor);
            this.price = (TextView) view.findViewById(R.id.bookPrice);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cardlayout, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(booksList.get(position).get("Title"));
        double x = Double.valueOf(booksList.get(position).get("Price"));
        holder.price.setText("$" + String.format("%.2f", x));
        holder.author.setText(booksList.get(position).get("Author"));
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return Book.getPhoto(booksList.get(position).get("ISBN"));
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                holder.BookImage.setImageBitmap(result);

                if(intentStarted==false && progressBar.isShown()){
                    progressBar.setVisibility(View.GONE);}
            }
        }.execute();

        new AsyncTask<Void, Void, Category>() {
            @Override
            protected Category doInBackground(Void... params) {
                return Category.getCategory(booksList.get(position).get("CategoryID"));
            }

            @Override
            protected void onPostExecute(Category result) {
                holder.category.setText(result.get("Name"));
            }
        }.execute();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(context,BookDetailsActivity.class);
                intentStarted=true;
                //remove focus on searchview
//                searchView.clearFocus();
//                searchView.onActionViewCollapsed();

                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        return Book.getPhoto(booksList.get(position).get("ISBN"));
                    }

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {
                        intent.putExtra("BookID",  booksList.get(position).get("BookID"));
                        intent.putExtra("bitmap", result);

                        imgLoadFlag=true;

                        if(categoryLoadFlag=true && progressBar.isShown()){
                            progressBar.setVisibility(View.GONE);}

                    }
                }.execute();

                new AsyncTask<Void, Void, Category>() {
                    @Override
                    protected Category doInBackground(Void... params) {
                        return Category.getCategory(booksList.get(position).get("CategoryID"));
                    }

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(Category result) {
                        activity.finish();
                        intent.putExtra("category", result.get("Name"));
                        v.getContext().startActivity(intent);

                        categoryLoadFlag=true;

                        if(imgLoadFlag=true && progressBar.isShown()){
                            progressBar.setVisibility(View.GONE);}

                    }
                }.execute();

            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}
