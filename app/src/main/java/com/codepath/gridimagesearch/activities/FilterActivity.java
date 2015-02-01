package com.codepath.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;

public class FilterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void onSubmit(View v) {
        Spinner spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
        Spinner spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);
        EditText etSite = (EditText) findViewById(R.id.etSite);

        String size = spinnerSize.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String site = etSite.getText().toString();
        System.out.println("*******before sending********"+size+color+type+site);
        Intent filterData = new Intent();
        filterData.putExtra("size", size);
        filterData.putExtra("color", color);
        filterData.putExtra("type", type);
        filterData.putExtra("site", site);

        setResult(RESULT_OK, filterData);
        // closes the activity and returns to first screen
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
