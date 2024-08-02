package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, BigInteger> {

    @Query(value = "select \n" +
            "a.name as hovaten,\n" +
            "b.age as tuoi,\n" +
            "c.address as diachi\n" +
            "from table1 as a,\n" +
            "table1 as b,\n" +
            "table1 as c", nativeQuery = true)
    List<IDemoExample> getExample();
    boolean existsByRoomId(int roomId);
    Page<Contract> searchContractByRoomId(String keySearch, Pageable paging);
    Contract findContractByContractId(String contractId);
    @Query(value = "SELECT * FROM miniapartment.contract where roomId = :roomId",nativeQuery = true)
    Contract getRepesentativeByRoomId(int roomId);
    Contract findContractByRoomId(int id);
    @Query(value = "SELECT * from miniapartment.contract where roomId = :roomId", nativeQuery = true)
    Optional<Contract> findByRoomId(int roomId);
    Contract findContractById(int id);

}
