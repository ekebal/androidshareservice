package ikbal_jimmy.shareservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jimmymunoz on 17/04/16.
 */
public class RestHelper {

    // Reads an InputStream and converts it to a String.
    public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
    {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    // REST - GET
    public static String executeGET(String urlString)
    {
        String result = "";
        InputStream is = null;
        int len = 500;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000); //60 secs
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            result = contentAsString;
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return result;
    }

    // REST - PUT
    public static String executePUT(String urlString)
    {
        String result = "";
        InputStream is = null;
        int len = 500;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000); //60 secs
            conn.setRequestMethod("PUT");
            //conn.setDoInput(true);
            conn.setDoOutput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            result = contentAsString;
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return result;
    }

    // REST - PUT
    public static String executeDELETE(String urlString)
    {
        String result = "";
        InputStream is = null;
        int len = 500;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000); //60 secs
            //conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty(
                    "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("DELETE");
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            result = contentAsString;
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return result;
    }




}
