package com.hiretalent.hiretalent.entity.elastic;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.hiretalent.hiretalent.entity.JobApplicationStatus;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(indexName = "job_application")
public class JobApplicationModel {
	@Id
    private Long id;
	private Long jobPostingId;

    @Field(type = FieldType.Object)
    private ApplicantModel applicant;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime dateCreated;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime dateEvaluated;

    @Field(type = FieldType.Object)
    private HrUserModel evaluatedBy;

    @Field(type = FieldType.Keyword)
    private JobApplicationStatus status;
}
