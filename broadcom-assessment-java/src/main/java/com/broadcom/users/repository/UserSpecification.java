package com.broadcom.users.repository;

import com.broadcom.users.repository.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<UserEntity> {
    private final SearchCriteria request;

    public UserSpecification(SearchCriteria request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (request.getLastName() != null && !request.getLastName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), request.getLastName().toLowerCase() + "%"));
        }
        if (request.getAge() != null) {
            predicates.add(criteriaBuilder.equal(root.get("age"), request.getAge()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
