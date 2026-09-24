package com.revworkforce.leaveservice.repository;

import com.revworkforce.leaveservice.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    Optional<LeaveType> findByCode(String code);
    Optional<LeaveType> findByNameIgnoreCase(String name);
    List<LeaveType> findByIsActiveTrue();
    boolean existsByCode(String code);
    boolean existsByNameIgnoreCase(String name);
}
