package com.revworkforce.leaveservice.repository;

import com.revworkforce.leaveservice.entity.LeaveRequest;
import com.revworkforce.leaveservice.entity.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeIdOrderByAppliedAtDesc(Long employeeId);
    List<LeaveRequest> findByStatusOrderByAppliedAtAsc(LeaveStatus status);
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, LeaveStatus status);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.employeeId = :employeeId " +
           "AND lr.status IN ('PENDING', 'APPROVED') " +
           "AND ((lr.startDate BETWEEN :startDate AND :endDate) " +
           "OR (lr.endDate BETWEEN :startDate AND :endDate) " +
           "OR (:startDate BETWEEN lr.startDate AND lr.endDate))")
    List<LeaveRequest> findOverlappingRequests(@Param("employeeId") Long employeeId,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);
}
