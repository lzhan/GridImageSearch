package com.codepath.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.model.Filter;

public class FilterActivity extends ActionBarActivity {
    private Filter filter;
    private Spinner spinnerSize;
    private Spinner spinnerColor;
    private Spinner spinnerType;
    private EditText etSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filter = (Filter) getIntent().getSerializableExtra("filter");

        spinnerSize = (Spinner) findViewById(R.id.spinnerSize);
        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        etSite = (EditText) findViewById(R.id.etSite);

        setSpinnerToValue(spinnerSize, filter.getSize());
        setSpinnerToValue(spinnerColor, filter.getColor());
        setSpinnerToValue(spinnerType, filter.getType());
        etSite.setText(filter.getSite());

    }

    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }

    public void onSubmit(View v) {

        String size = spinnerSize.getSelectedItem().toString();
        String color = spinnerColor.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String site = etSite.getText().toString();
        System.out.println("*******before sending********"+size+color+type+site);

        filter.setSize(size);
        filter.setColor(color);
        filter.setType(type);
        filter.setSite(site);


        Intent filterData = new Intent();
        filterData.putExtra("filter", filter);
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
