package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query(value = "select \n" +
            "a.name as hovaten,\n" +
            "b.age as tuoi,\n" +
            "c.address as diachi\n" +
            "from table1 as a,\n" +
            "table1 as b,\n" +
            "table1 as c", nativeQuery = true)
    List<IDemoExample> getExample();
    Contract getContractByRoomId(int id);

    Page<Contract> searchContractByRoomId(String keySearch, Pageable paging);

    Page<Contract> getContractByContractId(int roomId, Pageable paging);
}
