package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.EmployeeDto;
import com.revworkforce.employee_management_service.entity.Employee;
import com.revworkforce.employee_management_service.exception.EmployeeNotFoundException;
import com.revworkforce.employee_management_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = new Employee();

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setStatus(employeeDto.getStatus());
        employee.setDateOfJoining(employeeDto.getDateOfJoining());
        employee.setAddress(employeeDto.getAddress());

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToDto(savedEmployee);
    }

    public List<EmployeeDto> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public EmployeeDto getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        return convertToDto(employee);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
        existingEmployee.setStatus(employeeDto.getStatus());
        existingEmployee.setDateOfJoining(employeeDto.getDateOfJoining());
        existingEmployee.setAddress(employeeDto.getAddress());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return convertToDto(updatedEmployee);
    }

    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException();
        }

        employeeRepository.deleteById(id);
    }

    private EmployeeDto convertToDto(Employee employee) {

        EmployeeDto dto = new EmployeeDto();

        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setStatus(employee.getStatus());
        dto.setDateOfJoining(employee.getDateOfJoining());
        dto.setAddress(employee.getAddress());

        return dto;
    }
}