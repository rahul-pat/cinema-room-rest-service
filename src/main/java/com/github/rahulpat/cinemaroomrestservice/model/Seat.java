package com.github.rahulpat.cinemaroomrestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {

    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean isPurchased;

    public Seat() {}

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        if (row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
        this.isPurchased = false;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @JsonIgnore
    public boolean isPurchased() {
        return isPurchased;
    }
    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
