package com.example.sandy.getbooks.Models;

public class Book extends java.util.HashMap<String,String> {

    final static String BASE_HOST_URL = "http://172.17.251.111/bookshop/WCFServices/Service.svc/";

    public Book(int BookID, int CategoryID, String ISBN, String Author, int Price, int Stock, String Title) {
        put("BookID", BookID);
        put("CategoryID", CategoryID);
        put("ISBN", ISBN);
        put("Author", Author);
        put("Price", Price);
        put("Stock", Stock);
        put("Title", Title);
    }

    public Book(){}

    public static List<String> listBook() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(BASE_HOST_URL+"/Books");
            for (int i=0; i<a.length(); i++) {
                String c = a.getString(i);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Book getBook(String id) {
        Book b = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(BASE_HOST_URL+"/Book/"+id);
            b = new Book(c.getInt("BookID"),
                    c.getInt("CategoryID"),
                    c.getString("ISBN"), c.getString("Author"), c.getInt("Price"), c.getInt("Stock"), c.getString("Title")
            );
        } catch (Exception e) {
        }
        return b;
    }

    final static String imageURL = "http://172.17.251.111/bookshop/images";
    public static Bitmap getPhoto(boolean thumbnail, String isbn) {
        try {
            URL url = (thumbnail ? new URL(String.format("%s/%s-s.jpg",imageURL, isbn)) :
                    new URL(String.format("%s/%s.jpg",imageURL, isbn)));
            URLConnection conn = url.openConnection();
            InputStream ins = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(ins);
            ins.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Book.getPhoto()", "Bitmap error");
        }
        return(null);
    }
}