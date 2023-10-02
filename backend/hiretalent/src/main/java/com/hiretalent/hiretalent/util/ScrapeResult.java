package com.hiretalent.hiretalent.util;


import lombok.Getter;

@Getter
public class ScrapeResult {
    private final boolean success;
    private String errorMessage;

    public ScrapeResult(boolean success) {
        this.success = success;
    }

    public ScrapeResult(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

}
