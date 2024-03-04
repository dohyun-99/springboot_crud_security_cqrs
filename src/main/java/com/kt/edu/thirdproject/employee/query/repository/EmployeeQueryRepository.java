package com.kt.edu.thirdproject.employee.query.repository;

import com.kt.edu.thirdproject.employee.query.domain.EmployeeEntity;
import com.kt.edu.thirdproject.employee.query.repository.sql.QueryEmployeeSqls;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeQueryRepository extends CrudRepository<EmployeeEntity, Long> {

    @Query(QueryEmployeeSqls.RETV_NEXT_VAL)
    Long retvNextVal();

    @Query(QueryEmployeeSqls.RETV_NEXT_VAL_H2)
    Long retvNextVal_H2();
    
}
