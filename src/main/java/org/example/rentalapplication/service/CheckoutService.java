package org.example.rentalapplication.service;

import org.example.rentalapplication.dto.CheckoutRequest;
import org.example.rentalapplication.dto.RentalAgreement;

public interface CheckoutService {

    RentalAgreement checkout(CheckoutRequest checkoutRequest);
}
