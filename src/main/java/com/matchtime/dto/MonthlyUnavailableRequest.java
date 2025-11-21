package com.matchtime.dto;

import java.util.List;

public class MonthlyUnavailableRequest {

    private List<String> dates;

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
