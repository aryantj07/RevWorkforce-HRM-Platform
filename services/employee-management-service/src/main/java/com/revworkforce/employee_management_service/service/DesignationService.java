package com.revworkforce.employee_management_service.service;

import com.revworkforce.employee_management_service.dto.DesignationDto;
import com.revworkforce.employee_management_service.entity.Designation;
import com.revworkforce.employee_management_service.exception.DesignationNotFoundException;
import com.revworkforce.employee_management_service.repository.DesignationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationService {

    private final DesignationRepository designationRepository;

    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    public DesignationDto createDesignation(DesignationDto designationDto) {

        Designation designation = new Designation();

        designation.setName(designationDto.getName());
        designation.setDescription(designationDto.getDescription());

        Designation savedDesignation = designationRepository.save(designation);

        return convertToDto(savedDesignation);
    }

    public List<DesignationDto> getAllDesignations() {

        return designationRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public DesignationDto getDesignationById(Long id) {

        Designation designation = designationRepository.findById(id)
                .orElseThrow(DesignationNotFoundException::new);

        return convertToDto(designation);
    }

    public DesignationDto updateDesignation(
            Long id,
            DesignationDto designationDto) {

        Designation existingDesignation =
                designationRepository.findById(id)
                        .orElseThrow(DesignationNotFoundException::new);

        existingDesignation.setName(designationDto.getName());
        existingDesignation.setDescription(designationDto.getDescription());

        Designation updatedDesignation =
                designationRepository.save(existingDesignation);

        return convertToDto(updatedDesignation);
    }

    public void deleteDesignation(Long id) {

        if (!designationRepository.existsById(id)) {
            throw new DesignationNotFoundException();
        }

        designationRepository.deleteById(id);
    }

    private DesignationDto convertToDto(Designation designation) {

        DesignationDto dto = new DesignationDto();

        dto.setId(designation.getId());
        dto.setName(designation.getName());
        dto.setDescription(designation.getDescription());

        return dto;
    }
}

