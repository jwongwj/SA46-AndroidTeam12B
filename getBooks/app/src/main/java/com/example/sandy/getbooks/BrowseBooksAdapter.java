package com.example.sandy.getbooks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class BrowseBooksAdapter extends RecyclerView.Adapter<BrowseBooksAdapter.ViewHolder>{

    private Context context;
    private List<BooksModel> booksList;

    public BrowseBooksAdapter(Context context, List<BooksModel> booksModels){
        this.context = context;
        this.booksList = booksModels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView BookImage;
        public View view;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.title = (TextView) view.findViewById(R.id.textView);
            this.BookImage = (ImageView) view.findViewById(R.id.BookImage);
            this.cardView = (CardView) view.findViewById(R.id.CardView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cardlayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(booksList.get(position).getTitle());
        holder.BookImage.setImageResource(R.drawable.getbooks_logo);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), booksList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,BookDetailsActivity.class);
                intent.putExtra("bookId",  booksList.get(position).getBookID());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

//    private Context mContext;
//    private List<BooksModel> bookList;
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView title, count;
//        public ImageView thumbnail, overflow;
//
//        public ViewHolder(View view) {
//            super(view);
//            title = (TextView) view.findViewById(R.id.BookTitle);
//        }
//    }
//
//
//    public BrowseBooksAdapter(Context mContext, List<BooksModel> bookList) {
//        this.mContext = mContext;
//        this.bookList = bookList;
//    }
//
//    @Override
//    public BrowseBooksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.view_cardlayout, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        Album album = albumList.get(position);
//        holder.title.setText(album.getName());
//        holder.count.setText(album.getNumOfSongs() + " songs");
//
//        // loading album cover using Glide library
//        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);
//
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.overflow);
//            }
//        });
//    }
}
