package com.example.sandy.getbooks.Models;

public class Category extends java.util.HashMap<String,String> {

    final static String BASE_HOST_URL = "http://172.17.251.111/bookshop/WCFServices/Service.svc/";

    public Category(int CategoryID, String Name) {
        put("CategoryID", CategoryID);
        put("Name", Name);
    }

    public Category(){}

    public static List<String> listCategory() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(BASE_HOST_URL+"/Categories");
            for (int i=0; i<a.length(); i++) {
                String c = a.getString(i);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Category getCategory(String id) {
        Category b = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(BASE_HOST_URL+"/Category/"+id);
            b = new Category(c.getInt("CategoryID"),
                    c.getString("Name"));
        } catch (Exception e) {
        }
        return b;
    }

}