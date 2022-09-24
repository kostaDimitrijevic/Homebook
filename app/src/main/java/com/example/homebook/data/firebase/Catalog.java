package com.example.homebook.data.firebase;

import com.example.homebook.data.JoinItemsCatalog;

import java.util.List;

public class Catalog {
    private String userEmail;
    private String toUserEmail;
    private String catalogName;
    private String date;
    private int status;
    private List<JoinItemsCatalog> catalogItems;

    public Catalog() {
    }

    public Catalog(String userEmail, String toUserEmail, String catalogName, String date, int status, List<JoinItemsCatalog> catalogItems) {
        this.userEmail = userEmail;
        this.toUserEmail = toUserEmail;
        this.catalogName = catalogName;
        this.date = date;
        this.status = status;
        this.catalogItems = catalogItems;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public List<JoinItemsCatalog> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(List<JoinItemsCatalog> catalogItems) {
        this.catalogItems = catalogItems;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getToUserEmail() {
        return toUserEmail;
    }

    public void setToUserEmail(String toUserEmail) {
        this.toUserEmail = toUserEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
