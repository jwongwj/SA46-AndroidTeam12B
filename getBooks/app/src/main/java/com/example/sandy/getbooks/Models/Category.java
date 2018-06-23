package com.example.sandy.getbooks.Models;

import com.example.sandy.getbooks.Utils.Configs;
import com.example.sandy.getbooks.Utils.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category extends java.util.HashMap<String,String> {


    public Category(String CategoryID, String Name) {
        put("CategoryID", CategoryID);
        put("Name", Name);
    }

    public Category(){}

    public static List<String> listCategory() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(Configs.BASE_HOST_URL+"Categories");
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
            JSONObject c = JSONParser.getJSONFromUrl(Configs.BASE_HOST_URL+"Category/"+id);
            b = new Category(c.getString("CategoryID"),
                    c.getString("Name"));
        } catch (Exception e) {
        }
        return b;
    }

}