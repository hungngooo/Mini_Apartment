package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query(value = "select \n" +
            "a.name as hovaten,\n" +
            "b.age as tuoi,\n" +
            "c.address as diachi\n" +
            "from table1 as a,\n" +
            "table1 as b,\n" +
            "table1 as c", nativeQuery = true)
    List<IDemoExample> getExample();
}
