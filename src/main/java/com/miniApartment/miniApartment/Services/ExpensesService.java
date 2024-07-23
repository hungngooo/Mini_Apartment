package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import org.springframework.data.domain.Page;

public interface ExpensesService {
    void addNewExpenses(ExpensesDetailEntity entity);

    Page<ExpensesDetailEntity> getExpensesDetailByMonth(Integer pageNo, Integer pageSize, String year, String month);

    Page<ExpensesDetailEntity> getExpensesDetailByRoom(Integer pageNo, Integer pageSize, String year, int room);
}
