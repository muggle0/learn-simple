package com.lcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcat.entity.SysUserRole;
import io.lettuce.core.dynamic.annotation.Param;


public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 根据用户ID更新角色
     * @param userId
     * @param roleId
     * @return
     */
    int updateRoleByUserId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 删除用户同时删除用户角色表
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 删除角色同时删除用户角色表
     * @param roleId
     * @return
     */
    int deleteUserRoleByRoleId(Long roleId);
}
