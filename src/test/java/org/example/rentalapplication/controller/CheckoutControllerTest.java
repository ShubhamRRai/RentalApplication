package org.example.rentalapplication.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rentalapplication.dto.CheckoutRequest;
import org.example.rentalapplication.dto.RentalAgreement;
import org.example.rentalapplication.entity.Tool;
import org.example.rentalapplication.entity.ToolType;
import org.example.rentalapplication.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {CheckoutController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CheckoutControllerTest {
    @Autowired
    private CheckoutController checkoutController;

    @MockBean
    private CheckoutService checkoutService;
    @Test
    void testGetRentalAgreement() throws Exception {

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setCheckoutDate("2020-03-01");
        checkoutRequest.setDiscountPercent(10.0d);
        checkoutRequest.setRentalDay(1L);
        checkoutRequest.setToolCode("Tool Code");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Please enter a valid checkout date\"}"));
    }

    /**
     * Method under test:
     * {@link CheckoutController#getRentalAgreement(CheckoutRequest, BindingResult)}
     */
    @Test
    void testGetRentalAgreement2() throws Exception {
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setCheckoutDate("2020-03-01");
        checkoutRequest.setDiscountPercent(10.0d);
        checkoutRequest.setRentalDay(1L);
        checkoutRequest.setToolCode("Tool Code");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout", "Uri Variables")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder);

        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"error\":\"Please enter a valid checkout date\"}"));
    }


    /**
     * Method under test:
     * {@link CheckoutController#getRentalAgreement(CheckoutRequest, BindingResult)}
     */
    @Test
    void testGetRentalAgreement3() throws Exception {
        when(checkoutService.checkout(Mockito.<CheckoutRequest>any()))
                .thenReturn(new RentalAgreement("Tool Code", "Tool Type", "Tool Brand", 1L, "2020-03-01", "2020-03-01",
                        "Daily Rental Charge", 1L, "3", "3", "10", "Final Charge"));

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setCheckoutDate("09/09/99");
        checkoutRequest.setDiscountPercent(10.0d);
        checkoutRequest.setRentalDay(1L);
        checkoutRequest.setToolCode("Tool Code");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"toolCode\":\"Tool Code\",\"toolType\":\"Tool Type\",\"toolBrand\":\"Tool Brand\",\"rentalDays\":1,\"checkoutDate"
                                        + "\":\"2020-03-01\",\"dueDate\":\"2020-03-01\",\"dailyRentalCharge\":\"Daily Rental Charge\",\"chargeDays\":1,"
                                        + "\"preDiscountCharge\":\"3\",\"discountPercent\":\"3\",\"discountAmount\":\"10\",\"finalCharge\":\"Final Charge\"}"));
    }

    @Test
    void testGetRentalAgreementDiscountPercentExceeds100() throws Exception {

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode("JAKR");
        checkoutRequest.setRentalDay(5L);
        checkoutRequest.setDiscountPercent(101.0d);
        checkoutRequest.setCheckoutDate("09/03/15");

        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Discount percent must be at most 100"));
    }

    @Test
    void testGetRentalAgreementValidInputLADW() throws Exception {

        when(checkoutService.checkout(Mockito.<CheckoutRequest>any()))
                .thenReturn(new RentalAgreement("LADW", "Ladder", "Werner", 3L, "07/02/20", "07/05/20",
                        "$1.99", 2L, "$3.98", "10%", "$0.4", "$3.58"));

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode("LADW");
        checkoutRequest.setRentalDay(3L);
        checkoutRequest.setDiscountPercent(10.0d);
        checkoutRequest.setCheckoutDate("07/02/20");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"toolCode\":\"LADW\",\"toolType\":\"Ladder\",\"toolBrand\":\"Werner\",\"rentalDays\":3,\"checkoutDate\":\"07/02/20\","
                                + "\"dueDate\":\"07/05/20\",\"dailyRentalCharge\":\"$1.99\",\"chargeDays\":2,\"preDiscountCharge\":\"$3.98\","
                                + "\"discountPercent\":\"10%\",\"discountAmount\":\"$0.4\",\"finalCharge\":\"$3.58\"}"));
    }
    @Test
    void testGetRentalAgreementValidInputCHNS() throws Exception {

        when(checkoutService.checkout(Mockito.<CheckoutRequest>any()))
                .thenReturn(new RentalAgreement("CHNS", "Chainsaw", "Stihl", 5L, "07/02/15", "07/07/15",
                        "$1.49", 3L, "$4.47", "25%", "$1.12", "$3.35"));

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode("CHNS");
        checkoutRequest.setRentalDay(5L);
        checkoutRequest.setDiscountPercent(25.0d);
        checkoutRequest.setCheckoutDate("07/02/15");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"toolCode\":\"CHNS\",\"toolType\":\"Chainsaw\",\"toolBrand\":\"Stihl\",\"rentalDays\":5,\"checkoutDate\":\"07/02/15\","
                                + "\"dueDate\":\"07/07/15\",\"dailyRentalCharge\":\"$1.49\",\"chargeDays\":3,\"preDiscountCharge\":\"$4.47\","
                                + "\"discountPercent\":\"25%\",\"discountAmount\":\"$1.12\",\"finalCharge\":\"$3.35\"}"));
    }

    @Test
    void testGetRentalAgreementValidInputJAKD() throws Exception {
        when(checkoutService.checkout(Mockito.<CheckoutRequest>any()))
                .thenReturn(new RentalAgreement("JAKD", "Jackhammer", "DeWalt", 5L, "09/03/15", "09/08/15",
                        "$2.99", 2L, "$5.98", "0%", "$0", "$5.98"));

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode("JAKD");
        checkoutRequest.setRentalDay(5L);
        checkoutRequest.setDiscountPercent(0.0d);
        checkoutRequest.setCheckoutDate("09/03/15");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"toolCode\":\"JAKD\",\"toolType\":\"Jackhammer\",\"toolBrand\":\"DeWalt\",\"rentalDays\":5,\"checkoutDate\":\"09/03/15\","
                                + "\"dueDate\":\"09/08/15\",\"dailyRentalCharge\":\"$2.99\",\"chargeDays\":2,\"preDiscountCharge\":\"$5.98\","
                                + "\"discountPercent\":\"0%\",\"discountAmount\":\"$0\",\"finalCharge\":\"$5.98\"}"));
    }

    @Test
    void testGetRentalAgreementValidInputJAKR() throws Exception {

        when(checkoutService.checkout(Mockito.<CheckoutRequest>any()))
                .thenReturn(new RentalAgreement("JAKR", "Jackhammer", "Redgid", 9L, "07/02/15", "07/11/15",
                        "$2.99", 5L, "$14.95", "0%", "$0", "$14.95"));

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode("JAKR");
        checkoutRequest.setRentalDay(9L);
        checkoutRequest.setDiscountPercent(0.0d);
        checkoutRequest.setCheckoutDate("07/02/15");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"toolCode\":\"JAKR\",\"toolType\":\"Jackhammer\",\"toolBrand\":\"Redgid\",\"rentalDays\":9,\"checkoutDate\":\"07/02/15\","
                                + "\"dueDate\":\"07/11/15\",\"dailyRentalCharge\":\"$2.99\",\"chargeDays\":5,\"preDiscountCharge\":\"$14.95\","
                                + "\"discountPercent\":\"0%\",\"discountAmount\":\"$0\",\"finalCharge\":\"$14.95\"}"));
    }


    @Test
    void testGetRentalAgreementValidInputJAKRDiscount() throws Exception {

        when(checkoutService.checkout(Mockito.<CheckoutRequest>any()))
                .thenReturn(new RentalAgreement("JAKR", "Jackhammer", "Redgid", 4L, "07/02/20", "07/06/20",
                        "$2.99", 1L, "$2.99", "50%", "$1.5", "$1.5"));

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setToolCode("JAKR");
        checkoutRequest.setRentalDay(4L);
        checkoutRequest.setDiscountPercent(50.0d);
        checkoutRequest.setCheckoutDate("07/02/20");
        String content = (new ObjectMapper()).writeValueAsString(checkoutRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(checkoutController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"toolCode\":\"JAKR\",\"toolType\":\"Jackhammer\",\"toolBrand\":\"Redgid\",\"rentalDays\":4,\"checkoutDate\":\"07/02/20\","
                                + "\"dueDate\":\"07/06/20\",\"dailyRentalCharge\":\"$2.99\",\"chargeDays\":1,\"preDiscountCharge\":\"$2.99\","
                                + "\"discountPercent\":\"50%\",\"discountAmount\":\"$1.5\",\"finalCharge\":\"$1.5\"}"));
    }



}
