package com.lcat.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcat.entity.SysUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    SysUser loadUserByUsername(String username);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    SysUser getUserInfo(String username);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @param start
     * @param end
     * @return
     */
    List<SysUser> getUserInfoByName(@Param("username") String username, @Param("start") Long start, @Param("end") Long end);

    /**
     * 获取所有用户信息
     * @param start
     * @param end
     * @return
     */
    List<SysUser> getAllUserInfo(@Param("start") Long start, @Param("end") Long end);

    /**
     * 通过ID获取用户信息
     * @param id
     * @return
     */
    SysUser getUserInfoById(Long id);
    /**
     * 修改用户信息
     * @param sysUser
     * @return
     */
    int updateUserInfoById(SysUser sysUser);

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    int insertUserInfo(SysUser sysUser);

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 重置密码
     * @param password
     * @param id
     * @return
     */
    int repass(@Param("password") String password, @Param("id") Long id);

    /**
     * 修改密码
     * @param sysUser
     * @param username
     * @param password
     * @return
     */
    int updatePass(@Param("sysUser") SysUser sysUser, @Param("username") String username, @Param("password") String password);

}
