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

import com.codepath.gridimagesearch.EndlessScrollListener;
import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.adapters.ImageResultsAdapter;
import com.codepath.gridimagesearch.model.Filter;
import com.codepath.gridimagesearch.model.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    private final int REQUEST_CODE = 50;
    EditText etQuery;
    GridView gvResults;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultsAdapter aImageResults;
    Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        //Attaches the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //link the adapter to the adapterview (gridview)
        gvResults.setAdapter(aImageResults);

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                imageQuery(totalItemsCount);
            }
        });

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

    //fire when button is pressed
    public void onImageSearch(View v) {
        imageResults.clear();
        aImageResults.clear();
        imageQuery(0);
    }

    public void imageQuery(final int startIndex) {
        String query = etQuery.getText().toString();
        String searchUrl ="https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

        if (filter == null) {
            searchUrl = searchUrl + query + "&rsz=8&start=" + startIndex;
        } else {
            String allFilter = "&imgsz=" + filter.getSize() + "&imgcolor=" + filter.getColor() +
                    "&imgtype=" + filter.getType() + "&as_sitesearch=" + filter.getSite();
            searchUrl = searchUrl + query + "&rsz=8&start=" + startIndex + allFilter;
        }
        //System.out.println("*********filter**====== *****" + searchUrl);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    //when making change to adapter, it does modify the underline data
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("DEBUG", imageResults.toString());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.miFilter) {
            launchFilterView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchFilterView() {
        Intent i = new Intent(this, FilterActivity.class);
        if (filter == null)
            filter = new Filter("medium", "blue", "car", null);
        i.putExtra("filter", filter);
        startActivityForResult(i, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            filter = (Filter) data.getSerializableExtra("filter");
            imageResults.clear();
            aImageResults.clear();
            imageQuery(0);
        }
    }
}
