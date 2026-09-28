package com.revworkforce.employee_management_service.repository;

import com.revworkforce.employee_management_service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}