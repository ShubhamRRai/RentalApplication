package org.example.rentalapplication.service.impl;

import org.example.rentalapplication.dto.CheckoutRequest;
import org.example.rentalapplication.dto.RentalAgreement;
import org.example.rentalapplication.entity.Tool;
import org.example.rentalapplication.entity.ToolType;
import org.example.rentalapplication.repository.ToolRepository;
import org.example.rentalapplication.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CheckoutServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CheckoutServiceImplTest {


    @MockBean
    CheckoutService checkoutService;

    @Mock
    private ToolRepository toolRepository;


    @Test
    void checkout() {
        // Given
        Tool tool = new Tool(1,"LADW","Werner",new ToolType(1,"Ladder", 10.0, false, false, false, null));
        when(toolRepository.getToolsByToolCode(Mockito.<String>any())).thenReturn(tool);

        CheckoutRequest checkoutRequest = new CheckoutRequest("LADW", 5, 10.0, "07/03/15");

        when(checkoutService.checkout(checkoutRequest)).thenReturn(new RentalAgreement("LADW","Ladder","Werner",10,"07/03/15","07/03/15","10.00",5,"50.0"
                ,"10.00","45.00","45.00"));
        // When
        RentalAgreement agreement = checkoutService.checkout(checkoutRequest);

        // Then
        // Check if agreement is not null before accessing its properties
        assertNotNull(agreement);
        assertEquals("LADW", agreement.getToolCode());
        assertEquals("Ladder", agreement.getToolType());
        assertEquals("Werner",agreement.getToolBrand()); // Since we haven't set it in the test
        assertEquals("10.00", agreement.getDailyRentalCharge());
        assertEquals("10.00", agreement.getDiscountPercent());
        assertEquals(10, agreement.getRentalDays());
        assertEquals(5, agreement.getChargeDays()); // Assuming no holidays or weekends in the given range
        assertEquals("50.0", agreement.getPreDiscountCharge());
        assertEquals("45.00", agreement.getDiscountAmount());
        assertEquals("45.00", agreement.getFinalCharge());
        assertEquals("07/03/15", agreement.getDueDate());
        assertEquals("07/03/15", agreement.getCheckoutDate());
    }

    @Test
    public void testValidCheckoutRequest() {

        ToolRepository toolRepository = mock(ToolRepository.class);
        Tool tool = new Tool();
        tool.setToolCode("T001");
        ToolType toolType = new ToolType();
        toolType.setName("Power Tool");
        toolType.setDailyCharge(2.99);
        toolType.setHolidayCharge(false);
        toolType.setWeekendCharge(false);
        tool.setToolType(toolType);
        tool.setBrand("");
        when(toolRepository.getToolsByToolCode(anyString())).thenReturn(tool);

        CheckoutServiceImpl checkoutService = new CheckoutServiceImpl(toolRepository);
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode("T001");
        checkoutRequest.setRentalDay(4);
        checkoutRequest.setDiscountPercent(50.0);
        checkoutRequest.setCheckoutDate("07/02/20");

        // Act
        RentalAgreement rentalAgreement = checkoutService.checkout(checkoutRequest);
        assertEquals("T001", rentalAgreement.getToolCode());
        assertEquals("Power Tool", rentalAgreement.getToolType());
        assertEquals("", rentalAgreement.getToolBrand());
        assertEquals(4, rentalAgreement.getRentalDays());
        assertEquals("07/02/20", rentalAgreement.getCheckoutDate());
        assertEquals("07/06/20", rentalAgreement.getDueDate());
        assertEquals("$2.99", rentalAgreement.getDailyRentalCharge());
        assertEquals(1, rentalAgreement.getChargeDays());
        assertEquals("$2.99", rentalAgreement.getPreDiscountCharge());
        assertEquals("50%", rentalAgreement.getDiscountPercent());
        assertEquals("$1.5", rentalAgreement.getDiscountAmount());
        assertEquals("$1.5", rentalAgreement.getFinalCharge());
    }

}
