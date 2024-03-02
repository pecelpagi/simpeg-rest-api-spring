package com.galuhrmdh.simpegrestapi.service;

import com.galuhrmdh.simpegrestapi.entity.Employee;
import com.galuhrmdh.simpegrestapi.entity.Children;
import com.galuhrmdh.simpegrestapi.model.ListRequest;
import com.galuhrmdh.simpegrestapi.model.SavedResponse;
import com.galuhrmdh.simpegrestapi.model.children.CreateChildrenRequest;
import com.galuhrmdh.simpegrestapi.model.children.ChildrenResponse;
import com.galuhrmdh.simpegrestapi.model.children.UpdateChildrenRequest;
import com.galuhrmdh.simpegrestapi.repository.ChildrenRepository;
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
public class ChildrenService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ChildrenRepository childrenRepository;

    @Autowired
    private ValidationService validationService;

    private ChildrenResponse toChildrenResponse(Children children) {
        return ChildrenResponse.builder()
                .id(children.getId())
                .idNumber(children.getIdNumber())
                .employee(children.getEmployee())
                .name(children.getName())
                .birthplace(children.getBirthplace())
                .birthdate(children.getBirthdate())
                .gender(children.getGender())
                .childSequence(children.getChildSequence())
                .lastEducation(children.getLastEducation())
                .occupation(children.getOccupation())
                .childStatus(children.getChildStatus())
                .build();
    }

    @Transactional(readOnly = true)
    public Page<ChildrenResponse> list(ListRequest request) {
        Specification<Children> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

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
        Page<Children> children = childrenRepository.findAll(specification, pageable);

        List<ChildrenResponse> childrenResponses = children.stream().map(this::toChildrenResponse).toList();

        return new PageImpl<>(childrenResponses, pageable, children.getTotalElements());
    }

    @Transactional
    public SavedResponse create(CreateChildrenRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Children children = new Children();
        children.setEmployee(employee);
        children.setIdNumber(request.getIdNumber());
        children.setName(request.getName());
        children.setBirthplace(request.getBirthplace());
        children.setBirthdate(request.getBirthdate());
        children.setGender(request.getGender());
        children.setChildSequence(request.getChildSequence());
        children.setLastEducation(request.getLastEducation());
        children.setOccupation(request.getOccupation());
        children.setChildStatus(request.getChildStatus());

        Children savedChildren = childrenRepository.save(children);

        return SavedResponse.builder()
                .id(savedChildren.getId())
                .label(savedChildren.getName())
                .build();
    }

    @Transactional
    public SavedResponse update(UpdateChildrenRequest request) {
        validationService.validate(request);

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee ID is not found"));

        Children children = childrenRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        children.setEmployee(employee);
        children.setIdNumber(request.getIdNumber());
        children.setName(request.getName());
        children.setBirthplace(request.getBirthplace());
        children.setBirthdate(request.getBirthdate());
        children.setGender(request.getGender());
        children.setChildSequence(request.getChildSequence());
        children.setLastEducation(request.getLastEducation());
        children.setOccupation(request.getOccupation());
        children.setChildStatus(request.getChildStatus());

        Children savedChildren = childrenRepository.save(children);

        return SavedResponse.builder()
                .id(savedChildren.getId())
                .label(savedChildren.getName())
                .build();
    }

    @Transactional
    public void delete(Integer id) {
        Children children = childrenRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found"));
        childrenRepository.delete(children);
    }
    
}
