package com.example.proiect;

public class Item {
    String name;
    int marketValue;
    int image;

    public void setMarketValue(int marketValue) {
        this.marketValue = marketValue;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMarketValue() {
        return marketValue;
    }

    public int getImage() {
        return image;
    }

    public Item(String name, int marketValue, int image) {
        this.name = name;
        this.marketValue = marketValue;
        this.image = image;
    }
}
