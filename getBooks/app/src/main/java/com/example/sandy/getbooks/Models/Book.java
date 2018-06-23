package com.example.sandy.getbooks.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.sandy.getbooks.Utils.Configs;
import com.example.sandy.getbooks.Utils.JSONParser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Book extends java.util.HashMap<String, String> {

    public Book(String BookID, String CategoryID, String ISBN, String Author, String Price, String Stock, String Title) {
        put("BookID", BookID);
        put("CategoryID", CategoryID);
        put("ISBN", ISBN);
        put("Author", Author);
        put("Price", Price);
        put("Stock", Stock);
        put("Title", Title);
    }

    public Book() {
    }

    public static List<Book> listBook() {
        List<Book> list = new ArrayList<Book>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(Configs.BASE_HOST_URL + "Books");
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                Gson gson= new Gson();
                Book book = gson.fromJson(b.toString(),Book.class);
                list.add(book);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static List<String> listBookID(){
        List<String> listBookID = new ArrayList<String>();
        try{
            List<Book> listBook = listBook();
            for (Book b :listBook) {
                listBookID.add(b.get("BookID"));
            }
            return listBookID;
        }
        catch (Exception e){

        }
        return listBookID;
    }

    public static Book getBook(String id) {
        Book b = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(Configs.BASE_HOST_URL + "Book/" + id);
            b = new Book(
                    c.getString("BookID"),
                    c.getString("CategoryID"),
                    c.getString("ISBN"),
                    c.getString("Author"),
                    c.getString("Price"),
                    c.getString("Stock"),
                    c.getString("Title")
            );
        } catch (Exception e) {
        }
        return b;
    }


    public static Bitmap getPhoto(String isbn) {
        try {
            URL url = new URL(String.format("%s/%s.jpg", Configs.IMAGE_URL, isbn));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Book.getPhoto()", "Bitmap error");
        }
        return (null);
    }
}