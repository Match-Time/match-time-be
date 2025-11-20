package com.example.matchtime.dto;

public class RecommendedDateResponse {
    private String date;
    private int availableCount;

    public RecommendedDateResponse(String date, int availableCount) {
        this.date = date;
        this.availableCount = availableCount;
    }

    public String getDate() {
        return date;
    }

    public int getAvailableCount() {
        return availableCount;
    }
}
