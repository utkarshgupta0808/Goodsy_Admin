package com.goodsy.goodsyadmin.models;

public class DeliveryBoyModel {

    String delName, delCity, delNumber, delId, delEmail, delRating, profilePic, applicationStatus, docId;
    Boolean addDocumentStatus, deliveryStatus, reviewStatus;

    public DeliveryBoyModel(String delName, String delCity, String delNumber, String delId, String delEmail, String delRating, String profilePic, String applicationStatus, Boolean addDocumentStatus, Boolean deliveryStatus, Boolean reviewStatus, String docId) {
        this.delName = delName;
        this.delCity = delCity;
        this.delNumber = delNumber;
        this.delId = delId;
        this.delEmail = delEmail;
        this.delRating = delRating;
        this.profilePic = profilePic;
        this.applicationStatus = applicationStatus;
        this.addDocumentStatus = addDocumentStatus;
        this.deliveryStatus = deliveryStatus;
        this.reviewStatus = reviewStatus;
        this.docId=docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public DeliveryBoyModel() {
    }

    public String getDelName() {
        return delName;
    }

    public void setDelName(String delName) {
        this.delName = delName;
    }

    public String getDelCity() {
        return delCity;
    }

    public void setDelCity(String delCity) {
        this.delCity = delCity;
    }

    public String getDelNumber() {
        return delNumber;
    }

    public void setDelNumber(String delNumber) {
        this.delNumber = delNumber;
    }

    public String getDelId() {
        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }

    public String getDelEmail() {
        return delEmail;
    }

    public void setDelEmail(String delEmail) {
        this.delEmail = delEmail;
    }

    public String getDelRating() {
        return delRating;
    }

    public void setDelRating(String delRating) {
        this.delRating = delRating;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Boolean getAddDocumentStatus() {
        return addDocumentStatus;
    }

    public void setAddDocumentStatus(Boolean addDocumentStatus) {
        this.addDocumentStatus = addDocumentStatus;
    }

    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Boolean getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Boolean reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
