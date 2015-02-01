package com.codepath.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.model.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private String color = "";
    private String type = "";
    private String size = "";
    private String site = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        //creates the data source
        imageResults = new ArrayList<ImageResult>();
        //Attaches the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //link the adapter to the adapterview (gridview)
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResult);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //launch the image display activity
                //Creating on intent
                Intent i = new Intent(SearchActivity.this,  ImageDisplayActivity.class);
                //Get the image result to display
                ImageResult result = imageResults.get(position);
                //Pass image result into the intent
                i.putExtra("result", result);  //need to either be serializable or parcelable
                //launch the new activity
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    private String getFilter(){
        String filter = "";
        if(size != ""){
            filter = filter + "&imgsz="+size;
        }
        if(type != ""){
            filter = filter + "&imgtype="+type;
        }
        if(color != ""){
            filter = filter + "&imgcolor="+color;
        }
        if(site != ""){
            filter = filter + "&as_sitesearch="+site;
        }
        return filter;
    }
    //fire when button is pressed
    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();
        //Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + "" + "&rsz=8"
        String filter = getFilter();
        String searchUrl ="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
        if (filter == "") {
            searchUrl = searchUrl + query + "&rsz=8";
        } else {
            searchUrl = searchUrl + query + "&rsz=8" + filter;
        }
        System.out.println("*********filter**====== *****"+ filter);
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    //clear the existing images from the array in cases where it is a new search
                    imageResults.clear();
                    //when making change to adapter, it does modify the underline data
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("DEBUG", imageResults.toString());
            }

        });
    }
// width, height, tbUrl, title, url
    //responseData => results =>[x] => tbUrl
    //responseData => results =>[x] => title
    //responseData => results =>[x] => url
    //responseData => results =>[x] => height
    //responseData => results =>[x] => width

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.miFilter) {
            System.out.println("********go to filter*******");
            launchFilterView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchFilterView() {
        Intent i = new Intent(this, FilterActivity.class);
        i.putExtra("mycode", "20");
        startActivityForResult(i, 50);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 50) {
            size = data.getExtras().getString("size");
            color = data.getExtras().getString("color");
            type = data.getExtras().getString("type");
            site = data.getExtras().getString("site");
        }
    }
}
