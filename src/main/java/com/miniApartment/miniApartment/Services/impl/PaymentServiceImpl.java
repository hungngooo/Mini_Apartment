package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Repository.PaymentRepository;
import com.miniApartment.miniApartment.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Page<IListPayment> getPaymentByYear(Integer pageNo, Integer pageSize,String year){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return paymentRepository.getListPaymentByYear(year,paging);
    }
    @Override
    public Page<IListPayment> getPaymentByYearAndRoom(Integer pageNo, Integer pageSize, String year,int roomId){
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return paymentRepository.getListPaymentByYearAndRoomId(year,roomId,paging);
    }
}
