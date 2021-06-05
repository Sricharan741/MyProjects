package com.marketprices;

import com.squareup.moshi.Json;

public class VegetableItemPojo {

    @Json(name = "vegname")
    private String vegname;
    @Json(name = "market")
    private String market;
    @Json(name = "retail")
    private String retail;
    @Json(name = "shopping")
    private String shopping;
    public VegetableItemPojo(){}
    public String getVegname() {
        return vegname;
    }

    /*public void setVegname(String vegname) {
        this.vegname = vegname;
    }
     */

    public String getMarket() {
        return market;
    }
/*
    public void setMarket(String market) {
        this.market = market;
    }
 */

    public String getRetail() {
        return retail;
    }
/*
    public void setRetail(String retail) {
        this.retail = retail;
    }
 */

    public String getShopping() {
        return shopping;
    }
/*
    public void setShopping(String shopping) {
        this.shopping = shopping;
    }
*/
}