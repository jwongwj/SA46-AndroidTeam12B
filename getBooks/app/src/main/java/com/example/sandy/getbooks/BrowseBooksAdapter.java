package com.example.sandy.getbooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BrowseBooksAdapter extends RecyclerView.Adapter<BrowseBooksAdapter.ViewHolder>{

    private Context context;
    private List<BooksModel> booksList;

    public BrowseBooksAdapter(Context context, List<BooksModel> booksModels){
        this.context = context;
        this.booksList = booksModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView BookImage;
        public CardView cardView;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.title = (TextView) view.findViewById(R.id.BookTitle);
            this.BookImage = (ImageView) view.findViewById(R.id.BookImage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cardlayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

}
