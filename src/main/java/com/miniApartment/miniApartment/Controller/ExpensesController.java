package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.ExpensesService;
import com.miniApartment.miniApartment.dto.ExpensesStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;

    @PostMapping("/addNewExpenses")
    public Response<?> addNewExpense(@RequestBody ExpensesDetailEntity expensesDetailEntity) {
        return new Response<>(EHttpStatus.OK, expensesService.addNewExpenses(expensesDetailEntity));
    }

    @GetMapping("/getExpensesBymonth")
    public Response<?> getExpensesByMonth(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam String year, @RequestParam String month) {
        return new Response<>(EHttpStatus.OK, expensesService.getExpensesDetailByMonth(pageNo, pageSize, year, month));
    }

    @GetMapping("/getExpensesByRoom")
    public Response<?> getExpensesByRoom(@RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "5") Integer pageSize,
                                         @RequestParam String year, @RequestParam int roomId) {
        return new Response<>(EHttpStatus.OK, expensesService.getExpensesDetailByRoom(pageNo, pageSize, year, roomId));
    }

    @GetMapping("/getExpensesByRoomAndMonth")
    public Response<?> getExpensesByRoom(@RequestParam String year, @RequestParam int month, @RequestParam int roomId) {
        return new Response<>(EHttpStatus.OK, expensesService.getExpensesByMonthAndRomm(year, month, roomId));
    }
    @PostMapping("/updateStatus")
    public Response<?> updateExpensesStatus(@RequestBody ExpensesStatusDTO dto){
        return new Response<>(EHttpStatus.OK,expensesService.updateExpensesStatus(dto));
    }
    @DeleteMapping("/deleteExpenses")
    public Response<?> deleteExpenses(@RequestParam String year, @RequestParam int month, @RequestParam int roomId){
        return new Response<>(EHttpStatus.OK, expensesService.deleteExpenses(year,month,roomId));
    }

}
