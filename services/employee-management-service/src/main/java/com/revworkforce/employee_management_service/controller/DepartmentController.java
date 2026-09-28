package com.revworkforce.employee_management_service.controller;

import com.revworkforce.employee_management_service.dto.DepartmentDto;
import com.revworkforce.employee_management_service.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentDto createDepartment(
            @Valid @RequestBody DepartmentDto departmentDto) {

        return departmentService.createDepartment(departmentDto);
    }

    @GetMapping
    public List<DepartmentDto> getAllDepartments() {

        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(
            @PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDto departmentDto) {

        return departmentService.updateDepartment(id, departmentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);
    }
}
