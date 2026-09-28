package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.DepartmentDto;
import com.revworkforce.employee_management_service.entity.Department;
import com.revworkforce.employee_management_service.exception.DepartmentNotFoundException;
import com.revworkforce.employee_management_service.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        Department department = new Department();

        department.setName(departmentDto.getName());
        department.setDescription(departmentDto.getDescription());

        Department savedDepartment = departmentRepository.save(department);

        return convertToDto(savedDepartment);
    }

    public List<DepartmentDto> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public DepartmentDto getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(DepartmentNotFoundException::new);

        return convertToDto(department);
    }

    public DepartmentDto updateDepartment(
            Long id,
            DepartmentDto departmentDto) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(DepartmentNotFoundException::new);

        existingDepartment.setName(departmentDto.getName());
        existingDepartment.setDescription(departmentDto.getDescription());

        Department updatedDepartment =
                departmentRepository.save(existingDepartment);

        return convertToDto(updatedDepartment);
    }

    public void deleteDepartment(Long id) {

        if (!departmentRepository.existsById(id)) {
            throw new DepartmentNotFoundException();
        }

        departmentRepository.deleteById(id);
    }

    private DepartmentDto convertToDto(Department department) {

        DepartmentDto dto = new DepartmentDto();

        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());

        return dto;
    }
}

