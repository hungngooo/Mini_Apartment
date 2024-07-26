package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import com.miniApartment.miniApartment.Entity.Payment;
import com.miniApartment.miniApartment.Repository.ExpensesDetailRepository;
import com.miniApartment.miniApartment.Repository.PaymentRepository;
import com.miniApartment.miniApartment.Services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ExpensesServiceImpl implements ExpensesService {
    @Autowired
    private ExpensesDetailRepository repository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public String addNewExpenses(ExpensesDetailEntity entity) {
        if (validateExpensesDetail(entity)) {
            if (checkExpensesExist(entity.getRoomId(), entity.getYear(), entity.getMonth())) {
                entity.setElectricity((entity.getElectricCurrentMeter() - entity.getElectricPreviousMeter()) * 3800);
                entity.setWater((entity.getWaterCurrentMeter() - entity.getWaterPreviousMeter()) * 35000);
                repository.save(entity);
                this.addPaymentEntity(entity);
                return "add success";
            }
        }
        return "add fail";
    }

    private boolean checkExpensesExist(int roomId, String year, int month) {
        if (repository.getExpensesDetailEntitiesByRoomIdAndYearAndMonth(roomId, year, month) != null) return false;
        return true;
    }

    private void addPaymentEntity(ExpensesDetailEntity expensesDetailEntity) {
        Payment payment = new Payment();
        payment.setId(expensesDetailEntity.getId());
        payment.setRoomId(expensesDetailEntity.getRoomId());
        payment.setYear(expensesDetailEntity.getYear());
        payment.setMonth(expensesDetailEntity.getMonth());
        double totalFee = expensesDetailEntity.getRentalFee() + expensesDetailEntity.getWater() + expensesDetailEntity.getElectricity()
                + expensesDetailEntity.getService() + expensesDetailEntity.getInternet() + expensesDetailEntity.getDebt()
                + expensesDetailEntity.getFine() + expensesDetailEntity.getSecurityDeposite();
        payment.setTotalFee(totalFee);
        payment.setStatus(expensesDetailEntity.getStatus());
        paymentRepository.save(payment);
    }

    @Override
    public Page<ExpensesDetailEntity> getExpensesDetailByMonth(Integer pageNo, Integer pageSize, String year, String month) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return repository.getExpensesDetailEntitiesByMonth(month, year, paging);
    }

    @Override
    public Page<ExpensesDetailEntity> getExpensesDetailByRoom(Integer pageNo, Integer pageSize, String year, int room) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return repository.getExpensesDetailEntitiesByRoomId(room, year, paging);
    }

    private boolean validateExpensesDetail(ExpensesDetailEntity expensesDetail) {
        if (expensesDetail.getRoomId() <= 0) {
            System.err.println("Room ID must be greater than zero");
            return false;
        }
        if (!isValidYear(expensesDetail.getYear())) {
            System.err.println("Invalid year format");
            return false;
        }
        if (expensesDetail.getMonth() < 1 || expensesDetail.getMonth() > 12) {
            System.err.println("Month must be between 1 and 12");
            return false;
        }
        if (expensesDetail.getRentalFee() < 0) {
            System.err.println("Rental Fee cannot be negative");
            return false;
        }
        if (expensesDetail.getElectricity() < 0) {
            System.err.println("Electricity cannot be negative");
            return false;
        }
        if (expensesDetail.getElectricPreviousMeter() < 0 || expensesDetail.getElectricCurrentMeter() < expensesDetail.getElectricPreviousMeter()) {
            System.err.println("Invalid electric meter readings");
            return false;
        }
        if (expensesDetail.getWater() < 0) {
            System.err.println("Water cannot be negative");
            return false;
        }
        if (expensesDetail.getWaterPreviousMeter() < 0 || expensesDetail.getWaterCurrentMeter() < expensesDetail.getWaterPreviousMeter()) {
            System.err.println("Invalid water meter readings");
            return false;
        }
        if (expensesDetail.getInternet() < 0) {
            System.err.println("Internet cannot be negative");
            return false;
        }
        if (expensesDetail.getService() < 0) {
            System.err.println("Service cannot be negative");
            return false;
        }
        if (expensesDetail.getSecurityDeposite() < 0) {
            System.err.println("Security Deposit cannot be negative");
            return false;
        }
        if (expensesDetail.getDebt() < 0) {
            System.err.println("Debt cannot be negative");
            return false;
        }
        if (expensesDetail.getFine() < 0) {
            System.err.println("Fine cannot be negative");
            return false;
        }
        if (expensesDetail.getStatus() == null || expensesDetail.getStatus().isEmpty()) {
            System.err.println("Status cannot be null or empty");
            return false;
        }

        return true;
    }

    private boolean isValidYear(String year) {
        String regex = "^[1-9][0-9]{3}$";
        return Pattern.matches(regex, year);
    }
}
