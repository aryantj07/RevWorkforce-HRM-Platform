package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.SystemConfigurationDto;

import java.util.List;

public interface SystemConfigurationService {

    SystemConfigurationDto createConfiguration(
            SystemConfigurationDto dto);

    List<SystemConfigurationDto> getAllConfigurations();

    SystemConfigurationDto getConfigurationById(Long id);

    SystemConfigurationDto updateConfiguration(
            Long id,
            SystemConfigurationDto dto);

    void deleteConfiguration(Long id);
}