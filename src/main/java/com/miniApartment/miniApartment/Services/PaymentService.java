package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.dto.OnTimePaymentMonthsDTO;
import com.miniApartment.miniApartment.dto.PaymentQrDTO;
import com.miniApartment.miniApartment.dto.PaymentStatusRoomDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaymentService {

    Page<IListPayment> getPaymentByYear(Integer pageNo, Integer pageSize,String year);
    Page<IListPayment> getPaymentByYearAndRoom(Integer pageNo, Integer pageSize, String year, int roomId);

    List<OnTimePaymentMonthsDTO> getOnTimePaymentMonths();

    List<PaymentStatusRoomDTO> getPaymentStatusRoom(int month);
    IListPayment getPaymentByYearAndEmail(String year, String token);
    String getQrUrl(PaymentQrDTO dto);

    void confirmPayment(String json) throws Exception;

    String checkPay(PaymentQrDTO dto);
}
