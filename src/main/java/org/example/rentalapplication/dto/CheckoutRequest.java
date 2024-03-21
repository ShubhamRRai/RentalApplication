package org.example.rentalapplication.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    private String toolCode;

    @Min(value = 1, message = "Rental day count must be 1 or greater")
    private long rentalDay;


    @Min(value = 0, message = "Discount percent must be at least 0")
    @Max(value = 100, message = "Discount percent must be at most 100")
    private double discountPercent;


    @Pattern(regexp = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/[0-9]{2}$", message = "Please enter a valid checkout date")
    private String checkoutDate;
}
