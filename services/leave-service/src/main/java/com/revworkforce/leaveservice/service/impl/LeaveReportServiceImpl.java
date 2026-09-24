package com.revworkforce.leaveservice.service.impl;

import com.revworkforce.leaveservice.dto.response.LeaveSummaryResponseDto;
import com.revworkforce.leaveservice.entity.enums.LeaveStatus;
import com.revworkforce.leaveservice.repository.LeaveRequestRepository;
import com.revworkforce.leaveservice.service.LeaveReportService;
import org.springframework.stereotype.Service;

@Service
public class LeaveReportServiceImpl implements LeaveReportService {

    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveReportServiceImpl(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @Override
    public LeaveSummaryResponseDto getLeaveSummary() {

        long pendingApplications =
                leaveRequestRepository.countByStatus(LeaveStatus.PENDING);

        long approvedApplications =
                leaveRequestRepository.countByStatus(LeaveStatus.APPROVED);

        long rejectedApplications =
                leaveRequestRepository.countByStatus(LeaveStatus.REJECTED);

        long cancelledApplications =
                leaveRequestRepository.countByStatus(LeaveStatus.CANCELLED);

        long totalApplications =
                pendingApplications
                        + approvedApplications
                        + rejectedApplications
                        + cancelledApplications;

        double utilizationPercentage = totalApplications == 0
                ? 0.0
                : (approvedApplications * 100.0) / totalApplications;

        return new LeaveSummaryResponseDto(
                totalApplications,
                approvedApplications,
                rejectedApplications,
                pendingApplications,
                utilizationPercentage
        );
    }
}