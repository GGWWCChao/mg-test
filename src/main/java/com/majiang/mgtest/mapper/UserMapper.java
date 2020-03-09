package com.majiang.mgtest.mapper;

import com.majiang.mgtest.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
  void insert(User user);

  User findByToken(@Param("token") String token);
}
