package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Entity.Payment;
import com.miniApartment.miniApartment.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    public List<IListPayment> getPaymentByYear(String year){
//        List<Integer> roomIdList = paymentRepository.getRoomIdDistinct();
//        List<IListPayment> iListPayments = paymentRepository.getListPaymentByYear(year);
//        for(Integer obRoomIdList: roomIdList){
//            double totalFee = paymentRepository.getTotalFeeByRoomIdAndYear(obRoomIdList,year);
//            double paid = paymentRepository.getPaidByRoomIdAndYear(obRoomIdList,year);
//            if((totalFee - paid) == totalFee){
//                for(IListPayment obIListPayments:iListPayments){
//                    if(obIListPayments.getRoomId() == obRoomIdList){
////                        obIListPayments.getStatus() = "sks";
//                    }
//                }
//            }
//        }
        return paymentRepository.getListPaymentByYear(year);
    }
}
