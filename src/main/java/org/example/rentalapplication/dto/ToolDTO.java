package org.example.rentalapplication.dto;

import lombok.Data;

@Data
public class ToolDTO {

    private long id;
    private String toolCode;
    private String brand;
    private ToolTypeDTO toolType;
}
