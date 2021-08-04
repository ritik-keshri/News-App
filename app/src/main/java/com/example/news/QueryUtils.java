package com.example.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String LOG_TAG = MainActivity.class.getName();

    public static List<News> fetchNewsData(String requestUrl) {
        if (requestUrl.isEmpty())
            return null;
        URL url = createUrl(requestUrl);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractFeaturesFromJson(jsonResponse);
    }

    //Returns new URL object from the given string URL.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(4000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                // Closing the input stream could throw an IOException, which is why the makeHttpRequest(URL url) method signature specifies than an IOException could be thrown.
                inputStream.close();
        }
        return jsonResponse;
    }

    //Convert the InputStream into a String which contains the whole JSON response from the server.
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<News> extractFeaturesFromJson(String JsonResponse) {
        List<News> news = new ArrayList<>();
        try {
            //For accessing the json object
            JSONObject root = new JSONObject(JsonResponse);
            JSONObject baseJsonResponse = root.getJSONObject("response");

            //For accessing the json array that is present in JSON object
            JSONArray earthquakeArray = baseJsonResponse.optJSONArray("results");

            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentNews = earthquakeArray.getJSONObject(i);

                String sectionName = currentNews.optString("sectionName");
                String title = currentNews.optString("webTitle");
                String date = currentNews.optString("webPublicationDate");
                String url = currentNews.getString("webUrl");

                //Adding the value in ArrayList
                news.add(new News(sectionName, title, date, url));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return news;
    }
}