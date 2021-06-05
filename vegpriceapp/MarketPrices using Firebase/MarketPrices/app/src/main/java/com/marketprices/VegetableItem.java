package com.marketprices;

public class VegetableItem {
    private String vegname;
    private String market;
    private String retail;
    private String shopping;
    public VegetableItem(){}
    public VegetableItem(String market,String retail,String shopping,String vegname){
        this.market=market;
        this.retail=retail;
        this.shopping=shopping;
        this.vegname=vegname;
    }
    public String getVegname() {
        return vegname;
    }

    public String getMarket() {
        return market;
    }
    public String getRetail() {
        return retail;
    }
    public String getShopping() {
        return shopping;
    }
}
