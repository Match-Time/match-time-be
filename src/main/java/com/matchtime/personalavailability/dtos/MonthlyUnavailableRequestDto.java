package com.matchtime.personalavailability.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthlyUnavailableRequestDto {
    private List<String> dates;
}
