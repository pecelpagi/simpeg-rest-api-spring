package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.model.EmployeeRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.repository.EmployeePositionRepository;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePositionRepository employeePositionRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public SavedResponse create(EmployeeRequest request) {
        validationService.validate(request);

        EmployeePosition employeePosition = employeePositionRepository.findById(request.getEmployeePositionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Position ID is not found"));

        log.info("GENDER {}", request.getGender());

        Employee employee = new Employee();
        employee.setIdNumber(request.getIdNumber());
        employee.setName(request.getName());
        employee.setGender(request.getGender());
        employee.setDepartmentCode(request.getDepartmentCode());
        employee.setEntryDate(request.getEntryDate());
        employee.setAddress(request.getAddress());
        employee.setCity(request.getCity());
        employee.setOriginCity(request.getOriginCity());
        employee.setBirthplace(request.getBirthplace());
        employee.setBirthdate(request.getBirthdate());
        employee.setEmployeePosition(employeePosition);
        employee.setReligion(request.getReligion());
        employee.setCitizen(request.getCitizen());
        employee.setMaritalStatus(request.getMaritalStatus());
        employee.setIncomeTaxStatus(request.getIncomeTaxStatus());
        employee.setBloodType(request.getBloodType());
        employee.setBpjsHealth(request.getBpjsHealth());
        employee.setBpjsRetirement(request.getBpjsRetirement());
        employee.setBpjsEmployment(request.getBpjsEmployment());
        employee.setProfilePicture(request.getProfilePicture());

        employee = employeeRepository.save(employee);

        return SavedResponse.builder()
                .id(employee.getId())
                .label(employee.getName())
                .build();
    }

}
