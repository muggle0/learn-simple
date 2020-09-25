package com.muggle.learn.mapper;

import com.github.pagehelper.Page;
import com.muggle.learn.domain.UserAuthority;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserAuthority record);

    int insertSelective(UserAuthority record);

    UserAuthority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAuthority record);

    int updateByPrimaryKey(UserAuthority record);

    Page<UserAuthority> find();

}