package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.EmployeeDto;
import com.revworkforce.employee_management_service.entity.Employee;
import com.revworkforce.employee_management_service.exception.EmployeeNotFoundException;
import com.revworkforce.employee_management_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import com.revworkforce.employee_management_service.exception.DepartmentNotFoundException;
import com.revworkforce.employee_management_service.exception.DesignationNotFoundException;
import com.revworkforce.employee_management_service.repository.DepartmentRepository;
import com.revworkforce.employee_management_service.repository.DesignationRepository;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            DesignationRepository designationRepository) {

        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.designationRepository = designationRepository;
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        if (!departmentRepository.existsById(employeeDto.getDepartmentId())) {
            throw new DepartmentNotFoundException();
        }

        if (!designationRepository.existsById(employeeDto.getDesignationId())) {
            throw new DesignationNotFoundException();
        }

        validateEmployeeStatus(employeeDto.getStatus());

        Employee employee = new Employee();

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setStatus(employeeDto.getStatus());
        employee.setDateOfJoining(employeeDto.getDateOfJoining());
        employee.setAddress(employeeDto.getAddress());
        employee.setDepartmentId(employeeDto.getDepartmentId());
        employee.setDesignationId(employeeDto.getDesignationId());

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

        if (!departmentRepository.existsById(employeeDto.getDepartmentId())) {
            throw new DepartmentNotFoundException();
        }

        if (!designationRepository.existsById(employeeDto.getDesignationId())) {
            throw new DesignationNotFoundException();
        }

        validateEmployeeStatus(employeeDto.getStatus());

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
        existingEmployee.setStatus(employeeDto.getStatus());
        existingEmployee.setDateOfJoining(employeeDto.getDateOfJoining());
        existingEmployee.setAddress(employeeDto.getAddress());
        existingEmployee.setDepartmentId(employeeDto.getDepartmentId());
        existingEmployee.setDesignationId(employeeDto.getDesignationId());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return convertToDto(updatedEmployee);
    }

    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException();
        }

        employeeRepository.deleteById(id);
    }

    private void validateEmployeeStatus(String status) {

        if (status == null
                || (!status.equals("ACTIVE")
                && !status.equals("INACTIVE")
                && !status.equals("OFFBOARDED"))) {

            throw new IllegalArgumentException(
                    "Status must be ACTIVE, INACTIVE, or OFFBOARDED");
        }
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
        dto.setDepartmentId(employee.getDepartmentId());
        dto.setDesignationId(employee.getDesignationId());

        return dto;
    }
}