package com.codepath.gridimagesearch.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lzhan on 1/28/15.
 */
public class ImageResult implements Serializable {
    private static final long serialVersionUID = -5980245050741433310L;
    public String fullUrl;
    public String title;
    public String thumbUrl;

    //new ImageResult(..raw item JSON)
    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");

        }catch(JSONException e) {

        }

    }
    //ImageResult.from JSONArray([])
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for(int i = 0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
