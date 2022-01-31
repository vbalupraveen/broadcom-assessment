package com.broadcom.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
}
