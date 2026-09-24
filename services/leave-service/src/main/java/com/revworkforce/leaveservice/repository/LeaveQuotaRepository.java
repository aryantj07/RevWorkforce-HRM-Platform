package com.revworkforce.leaveservice.repository;

import com.revworkforce.leaveservice.entity.LeaveQuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveQuotaRepository extends JpaRepository<LeaveQuota, Long> {
    List<LeaveQuota> findByYear(int year);
    Optional<LeaveQuota> findByLeaveTypeIdAndYear(Long leaveTypeId, int year);
    boolean existsByLeaveTypeIdAndYear(Long leaveTypeId, int year);
}
