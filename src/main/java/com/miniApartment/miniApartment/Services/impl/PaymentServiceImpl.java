package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Repository.PaymentRepository;
import com.miniApartment.miniApartment.Services.JwtService;
import com.miniApartment.miniApartment.Services.PaymentService;
import com.miniApartment.miniApartment.dto.OnTimePaymentMonthsDTO;
import com.miniApartment.miniApartment.dto.PaymentStatusRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private JwtService jwtService;
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
    public  List<OnTimePaymentMonthsDTO> getOnTimePaymentMonths(){
        return paymentRepository.getOnTimePaymentMonths();
    }

    @Override
    public List<PaymentStatusRoomDTO> getPaymentStatusRoom(int month){
        List<PaymentStatusRoomDTO> paymentStatusRoomDTOS = paymentRepository.getPaymentRate(month);
        for (int i = 0; i < paymentStatusRoomDTOS.size(); i++) {
            if (paymentStatusRoomDTOS.get(i).getPaymentDate().before(paymentStatusRoomDTOS.get(i).getDueDate())
                    && "paid".equals(paymentStatusRoomDTOS.get(i).getStatus())) {
                paymentStatusRoomDTOS.get(i).setStatus("on-time");
            } else{

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

}
