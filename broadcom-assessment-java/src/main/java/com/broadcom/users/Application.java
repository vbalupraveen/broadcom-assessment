package com.broadcom.users;

import com.broadcom.users.repository.UserRepository;
import com.broadcom.users.repository.entity.AddressEntity;
import com.broadcom.users.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadData(UserRepository repository) {
        log.debug("Saving Users");
        return (args) -> {
            List<UserEntity> userEntities = new ArrayList<>();
            Random random = new Random();
            int limit = 1000000;
            for (int i = 0; i < limit; i++) {
                UserEntity userEntity = UserEntity.builder()
                        .firstName("User")
                        .lastName(String.valueOf(random.nextInt(50)))
                        .age((i % 50) + 10)
                        .build();

                AddressEntity adr = AddressEntity.builder()
                        .street("Street")
                        .city("City")
                        .state("State")
                        .country("CA")
                        .zipcode("69740")
                        .build();

//                userEntity.setAddressEntity1(adr);
                userEntities.add(userEntity);
            }
            repository.saveAll(userEntities);
            log.debug("number of records inserted:{}", userEntities.size());
        };
    }


}
