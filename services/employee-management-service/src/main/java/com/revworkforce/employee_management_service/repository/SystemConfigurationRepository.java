package com.revworkforce.employee_management_service.repository;

import com.revworkforce.employee_management_service.entity.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigurationRepository
        extends JpaRepository<SystemConfiguration, Long> {
}