package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesDetailRepository extends JpaRepository<ExpensesDetailEntity,Long> {
    @Query(value = "select e from ExpensesDetailEntity e where e.month = :month and e.year = :year")
    Page<ExpensesDetailEntity> getExpensesDetailEntitiesByMonth(String month, String year, Pageable pageable);
    @Query(value = "select e from ExpensesDetailEntity e where e.roomId = :roomId and e.year = :year")
    Page<ExpensesDetailEntity> getExpensesDetailEntitiesByRoomId(int roomId,String year, Pageable pageable);
    @Query(value = "select e from ExpensesDetailEntity e where e.roomId = :roomId and  e.year = :year and e.month = :month")
    ExpensesDetailEntity getExpensesDetailEntitiesByRoomIdAndYearAndMonth(int roomId, String year, int month);
}
