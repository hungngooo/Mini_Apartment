package com.miniApartment.miniApartment.Repository;

import com.miniApartment.miniApartment.Entity.Contract;
import com.miniApartment.miniApartment.Entity.IDemoExample;
import com.miniApartment.miniApartment.dto.TenantThisMonthDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = "SELECT * FROM miniapartment.contract where roomId = :roomId", nativeQuery = true)
    Contract getRepesentativeByRoomId(int roomId);

    Contract findContractByRoomId(int id);

    @Query(value = "SELECT * from miniapartment.contract where roomId = :roomId", nativeQuery = true)
    Optional<Contract> findByRoomId(int roomId);

    Contract findContractById(int id);

    @Query(value = "WITH months AS ( " +
            "SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION " +
            "SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12), " +
            "tenant_counts AS (SELECT m.month, COALESCE(SUM(c.numberOfTenant), 0) AS tenantCount " +
            "FROM months m LEFT JOIN Contract c ON m.month BETWEEN EXTRACT(MONTH FROM c.moveinDate) AND EXTRACT(MONTH FROM c.expireDate) " +
            "GROUP BY m.month) " +
            "SELECT t.month, t.tenantCount FROM tenant_counts t " +
            "ORDER BY t.month", nativeQuery = true)
    List<Object[]> countTenantsEachMonth();

    @Query(value = "select new com.miniApartment.miniApartment.dto.TenantThisMonthDTO(r.roomId, r.maxTenant," +
            "SUM(CASE WHEN :lastMonth BETWEEN MONTH(c.moveinDate) AND MONTH(c.expireDate) \n" +
            "            THEN c.numberOfTenant ELSE 0 END) , \n" +
            "            SUM(CASE WHEN :currentMonth BETWEEN MONTH(c.moveinDate) AND MONTH(c.expireDate) \n" +
            "           THEN c.numberOfTenant ELSE 0 END))" +
            " from RoomEntity r left join Contract c on r.roomId = c.roomId group by r.roomId, r.maxTenant")
    List<TenantThisMonthDTO> findTenantThisMonth(@Param("lastMonth") int lastMonth, @Param("currentMonth") int currentMonth);

    @Query(value = "select COALESCE(sum(c.numberOfTenant), 0) from Contract c where :month BETWEEN MONTH(c.moveinDate) AND MONTH(c.expireDate)", nativeQuery = true)
    int countAllTenant(@Param("month") int month);
}