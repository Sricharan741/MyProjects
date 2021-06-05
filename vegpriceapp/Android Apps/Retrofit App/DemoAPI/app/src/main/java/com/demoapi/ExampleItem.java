package com.demoapi;

public class ExampleItem {
    private int mImageResource;
    private String mTitle;
    private String mBody;

    public ExampleItem(int imageResource, String mtitle, String mbody) {
        mImageResource = imageResource;
        mTitle = mtitle;
        mBody = mbody;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }
}