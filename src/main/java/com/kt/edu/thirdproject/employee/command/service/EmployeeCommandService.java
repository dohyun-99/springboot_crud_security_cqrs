package com.kt.edu.thirdproject.employee.command.service;

import com.kt.edu.thirdproject.common.annotation.Ktedu;
import com.kt.edu.thirdproject.employee.command.repository.EmployeeCommandRepository;
import com.kt.edu.thirdproject.employee.command.domain.EmployeeEntity;
import com.kt.edu.thirdproject.common.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class EmployeeCommandService {

    @Autowired
    private EmployeeCommandRepository employeecommandRepository;

    // spring profile
    @Value("${spring.profiles.active}")
    private String activeProfile;
    
    // jasypt로 저장된 비밀번호가  복호화 된다.
    @Value("${spring.datasource.password}")
    private String h2Password;

    @Transactional
    public EmployeeEntity create(EmployeeEntity employeeEntity) {
        log.info("Request to create Employee : " +  employeeEntity);
        
        log.info("Active Springboot Profile : " + activeProfile );

        if (activeProfile.equals("prd")){ // maria, mysql
            employeeEntity.setId(employeecommandRepository.retvNextVal());
        } else { // h2 db
            employeeEntity.setId(employeecommandRepository.retvNextVal_H2());;
        }
        employeeEntity.setNew(true);
        return this.employeecommandRepository.save(employeeEntity);
    }

    @Ktedu
    public EmployeeEntity update(Long id,EmployeeEntity employeeEntity) {
        log.info("Request to update Employee : " +  employeeEntity);
        EmployeeEntity employee = employeecommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setId(id);
        employee.setEmpName(employeeEntity.getEmpName());
        employee.setEmpDeptName(employeeEntity.getEmpDeptName());
        employee.setEmpTelNo(employeeEntity.getEmpTelNo());
        employee.setEmpMail(employeeEntity.getEmpMail());
        employee.setNew(false);
        return this.employeecommandRepository.save(employee);
    }

    public EmployeeEntity delete(Long id) {
        log.info("Request to delete Employee id : " +  id);
        EmployeeEntity employeeEntity = employeecommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not exist with id" +id));
        employeecommandRepository.delete(employeeEntity);
        return  employeeEntity;
    }
}
