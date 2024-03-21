package org.example.rentalapplication.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalAgreement {

    private String toolCode;

    private  String toolType;

    private String toolBrand;

    private long rentalDays;

    private String checkoutDate;

    private String dueDate;

    private String dailyRentalCharge;

    private long chargeDays;

    private String preDiscountCharge;

    private String discountPercent;

    private String discountAmount;

    private String finalCharge;


}
