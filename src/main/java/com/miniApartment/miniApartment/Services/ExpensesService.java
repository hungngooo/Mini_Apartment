package com.miniApartment.miniApartment.Services;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import com.miniApartment.miniApartment.dto.ExpensesStatusDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

public interface ExpensesService {
    String addNewExpenses(ExpensesDetailEntity entity);

    Page<ExpensesDetailEntity> getExpensesDetailByMonth(Integer pageNo, Integer pageSize, String year, String month);

    Page<ExpensesDetailEntity> getExpensesDetailByRoom(Integer pageNo, Integer pageSize, String year, int room);

    ExpensesDetailEntity getExpensesByMonthAndRomm(String year, int month, int room);


    String updateExpensesStatus(@NotNull ExpensesStatusDTO dto);
}
