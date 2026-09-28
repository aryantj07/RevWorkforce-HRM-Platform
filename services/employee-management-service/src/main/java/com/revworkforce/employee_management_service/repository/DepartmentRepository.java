package com.revworkforce.employee_management_service.repository;

import com.revworkforce.employee_management_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

