package com.broadcom.users.service;

import com.broadcom.users.model.UsersResponse;
import com.broadcom.users.repository.SearchCriteria;
import com.broadcom.users.repository.UserRepository;
import com.broadcom.users.repository.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserServiceTest {
    @Autowired
    UserRepository repository;
    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(repository);
        UserEntity u1 = UserEntity.builder().firstName("John").lastName("Doe").age(20).build();
        UserEntity u2 = UserEntity.builder().firstName("Sam").lastName("Smith").age(25).build();
        UserEntity u3 = UserEntity.builder().firstName("Matt").lastName("Smith").age(27).build();
        repository.saveAll(Arrays.asList(u1, u2, u3));
    }

    @Test
    void testSave() {
        SearchCriteria sc = SearchCriteria.builder().lastName("Smith").build();
        UsersResponse users = userService.getUsers(sc);
        assertThat(users.getTotal()).isEqualTo(2);

        sc = SearchCriteria.builder().lastName("Smith").age(25).build();
        users = userService.getUsers(sc);
        assertThat(users.getTotal()).isEqualTo(1);
        assertThat(users.getUsers().get(0).getFirstName()).isEqualTo("Sam");

        sc = SearchCriteria.builder().lastName("Smith").pageNumber(0).numOfElements(1).build();
        users = userService.getUsers(sc);
        assertThat(users.getUsers().size()).isEqualTo(1);
    }
}
