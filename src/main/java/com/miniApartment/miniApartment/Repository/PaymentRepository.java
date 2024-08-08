package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Entity.Payment;
import com.miniApartment.miniApartment.dto.OnTimePaymentMonthsDTO;
import com.miniApartment.miniApartment.dto.PaymentStatusRoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query(value = "SELECT \n" +
            "    roomId,\n" +
            "    SUM(CASE WHEN `month` = 1 THEN paid ELSE 0 END) AS Jan,\n" +
            "    SUM(CASE WHEN `month` = 2 THEN paid ELSE 0 END) AS Feb,\n" +
            "    SUM(CASE WHEN `month` = 3 THEN paid ELSE 0 END) AS Mar,\n" +
            "    SUM(CASE WHEN `month` = 4 THEN paid ELSE 0 END) AS Apr,\n" +
            "    SUM(CASE WHEN `month` = 5 THEN paid ELSE 0 END) AS May,\n" +
            "    SUM(CASE WHEN `month` = 6 THEN paid ELSE 0 END) AS Jun,\n" +
            "    SUM(CASE WHEN `month` = 7 THEN paid ELSE 0 END) AS Jul,\n" +
            "    SUM(CASE WHEN `month` = 8 THEN paid ELSE 0 END) AS Aug,\n" +
            "    SUM(CASE WHEN `month` = 9 THEN paid ELSE 0 END) AS Sep,\n" +
            "    SUM(CASE WHEN `month` = 10 THEN paid ELSE 0 END) AS Oct,\n" +
            "    SUM(CASE WHEN `month` = 11 THEN paid ELSE 0 END) AS Nov,\n" +
            "    SUM(CASE WHEN `month` = 12 THEN paid ELSE 0 END) AS `Dec`,\n" +
            "    CASE \n" +
            "\t\tWHEN SUM(paid) >= SUM(totalFee) THEN 'Paid'\n" +
            "        WHEN SUM(paid) > 0 AND SUM(paid) < SUM(totalFee) THEN 'Partial Paid'\n" +
            "        ELSE 'Unpaid'\n" +
            "        end as `status`\n" +
            "FROM \n" +
            "    payment\n" +
            "    where `year` = :year\n" +
            "GROUP BY \n" +
            "    roomId",nativeQuery = true)
    Page<IListPayment> getListPaymentByYear(String year, Pageable pageable);
    @Query(value = "SELECT \n" +
            "    roomId,\n" +
            "    SUM(CASE WHEN `month` = 1 THEN paid ELSE 0 END) AS Jan,\n" +
            "    SUM(CASE WHEN `month` = 2 THEN paid ELSE 0 END) AS Feb,\n" +
            "    SUM(CASE WHEN `month` = 3 THEN paid ELSE 0 END) AS Mar,\n" +
            "    SUM(CASE WHEN `month` = 4 THEN paid ELSE 0 END) AS Apr,\n" +
            "    SUM(CASE WHEN `month` = 5 THEN paid ELSE 0 END) AS May,\n" +
            "    SUM(CASE WHEN `month` = 6 THEN paid ELSE 0 END) AS Jun,\n" +
            "    SUM(CASE WHEN `month` = 7 THEN paid ELSE 0 END) AS Jul,\n" +
            "    SUM(CASE WHEN `month` = 8 THEN paid ELSE 0 END) AS Aug,\n" +
            "    SUM(CASE WHEN `month` = 9 THEN paid ELSE 0 END) AS Sep,\n" +
            "    SUM(CASE WHEN `month` = 10 THEN paid ELSE 0 END) AS Oct,\n" +
            "    SUM(CASE WHEN `month` = 11 THEN paid ELSE 0 END) AS Nov,\n" +
            "    SUM(CASE WHEN `month` = 12 THEN paid ELSE 0 END) AS `Dec`,\n" +
            "    CASE \n" +
            "\t\tWHEN SUM(paid) >= SUM(totalFee) THEN 'Paid'\n" +
            "        WHEN SUM(paid) > 0 AND SUM(paid) < SUM(totalFee) THEN 'Partial Paid'\n" +
            "        ELSE 'Unpaid'\n" +
            "        end as `status`\n" +
            "FROM \n" +
            "    payment\n" +
            "    where `year` = :year and roomId =  :roomId \n" +
            "GROUP BY \n" +
            "    roomId",nativeQuery = true)
    Page<IListPayment> getListPaymentByYearAndRoomId(String year,int roomId, Pageable pageable);

    @Query(value = "SELECT new com.miniApartment.miniApartment.dto.OnTimePaymentMonthsDTO(\n" +
            "    p.month, \n" +
            "    COUNT(*), \n" +
            "    SUM(CASE WHEN p.dueDate > p.paymentDate AND p.status='paid' THEN 1 ELSE 0 END))\n" +
            "FROM \n" +
            "    Payment p\n" +
            "GROUP BY \n" +
            "    p.month")
    List<OnTimePaymentMonthsDTO> getOnTimePaymentMonths();
    @Query(value = "select new com.miniApartment.miniApartment.dto.PaymentStatusRoomDTO(roomId, totalFee, dueDate, paymentDate, status) " +
            "from Payment where month = :month")
    List<PaymentStatusRoomDTO> getPaymentRate(@Param("month") int month);
}
