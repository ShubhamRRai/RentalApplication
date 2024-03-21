package org.example.rentalapplication.controller;


import org.example.rentalapplication.dto.CheckoutRequest;
import org.example.rentalapplication.dto.ErrorMessage;
import org.example.rentalapplication.dto.RentalAgreement;
import org.example.rentalapplication.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     *
     * @param checkoutRequest
     * @param bindingResult
     * @return Rental Agreement if there is no errors in bindingResult else return error
     */
    @PostMapping
    public ResponseEntity<?> getRentalAgreement(@RequestBody @Validated CheckoutRequest checkoutRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorMessage(bindingResult.getAllErrors().get(0).getDefaultMessage()));
        }
        return ResponseEntity.ok().body(checkoutService.checkout(checkoutRequest));
    }

}
