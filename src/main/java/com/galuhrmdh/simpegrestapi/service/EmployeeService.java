package com.galuhrmdh.simpegrestapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.galuhrmdh.simpegrestapi.model.JobMessage;
import com.galuhrmdh.simpegrestapi.RabbitMQUtil;
import com.galuhrmdh.simpegrestapi.entity.Department;
import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.EmployeePosition;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.employee.CreateEmployeeRequest;
import com.galuhrmdh.simpegrestapi.model.employee.EmployeeCitizenRecap;
import com.galuhrmdh.simpegrestapi.model.employee.EmployeeResponse;
import com.galuhrmdh.simpegrestapi.model.employee.UpdateEmployeeRequest;
import com.galuhrmdh.simpegrestapi.model.employeeposition.EmployeePositionRecap;
import com.galuhrmdh.simpegrestapi.repository.DepartmentRepository;
import com.galuhrmdh.simpegrestapi.repository.EmployeePositionRepository;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeePositionRepository employeePositionRepository;

    @Autowired
    private ValidationService validationService;

    private EmployeeResponse toEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .idNumber(employee.getIdNumber())
                .name(employee.getName())
                .gender(employee.getGender())
                .department(employee.getDepartment())
                .entryDate(employee.getEntryDate())
                .address(employee.getAddress())
                .city(employee.getCity())
                .originCity(employee.getOriginCity())
                .birthplace(employee.getBirthplace())
                .birthdate(employee.getBirthdate())
                .employeePosition(employee.getEmployeePosition())
                .religion(employee.getReligion())
                .citizen(employee.getCitizen())
                .maritalStatus(employee.getMaritalStatus())
                .incomeTaxStatus(employee.getIncomeTaxStatus())
                .bloodType(employee.getBloodType())
                .bpjsHealth(employee.getBpjsHealth())
                .bpjsEmployment(employee.getBpjsEmployment())
                .bpjsRetirement(employee.getBpjsRetirement())
                .profilePicture(employee.getProfilePicture())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> list(ListRequest request) {
        Specification<Employee> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(builder.or(
                        builder.like(root.get("idNumber"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("name"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("address"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("city"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("originCity"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("birthplace"), "%" + request.getSearch() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Employee> employees = employeeRepository.findAll(specification, pageable);

        List<EmployeeResponse> employeeResponses = employees.stream().map(this::toEmployeeResponse).toList();

        return new PageImpl<>(employeeResponses, pageable, employees.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateEmployeeRequest request) {
        validationService.validate(request);

        EmployeePosition employeePosition = employeePositionRepository.findById(request.getEmployeePositionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Position ID is not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department ID is not found"));

        Employee employee = new Employee();
        employee.setIdNumber(request.getIdNumber());
        employee.setName(request.getName());
        employee.setGender(request.getGender());
        employee.setDepartment(department);
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


    @Transactional
    public SavedResponse update(UpdateEmployeeRequest request) {
        validationService.validate(request);

        EmployeePosition employeePosition = employeePositionRepository.findById(request.getEmployeePositionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Position ID is not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department ID is not found"));

        Employee employee = employeeRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        employee.setIdNumber(request.getIdNumber());
        employee.setName(request.getName());
        employee.setGender(request.getGender());
        employee.setDepartment(department);
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

        Employee savedEmployee = employeeRepository.save(employee);

        return SavedResponse.builder()
                .id(savedEmployee.getId())
                .label(savedEmployee.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        employeeRepository.delete(employee);
    }

    public List<EmployeeCitizenRecap> getEmployeeCitizenRecap() {
        return employeeRepository.getEmployeeCitizenRecap();
    }

}
