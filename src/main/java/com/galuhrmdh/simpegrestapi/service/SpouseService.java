package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.Spouse;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.spouse.CreateSpouseRequest;
import com.galuhrmdh.simpegrestapi.model.spouse.SpouseResponse;
import com.galuhrmdh.simpegrestapi.model.spouse.UpdateSpouseRequest;
import com.galuhrmdh.simpegrestapi.repository.SpouseRepository;
import com.galuhrmdh.simpegrestapi.repository.EmployeeRepository;
import jakarta.persistence.criteria.Predicate;
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
public class SpouseService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SpouseRepository spouseRepository;

    @Autowired
    private ValidationService validationService;

    private SpouseResponse toSpouseResponse(Spouse spouse) {
        return SpouseResponse.builder()
                .id(spouse.getId())
                .employee(spouse.getEmployee())
                .idNumber(spouse.getIdNumber())
                .name(spouse.getName())
                .birthplace(spouse.getBirthplace())
                .birthdate(spouse.getBirthdate())
                .dateOfMarriage(spouse.getDateOfMarriage())
                .spouseSequence(spouse.getSpouseSequence())
                .lastEducation(spouse.getLastEducation())
                .occupation(spouse.getOccupation())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<SpouseResponse> list(ListRequest request) {
        Employee employee;

        if (Objects.nonNull(request.getEmployeeId())) {
            employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));
        } else {
            employee = null;
        }

        Specification<Spouse> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(employee)) {
                predicates.add(builder.equal(root.get("employee"), employee));
            }

            if (Objects.nonNull(request.getSearch())) {
                predicates.add(builder.or(
                        builder.like(root.get("idNumber"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("name"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("birthplace"), "%" + request.getSearch() + "%"),
                        builder.like(root.get("occupation"), "%" + request.getSearch() + "%")
                ));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Spouse> spouses = spouseRepository.findAll(specification, pageable);

        List<SpouseResponse> spouseResponses = spouses.stream().map(this::toSpouseResponse).toList();

        return new PageImpl<>(spouseResponses, pageable, spouses.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateSpouseRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Spouse spouse = new Spouse();
        spouse.setEmployee(employee);
        spouse.setIdNumber(request.getIdNumber());
        spouse.setName(request.getName());
        spouse.setBirthplace(request.getBirthplace());
        spouse.setBirthdate(request.getBirthdate());
        spouse.setDateOfMarriage(request.getDateOfMarriage());
        spouse.setSpouseSequence(request.getSpouseSequence());
        spouse.setLastEducation(request.getLastEducation());
        spouse.setOccupation(request.getOccupation());

        Spouse savedSpouse = spouseRepository.save(spouse);

        return SavedResponse.builder()
                .id(savedSpouse.getId())
                .label(savedSpouse.getName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateSpouseRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Spouse spouse = spouseRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        spouse.setEmployee(employee);
        spouse.setIdNumber(request.getIdNumber());
        spouse.setName(request.getName());
        spouse.setBirthplace(request.getBirthplace());
        spouse.setBirthdate(request.getBirthdate());
        spouse.setDateOfMarriage(request.getDateOfMarriage());
        spouse.setSpouseSequence(request.getSpouseSequence());
        spouse.setLastEducation(request.getLastEducation());
        spouse.setOccupation(request.getOccupation());

        Spouse savedSpouse = spouseRepository.save(spouse);

        return SavedResponse.builder()
                .id(savedSpouse.getId())
                .label(savedSpouse.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        Spouse spouse = spouseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        spouseRepository.delete(spouse);
    }

}
