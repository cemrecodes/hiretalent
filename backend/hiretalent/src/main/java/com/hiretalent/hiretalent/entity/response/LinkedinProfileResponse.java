package com.hiretalent.hiretalent.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedinProfileResponse {
    @JsonProperty("localizedLastName")
    private String localizedLastName;

    @JsonProperty("localizedFirstName")
    private String localizedFirstName;

}
