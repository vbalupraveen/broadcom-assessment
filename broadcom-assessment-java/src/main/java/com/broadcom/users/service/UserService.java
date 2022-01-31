package com.broadcom.users.service;

import com.broadcom.users.model.User;
import com.broadcom.users.model.UsersResponse;
import com.broadcom.users.repository.SearchCriteria;
import com.broadcom.users.repository.UserRepository;
import com.broadcom.users.repository.UserSpecification;
import com.broadcom.users.repository.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UsersResponse getUsers(SearchCriteria sc) {
        logger.info("request received:{}", sc);
        UsersResponse response = new UsersResponse();
        Sort sort = null;
        if (!StringUtils.isEmpty(sc.getNameSort())) {
            if (sc.getNameSort().equalsIgnoreCase("asc"))
                sort = Sort.by("firstName").ascending().and(Sort.by("lastName").ascending());
            else if (sc.getNameSort().equalsIgnoreCase("desc"))
                sort = Sort.by("firstName").descending().and(Sort.by("lastName").descending());
        }
        if (!StringUtils.isEmpty(sc.getAgeSort())) {
            if (sc.getAgeSort().equalsIgnoreCase("asc"))
                sort = Sort.by("age").ascending();
            else if (sc.getAgeSort().equalsIgnoreCase("desc"))
                sort = Sort.by("age").descending();
        }

        Pageable pageable;
        UserSpecification specification = new UserSpecification(sc);
        Page<UserEntity> page;
        if (sort != null) {
            pageable = PageRequest.of(sc.getPageNumber(), sc.getNumOfElements(), sort);
        } else {
            pageable = PageRequest.of(sc.getPageNumber(), sc.getNumOfElements());
        }
        page = repository.findAll(specification, pageable);
        logger.info("number of records returned:{}", page.getTotalElements());

        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : page.toList()) {
            users.add(UserMapper.INSTANCE.convert(userEntity));
        }
        response.setUsers(users);
        response.setTotal(page.getTotalElements());
        return response;
    }

}
