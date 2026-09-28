package com.revworkforce.employee_management_service.controller;

import com.revworkforce.employee_management_service.dto.DesignationDto;
import com.revworkforce.employee_management_service.service.DesignationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/designations")
public class DesignationController {

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    @PostMapping
    public DesignationDto createDesignation(
            @Valid @RequestBody DesignationDto designationDto) {

        return designationService.createDesignation(designationDto);
    }

    @GetMapping
    public List<DesignationDto> getAllDesignations() {

        return designationService.getAllDesignations();
    }

    @GetMapping("/{id}")
    public DesignationDto getDesignationById(
            @PathVariable Long id) {

        return designationService.getDesignationById(id);
    }

    @PutMapping("/{id}")
    public DesignationDto updateDesignation(
            @PathVariable Long id,
            @Valid @RequestBody DesignationDto designationDto) {

        return designationService.updateDesignation(id, designationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDesignation(
            @PathVariable Long id) {

        designationService.deleteDesignation(id);
    }
}
