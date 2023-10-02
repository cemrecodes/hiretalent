package com.hiretalent.hiretalent.util;

import com.hiretalent.hiretalent.entity.Applicant;
import com.hiretalent.hiretalent.entity.HrUser;

public class TestUtil {
    public static Applicant createSampleApplicant() {
        Applicant applicant = new Applicant();
        applicant.setId(1L);
        applicant.setName("John");
        applicant.setSurname("Doe");
        applicant.setEmail("john.doe@example.com");
        applicant.setPhoneNumber("1234567890");
        applicant.setLink("https://example.com/john_doe");
        applicant.setBlacklisted(false);

        return applicant;
    }

    public static HrUser createSampleHrUser() {
        HrUser user = new HrUser();
        user.setId(1L);
        user.setName("Toby");
        user.setSurname("Flenderson");

        return user;
    }
}
