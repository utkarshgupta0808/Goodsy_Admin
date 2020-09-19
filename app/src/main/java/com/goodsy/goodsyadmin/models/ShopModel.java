package com.goodsy.goodsyadmin.models;

public class ShopModel {

    Boolean reviewStatus, shopFeatured;
    String shopCategory;
    String shopAddress;
    String shopArrange;
    String shopKeeperId;
    String shopName;
    String shopStatusBackground;
    String shopType;
    String shopLongitude;
    String shopLatitude;
    String aadharFront;
    String aadharBack;
    String panCard;
    String gst;

    public String getApplicationStatus() {
        return applicationStatus;
    }

    String applicationStatus;

    public String getShopLongitude() {
        return shopLongitude;
    }

    public String getShopLatitude() {
        return shopLatitude;
    }

    public String getAadharFront() {
        return aadharFront;
    }

    public String getAadharBack() {
        return aadharBack;
    }

    public String getPanCard() {
        return panCard;
    }

    public String getGst() {
        return gst;
    }

    public String getShopImage() {
        return shopImage;
    }

    String shopImage;

    public ShopModel() {
    }

    public Boolean getReviewStatus() {
        return reviewStatus;
    }

    public Boolean getShopFeatured() {
        return shopFeatured;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopArrange() {
        return shopArrange;
    }

    public String getShopKeeperId() {
        return shopKeeperId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopStatusBackground() {
        return shopStatusBackground;
    }

    public String getShopType() {
        return shopType;
    }

    public ShopModel(Boolean reviewStatus, Boolean shopFeatured, String shopCategory, String shopAddress, String shopArrange, String shopKeeperId, String shopName, String shopStatusBackground, String shopType, String shopImage, String shopLongitude, String shopLatitude, String aadharFront, String aadharBack, String panCard, String gst, String applicationStatus) {
        this.reviewStatus = reviewStatus;
        this.shopFeatured = shopFeatured;
        this.shopCategory = shopCategory;
        this.shopAddress = shopAddress;
        this.shopArrange = shopArrange;
        this.shopKeeperId = shopKeeperId;
        this.shopName = shopName;
        this.shopStatusBackground = shopStatusBackground;
        this.shopType = shopType;
        this.shopImage=shopImage;
        this.gst=gst;
        this.aadharBack=aadharBack;
        this.aadharFront=aadharFront;
        this.panCard=panCard;
        this.shopLatitude=shopLatitude;
        this.shopLongitude=shopLongitude;
        this.applicationStatus=applicationStatus;

    }
}
