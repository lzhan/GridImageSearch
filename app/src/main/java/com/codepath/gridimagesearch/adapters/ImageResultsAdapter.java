package com.codepath.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.model.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lzhan on 1/28/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> images){
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ImageResult imageInfo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        // Lookup view for data population
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        // Populate the data into the template view using the data object
        ivImage.setImageResource(0);
        tvTitle.setText(Html.fromHtml(imageInfo.title));
        //remotely download image
        Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
        // Return the completed view to render on screen
        return convertView;
    }
}
