package com.newproject.snapandsell.Model;

/**
 * Created by Korisnik on 04.05.2018.
 */

public class Product {
    private String mImageUrl;
    private String mPrice;
    private String mDiscription;

    public Product(){

    }

    public Product(String mImageUrl, String mDiscription,String mPrice) {
        this.mImageUrl = mImageUrl;
        this.mDiscription=mDiscription;
        this.mPrice = mPrice;
    }

    public String getmDiscription() {
        return mDiscription;
    }

    public void setmDiscription(String mDiscription) {
        this.mDiscription = mDiscription;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}
