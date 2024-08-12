package com.miniApartment.miniApartment.Controller;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import com.miniApartment.miniApartment.Response.EHttpStatus;
import com.miniApartment.miniApartment.Response.Response;
import com.miniApartment.miniApartment.Services.ExpensesService;
import com.miniApartment.miniApartment.dto.ExpensesStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses_citizen")
@CrossOrigin
@PreAuthorize("hasRole('ROLE_CITIZEN')")
public class ExpensesCitizenController {
    @Autowired
    private ExpensesService expensesService;

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
}
