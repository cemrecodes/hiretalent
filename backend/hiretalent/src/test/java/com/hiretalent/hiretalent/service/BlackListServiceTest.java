package com.hiretalent.hiretalent.service;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.BlackList;
import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.repository.BlackListRepository;
import com.hiretalent.hiretalent.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlackListServiceTest {

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private JobApplicationService applicationService;

    @Mock
    private ApplicantService applicantService;

    @InjectMocks
    private BlackListService blackListService;

    @Test
    void isBlacklisted() {
        Applicant applicant = TestUtil.createSampleApplicant();

        when(blackListRepository.existsByApplicant(applicant)).thenReturn(false);

        boolean result = blackListService.isBlacklisted(applicant);

        assertFalse(result);

        verify(blackListRepository, times(1)).existsByApplicant(applicant);
    }

    /*
    @Test
    void addToBlackList() {
        Applicant applicant = TestUtil.createSampleApplicant();
        HrUser hrUser = TestUtil.createSampleHrUser();
        String reason = "Some reason";

        BlackList savedBlackList = new BlackList();
        when(blackListRepository.save(any(BlackList.class))).thenReturn(savedBlackList);

        BlackList result = blackListService.addToBlackList(applicant, hrUser, reason);

        assertNotNull(result);

        verify(applicationService, times(1)).rejectAllJobApplication(applicant.getId());
        verify(applicantService, times(1)).addApplicantToBlacklist(applicant);
        verify(blackListRepository, times(1)).save(any(BlackList.class));

        // Additional assertions to check the saved values
        assertEquals(applicant, result.getApplicant());
        assertEquals(hrUser, result.getHrUser());
        assertEquals(reason, result.getReason());
    }
    */




}