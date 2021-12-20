package com.lcat.service;

import com.lcat.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import io.lettuce.core.dynamic.annotation.Param;

public interface SysUserRoleService extends IService<SysUserRole> {
    /**
     * 通过用户ID更新角色
     * @param userId
     * @param roleIds
     * @return
     */
    int updateRoleByUserId(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds);

}
