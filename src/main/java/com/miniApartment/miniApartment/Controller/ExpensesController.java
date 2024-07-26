package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;
    @PostMapping("/addNewExpenses")
    public Response<?> addNewExpense(@RequestBody ExpensesDetailEntity expensesDetailEntity){
        expensesService.addNewExpenses(expensesDetailEntity);
        return new Response<>(EHttpStatus.OK,"add successfull");
    }
    @GetMapping("/getExpensesBymonth")
    public Response<?> getExpensesByMonth(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam String year, @RequestParam String month){
        return new Response<>(EHttpStatus.OK, expensesService.getExpensesDetailByMonth(pageNo,pageSize,year,month));
    }
}
