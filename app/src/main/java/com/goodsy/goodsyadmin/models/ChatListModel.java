package com.goodsy.goodsyadmin.models;

public class ChatListModel {
    private String name, message, image, docId;

    public ChatListModel() {
    }

    public ChatListModel(String name, String message, String image, String docId) {
        this.name = name;
        this.message = message;
        this.image = image;
        this.docId = docId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
