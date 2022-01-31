package com.broadcom.users.controller;

import com.broadcom.users.model.UsersResponse;
import com.broadcom.users.repository.SearchCriteria;
import com.broadcom.users.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService service;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsersResponse getUsers(
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "currPageIndex", required = false) Integer currPageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "nameSort", required = false) String nameSort,
            @RequestParam(value = "ageSort", required = false) String ageSort) {
        logger.info("Got the request");
        return service.getUsers(SearchCriteria.builder()
                .lastName(lastName)
                .age(age)
                .pageNumber(currPageIndex)
                .numOfElements(pageSize)
                .nameSort(nameSort)
                .ageSort(ageSort)
                .build());
    }
}
