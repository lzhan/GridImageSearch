package com.codepath.gridimagesearch.model;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by lzhan on 2/1/15.
 */
public class Filter implements Serializable {
    private static final long serialVersionUID = -5569508836553519094L;
    public String size;
    public String color;
    public String type;
    public String site;

    public Filter(String size, String color, String type, String site) {
        this.setSize(size);
        this.setColor(color);
        this.setType(type);
        this.setSite(site);
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }
}
