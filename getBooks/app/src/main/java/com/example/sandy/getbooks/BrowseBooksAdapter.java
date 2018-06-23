package com.example.sandy.getbooks;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.getbooks.Models.Book;
import com.example.sandy.getbooks.Models.Category;

import org.w3c.dom.Text;

import java.util.List;

public class BrowseBooksAdapter extends RecyclerView.Adapter<BrowseBooksAdapter.ViewHolder>{

    private Context context;
    private List<Book> booksList;

    public BrowseBooksAdapter(Context context, List<Book> booksModels){
        this.context = context;
        this.booksList = booksModels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView BookImage;
        public View view;
        public CardView cardView;
        public TextView category;
        public TextView author;
        public TextView price;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.title = (TextView) view.findViewById(R.id.bookTitle);
            this.BookImage = (ImageView) view.findViewById(R.id.bookImage);
            this.cardView = (CardView) view.findViewById(R.id.CardView);
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
//        holder.title.setText(booksList.get(position).getTitle());
//        holder.title.setText(Book.getBook(booksList.get(position)).get("Title"));
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
            protected void onPostExecute(Bitmap result) {
                holder.BookImage.setImageBitmap(result);
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
                new AsyncTask<Void, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        return Book.getPhoto(booksList.get(position).get("ISBN"));
                    }
                    @Override
                    protected void onPostExecute(Bitmap result) {

                        intent.putExtra("BookID",  booksList.get(position).get("BookID"));
                        intent.putExtra("bitmap", result);

                    }
                }.execute();

                new AsyncTask<Void, Void, Category>() {
                    @Override
                    protected Category doInBackground(Void... params) {
                        return Category.getCategory(booksList.get(position).get("CategoryID"));
                    }
                    @Override
                    protected void onPostExecute(Category result) {
                        intent.putExtra("category", result.get("Name"));
                        v.getContext().startActivity(intent);
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
