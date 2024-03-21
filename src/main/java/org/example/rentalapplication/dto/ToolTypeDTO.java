package org.example.rentalapplication.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ToolTypeDTO {
    private long id;
    private String name;
    private double dailyCharge;
    private boolean weekDayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;
}
