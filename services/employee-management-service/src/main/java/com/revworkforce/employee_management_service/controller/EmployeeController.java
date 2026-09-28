package com.revworkforce.employee_management_service.controller;

import com.revworkforce.employee_management_service.dto.EmployeeDto;
import com.revworkforce.employee_management_service.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeDto createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto) {

        return employeeService.createEmployee(employeeDto);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {

        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(
            @PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDto) {

        return employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);
    }
}