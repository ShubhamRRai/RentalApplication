package org.example.rentalapplication.service.impl;

import org.example.rentalapplication.dto.CheckoutRequest;
import org.example.rentalapplication.dto.RentalAgreement;
import org.example.rentalapplication.entity.Tool;
import org.example.rentalapplication.repository.ToolRepository;
import org.example.rentalapplication.service.CheckoutService;
import org.example.rentalapplication.util.ConversionUtil;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;


@Service
public class CheckoutServiceImpl implements CheckoutService {

    final ToolRepository toolRepository;

    public CheckoutServiceImpl(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    /**
     *
     * @param checkoutRequest
     * @return RentalAgreement based on the discounts given
     */
    @Override
    public RentalAgreement checkout(CheckoutRequest checkoutRequest) {
        Tool tool = toolRepository.getToolsByToolCode(checkoutRequest.getToolCode());
        RentalAgreement agreement = new RentalAgreement();
        LocalDate checkoutDate = ConversionUtil.stringToDate(checkoutRequest.getCheckoutDate());
        LocalDate dueDate = checkoutDate.plusDays(checkoutRequest.getRentalDay());
        long freeDaysCount = 0;
        if(!tool.getToolType().isHolidayCharge())
            freeDaysCount += holidaysInRange(checkoutDate, dueDate);
        if(!tool.getToolType().isWeekendCharge()) {
            freeDaysCount +=  weekendInRange(checkoutDate, dueDate);
        }
        double preDiscountCharge = tool.getToolType().getDailyCharge() * (checkoutRequest.getRentalDay() - freeDaysCount);
        double discountAmount = preDiscountCharge * checkoutRequest.getDiscountPercent() / 100;
        double finalCharge = preDiscountCharge - discountAmount;
        agreement.setToolCode(tool.getToolCode());
        agreement.setToolType(tool.getToolType().getName());
        agreement.setToolBrand(tool.getBrand());
        agreement.setDailyRentalCharge(ConversionUtil.doubleToDollar(tool.getToolType().getDailyCharge()));
        agreement.setDiscountPercent(ConversionUtil.doubleToPercentage(checkoutRequest.getDiscountPercent()));
        agreement.setRentalDays(checkoutRequest.getRentalDay());
        agreement.setChargeDays(checkoutRequest.getRentalDay() - freeDaysCount);
        agreement.setPreDiscountCharge(ConversionUtil.doubleToDollar(preDiscountCharge));
        agreement.setDiscountAmount(ConversionUtil.doubleToDollar(discountAmount));
        agreement.setFinalCharge(ConversionUtil.doubleToDollar(finalCharge));
        agreement.setDueDate(ConversionUtil.dateToString(dueDate));
        agreement.setCheckoutDate(checkoutRequest.getCheckoutDate());
        return agreement;
    }


    /**
     *
     * @param date
     * @return if the give date is a holiday or not
     */
    private boolean isHoliday(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4);

        // Adjust Independence Day for weekend observance
        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            independenceDay = independenceDay.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            independenceDay = independenceDay.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        // Check for Independence Day and Labor Day
        return date.isEqual(independenceDay) ||
                date.getMonth() == Month.SEPTEMBER &&
                        date.getDayOfWeek() == DayOfWeek.MONDAY &&
                        date.with(TemporalAdjusters.firstDayOfMonth()).isBefore(date);
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return count of weekend days
     */
    private  long weekendInRange(LocalDate startDate, LocalDate endDate) {
        long weekendCount = 0;
        // Check if the day is weekend
        while(startDate.isBefore(endDate.plusDays(1))) {
            if(startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY)
                weekendCount++;
            startDate = startDate.plusDays(1);
        }
        return weekendCount;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return count of holidays in the range of startDate and endDate
     */
    private long holidaysInRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        long holidayCount = 0;
        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate.plusDays(1))) {
            if (isHoliday(currentDate)) {
                holidayCount++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return holidayCount;
    }


}
