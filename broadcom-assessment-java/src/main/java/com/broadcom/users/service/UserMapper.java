package com.broadcom.users.service;

import com.broadcom.users.model.User;
import com.broadcom.users.repository.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User convert(UserEntity userEntity);
}
