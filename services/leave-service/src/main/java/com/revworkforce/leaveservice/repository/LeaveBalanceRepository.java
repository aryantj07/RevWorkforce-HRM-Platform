package com.revworkforce.leaveservice.repository;

import com.revworkforce.leaveservice.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findByEmployeeIdAndYear(Long employeeId, int year);
    Optional<LeaveBalance> findByEmployeeIdAndLeaveTypeIdAndYear(Long employeeId, Long leaveTypeId, int year);
    boolean existsByEmployeeIdAndLeaveTypeIdAndYear(Long employeeId, Long leaveTypeId, int year);
}
