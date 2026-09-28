package com.revworkforce.employee_management_service.controller;

import com.revworkforce.employee_management_service.dto.SystemConfigurationDto;
import com.revworkforce.employee_management_service.service.SystemConfigurationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
public class SystemConfigurationController {

    private final SystemConfigurationService configurationService;

    public SystemConfigurationController(
            SystemConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SystemConfigurationDto createConfiguration(
            @Valid @RequestBody SystemConfigurationDto dto) {

        return configurationService.createConfiguration(dto);
    }

    @GetMapping
    public List<SystemConfigurationDto> getAllConfigurations() {

        return configurationService.getAllConfigurations();
    }

    @GetMapping("/{id}")
    public SystemConfigurationDto getConfigurationById(
            @PathVariable Long id) {

        return configurationService.getConfigurationById(id);
    }

    @PutMapping("/{id}")
    public SystemConfigurationDto updateConfiguration(
            @PathVariable Long id,
            @Valid @RequestBody SystemConfigurationDto dto) {

        return configurationService.updateConfiguration(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConfiguration(@PathVariable Long id) {

        configurationService.deleteConfiguration(id);
    }
}