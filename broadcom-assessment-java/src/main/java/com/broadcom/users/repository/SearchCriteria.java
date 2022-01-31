package com.broadcom.users.repository;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchCriteria {
    String lastName;
    Integer age;
    @Builder.Default
    Integer pageNumber = 0;
    @Builder.Default
    Integer numOfElements = 100;
    String nameSort;
    String ageSort;
}
