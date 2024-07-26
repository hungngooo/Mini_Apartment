package com.miniApartment.miniApartment.Services.impl;

import com.miniApartment.miniApartment.Entity.ExpensesDetailEntity;
import com.miniApartment.miniApartment.Repository.ExpensesDetailRepository;
import com.miniApartment.miniApartment.Services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExpensesServiceImpl implements ExpensesService {
    @Autowired
    private ExpensesDetailRepository repository;
    @Override
    public void addNewExpenses(ExpensesDetailEntity entity) {
        entity.setElectricity((entity.getElectricCurrentMeter()-entity.getElectricPreviousMeter())*3800);
        entity.setWater((entity.getWaterCurrentMeter()-entity.getWaterPreviousMeter())*35000);
        repository.save(entity);
    }

    @Override
    public Page<ExpensesDetailEntity> getExpensesDetailByMonth(Integer pageNo, Integer pageSize, String year, String month) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return repository.getExpensesDetailEntitiesByMonth(month,year,paging);
    }
    @Override
    public Page<ExpensesDetailEntity> getExpensesDetailByRoom(Integer pageNo, Integer pageSize, String year, int room) {
//        Pageable paging = PageRequest.of(pageNo, pageSize);
//        return repository.findAll(paging);
        return null;
    }
}
