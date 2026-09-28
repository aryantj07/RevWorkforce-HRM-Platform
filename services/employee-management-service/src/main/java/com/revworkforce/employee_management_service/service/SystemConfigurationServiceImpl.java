package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.SystemConfigurationDto;
import com.revworkforce.employee_management_service.entity.SystemConfiguration;
import com.revworkforce.employee_management_service.exception.SystemConfigurationNotFoundException;
import com.revworkforce.employee_management_service.repository.SystemConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigurationServiceImpl
        implements SystemConfigurationService {

    private final SystemConfigurationRepository configurationRepository;

    public SystemConfigurationServiceImpl(
            SystemConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public SystemConfigurationDto createConfiguration(
            SystemConfigurationDto dto) {

        SystemConfiguration configuration =
                new SystemConfiguration();

        configuration.setConfigKey(dto.getConfigKey());
        configuration.setConfigValue(dto.getConfigValue());
        configuration.setDescription(dto.getDescription());

        SystemConfiguration savedConfiguration =
                configurationRepository.save(configuration);

        return convertToDto(savedConfiguration);
    }

    @Override
    public List<SystemConfigurationDto> getAllConfigurations() {

        return configurationRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public SystemConfigurationDto getConfigurationById(Long id) {

        SystemConfiguration configuration =
                configurationRepository.findById(id)
                        .orElseThrow(
                                SystemConfigurationNotFoundException::new);

        return convertToDto(configuration);
    }

    @Override
    public SystemConfigurationDto updateConfiguration(
            Long id,
            SystemConfigurationDto dto) {

        SystemConfiguration existingConfiguration =
                configurationRepository.findById(id)
                        .orElseThrow(
                                SystemConfigurationNotFoundException::new);

        existingConfiguration.setConfigKey(dto.getConfigKey());
        existingConfiguration.setConfigValue(dto.getConfigValue());
        existingConfiguration.setDescription(dto.getDescription());

        SystemConfiguration updatedConfiguration =
                configurationRepository.save(existingConfiguration);

        return convertToDto(updatedConfiguration);
    }

    @Override
    public void deleteConfiguration(Long id) {

        if (!configurationRepository.existsById(id)) {
            throw new SystemConfigurationNotFoundException();
        }

        configurationRepository.deleteById(id);
    }

    private SystemConfigurationDto convertToDto(
            SystemConfiguration configuration) {

        SystemConfigurationDto dto =
                new SystemConfigurationDto();

        dto.setId(configuration.getId());
        dto.setConfigKey(configuration.getConfigKey());
        dto.setConfigValue(configuration.getConfigValue());
        dto.setDescription(configuration.getDescription());

        return dto;
    }
}