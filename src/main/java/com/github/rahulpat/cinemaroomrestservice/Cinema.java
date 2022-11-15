package com.github.rahulpat.cinemaroomrestservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cinema {

    private int totalRows;
    private int totalColumns;

    private List<Seat> availableSeats;
    @JsonIgnore
    private List<Seat> totalSeats;
    @JsonIgnore
    private List<Purchases> purchases;
    @JsonIgnore
    private int currentIncome;


    public Cinema() {
        this.totalColumns = 9;
        this.totalRows = 9;
        this.currentIncome = 0;
        this.availableSeats = new ArrayList<>();

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                this.availableSeats.add(new Seat(i,j));
            }
        }

        this.totalSeats = new ArrayList<>();

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                this.totalSeats.add(new Seat(i,j));
            }
        }

        this.purchases = new ArrayList<>();

    }
    @JsonIgnore
    public int getCurrentIncome() {
        return currentIncome;
    }

    public void incrementCurrentIncome(int income) {
        this.currentIncome += income;
    }

    public void decrementCurrentIncome(int income) {
        this.currentIncome -= income;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }
    @JsonIgnore
    public List<Seat> getTotalSeats() {
        return totalSeats;
    }
    // @JsonProperty
    public void setTotalSeats(List<Seat> totalSeats) {
        this.totalSeats = totalSeats;
    }
    @JsonIgnore
    public List<Purchases> getPurchases() {
        return purchases;
    }
    // @JsonProperty
    public void setPurchases(List<Purchases> purchases) {
        this.purchases = purchases;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "totalRows=" + totalRows +
                ", totalColumns=" + totalColumns +
                ", availableSeats=" + availableSeats +
                '}';
    }
}