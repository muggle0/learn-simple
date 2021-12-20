package com.lcat.service;

import com.lcat.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lcat.entity.dto.PassDTO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface SysUserService extends IService<SysUser> {
    /**
     * 通过用户名称获取用户信息
     * @param username
     * @return
     */
    SysUser getUserInfo(String username);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @param current
     * @param size
     * @return
     */
    List<SysUser> getUserInfoByName(@Param("username") String username, @Param("current") Long current, @Param("size") Long size);

    /**
     * 获取所有用户信息
     * @param current
     * @param size
     * @return
     */
    List<SysUser> getAllUserInfo(@Param("current") Long current, @Param("size") Long size);

    /**
     * 通过ID获取用户信息
     * @param id
     * @return
     */
    SysUser getUserInfoById(Long id);

    /**
     * 通过ID更新用户信息
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
     * 根据ID批量删除用户
     * @param ids
     * @return
     */
    int deleteUserByIds(Long[] ids);

    /**
     * 重置密码
     * @param userId
     * @return
     */
    int repass(Long userId);

    /**
     * 修改密码
     * @param passDTO
     * @return
     */
    int updatePass(@Param("passDTO") PassDTO passDTO);

    /**
     * 获取权限
     * @param username
     * @return
     */
    String getAuthoritys(String username);
}
