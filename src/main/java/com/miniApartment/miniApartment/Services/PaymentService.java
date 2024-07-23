package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Entity.Payment;
import com.miniApartment.miniApartment.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PaymentService {

    Page<IListPayment> getPaymentByYear(Integer pageNo, Integer pageSize,String year);
}
