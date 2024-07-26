package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.IListPayment;
import com.miniApartment.miniApartment.Entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
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
}
