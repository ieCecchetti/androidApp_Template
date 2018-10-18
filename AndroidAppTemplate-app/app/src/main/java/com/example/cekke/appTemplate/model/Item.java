package com.example.cekke.appTemplate.model;

public class Item {
    private int id;
    private String name;
    private String value;
    private String UrlImage;

    public Item() {
    }

    public Item(int id, String name, String value, String urlImage) {
        this.id = id;
        this.name = name;
        this.value = value;
        UrlImage = urlImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrlImage() {
        return UrlImage;
    }

    public void setUrlImage(String urlImage) {
        UrlImage = urlImage;
    }
}
