package com.hiretalent.hiretalent.entity.elastic;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class HrUserModel {

    @Id
    private Long id;
    private String name;
    private String surname;
    private String username;

}
