package com.revworkforce.employee_management_service.repository;

import com.revworkforce.employee_management_service.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignationRepository extends JpaRepository<Designation, Long> {
}

