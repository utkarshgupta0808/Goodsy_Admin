package com.goodsy.goodsyadmin.models;

public class ItemModel {

    String itemImage, itemPrice, itemName, itemStatus, itemWeight, rejectReason, itemDescription;
    Boolean underReview;

    public ItemModel() {
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public String getItemWeight() {
        return itemWeight;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Boolean getUnderReview() {
        return underReview;
    }

    public ItemModel(String itemImage, String itemPrice, String itemName, String itemStatus, String itemWeight, String rejectReason, Boolean underReview, String itemDescription) {
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
        this.itemWeight = itemWeight;
        this.rejectReason = rejectReason;
        this.underReview = underReview;
        this.itemDescription=itemDescription;
    }
}
