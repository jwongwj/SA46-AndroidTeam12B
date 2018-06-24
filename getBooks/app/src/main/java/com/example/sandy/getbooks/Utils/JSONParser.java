package com.example.sandy.getbooks.Utils;

import android.util.Log;

import com.example.sandy.getbooks.StackTrace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JSONParser {
    public static String getStream(String url) {
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.i("getstring", "getStream:" + e.getMessage());

        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return (sb.toString());
    }

    public static JSONObject getJSONFromUrl(String url) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public static JSONArray getJSONArrayFromUrl(String url) {
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing array " + e.toString());
        }
        return jArray;
    }


    public static String postStream(String url, String data) {
        InputStream is = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setFixedLengthStreamingMode(data.getBytes().length);
            conn.connect();
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(data.getBytes());
            os.flush();
            is = conn.getInputStream();
        } catch (UnsupportedEncodingException e) {
            Log.e("postStream Exception", StackTrace.trace(e));
        } catch (Exception e) {
            Log.e("postStream Exception", StackTrace.trace(e));
        }
        return readStream(is);
    }


    static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            Log.e("readStream Exception", StackTrace.trace(e));
        }
        return(sb.toString());
    }

//    public static void updateJSONtoUrl(String url, String json) {
//        try {
//            URL u = new URL(url); //Enter URL here
//            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.connect();
//            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//            wr.writeBytes(json);
//            wr.flush();
//            wr.close();
//
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }catch (Exception e) {
//            Log.i("Exception", "updateJSONtoUrl: " + e.getMessage());
//        }
//    }
}