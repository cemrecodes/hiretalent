package com.hiretalent.hiretalent.entity;

public enum JobStatus {
    ACTIVE,
    PASSIVE,
    CLOSED;

	public boolean isValid(JobStatus newStatus) {
		return newStatus == ACTIVE || newStatus == PASSIVE || newStatus == CLOSED;
	}
}
