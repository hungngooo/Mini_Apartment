package com.miniApartment.miniApartment.Services.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Entity.Payment;
import com.miniApartment.miniApartment.Repository.ExpensesDetailRepository;
import com.miniApartment.miniApartment.Repository.PaymentRepository;
import com.miniApartment.miniApartment.Services.JwtService;
import com.miniApartment.miniApartment.Services.PaymentService;
import com.miniApartment.miniApartment.dto.OnTimePaymentMonthsDTO;
import com.miniApartment.miniApartment.dto.PaymentQrDTO;
import com.miniApartment.miniApartment.dto.PaymentStatusRoomDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ExpensesDetailRepository expensesDetailRepository;
    @Override
    public Page<IListPayment> getPaymentByYear(Integer pageNo, Integer pageSize, String year) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return paymentRepository.getListPaymentByYear(year, paging);
    }

    @Override
    public Page<IListPayment> getPaymentByYearAndRoom(Integer pageNo, Integer pageSize, String year, int roomId) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return paymentRepository.getListPaymentByYearAndRoomId(year, roomId, paging);
    }

    @Override
    public List<OnTimePaymentMonthsDTO> getOnTimePaymentMonths() {
        return paymentRepository.getOnTimePaymentMonths();
    }

    @Override
    public List<PaymentStatusRoomDTO> getPaymentStatusRoom(int month) {
        List<PaymentStatusRoomDTO> paymentStatusRoomDTOS = paymentRepository.getPaymentRate(month);
        for (int i = 0; i < paymentStatusRoomDTOS.size(); i++) {
            if (paymentStatusRoomDTOS.get(i).getPaymentDate().before(paymentStatusRoomDTOS.get(i).getDueDate())
                    && "paid".equals(paymentStatusRoomDTOS.get(i).getStatus())) {
                paymentStatusRoomDTOS.get(i).setStatus("on-time");
            } else {

                paymentStatusRoomDTOS.get(i).setStatus("overdue");
            }
        }
        return paymentStatusRoomDTOS;
    }

    @Override
    public IListPayment getPaymentByYearAndEmail(String year, String token) {
        String email = jwtService.extractUsername(token);
        int roomId = paymentRepository.getRoomIdByEmail(email);
        return paymentRepository.getListPaymentByYearAndRoomId(year, roomId);
    }

    @Override
    public String getQrUrl(@NotNull PaymentQrDTO dto) {
        Payment payment = paymentRepository.getPaymentsByRoomIdAndYearAndMonth(dto.getRoomId(), dto.getMonth(), dto.getYear());
        double totalPrice = 0;
        if(payment.getPaid() != 0){
            totalPrice = payment.getTotalFee() - payment.getPaid();
        } else {
            totalPrice = payment.getTotalFee();
        }
        String accountName = "Tran Tuan Minh";
        String bankId = "MB";
        String accountNo = "1020052412003";
        String description = "Payment for rent of " + dto.getRoomId() + " for " + dto.getMonth() + " " + dto.getYear();
        String qrUrl = "https://img.vietqr.io/image/" + bankId + "-" + accountNo + "-qr_only.png?amount=" + totalPrice + "&addInfo=" + description + "&accountName=" + accountName;
        return qrUrl;
    }

    @Override
    public void confirmPayment(String json) throws Exception {
        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonArray jsonArray = (JsonArray) jsonObject.get("data");
            // loop array
            for (JsonElement element : jsonArray) {
                JsonObject transaction = element.getAsJsonObject();
                String description = transaction.get("description").getAsString();
                System.out.println(description);
                String amount = transaction.get("amount").getAsString();
                String des[] = description.split(" ");
                String roomIdStr = "";
                String monthStr = "";
                String yearStr = "";
                Integer roomId = null;
                Integer month = null;

                Integer year = null;
                Double paid = null;
                if (des.length >= 8) {
                    roomIdStr = des[4];
                    monthStr = des[6];
                    yearStr = des[7].substring(0, 4);
                }
                try {
                    roomId = Integer.valueOf(roomIdStr);
                    month = Integer.valueOf(monthStr);
                    year = Integer.valueOf(yearStr);
                    paid = Double.valueOf(amount);
                } catch (NumberFormatException ex) {
                    throw new Exception(ex.getMessage());
                }
                Payment payment = paymentRepository.getPaymentsByRoomIdAndYearAndMonth(roomId, month, year);
                if (payment != null) {
                    if(payment.getPaid() == 0){
                        payment.setPaid(paid);
                        if(paid < payment.getTotalFee()) payment.setStatus("Partial Paid");
                        else payment.setStatus("Paid");
                    } else {
                        payment.setPaid(paid + payment.getPaid());
                        if((paid + payment.getPaid()) < payment.getTotalFee()) payment.setStatus("Partial Paid");
                        else payment.setStatus("Paid");
                    }
                    payment.setYear(payment.getYear().substring(0,4));
                    Date currentDate = new Date();
                    payment.setPaymentDate(currentDate);
                }
                paymentRepository.save(payment);
                ExpensesDetailEntity expensesDetailEntity = expensesDetailRepository.getExpensesDetailEntitiesByRoomIdAndYearAndMonth(roomId,yearStr,month);
                if(expensesDetailEntity != null){
                    expensesDetailEntity.setStatus(payment.getStatus());
                    expensesDetailEntity.setYear(expensesDetailEntity.getYear().substring(0,4));
                }
                expensesDetailRepository.save(expensesDetailEntity);
            }
        } catch (Exception e){
            System.out.println(e.getMessage()
            );
        }
    }
    @Override
    public String checkPay(PaymentQrDTO dto){
        try {
            TimeUnit.SECONDS.sleep(5);
            Payment payment = paymentRepository.getPaymentsByRoomIdAndYearAndMonth(dto.getRoomId(), dto.getMonth(), dto.getYear());
            switch (payment.getStatus()){
                case "Paid": return "complete pay";
                case "Partial Paid" : return "not complete pay";
                case "Unpaid": return "not pay";
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
