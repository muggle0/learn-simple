package com.lcat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcat.entity.SysRoleMenu;
import io.lettuce.core.dynamic.annotation.Param;

public interface SysRoleMenuService extends IService<SysRoleMenu> {
    /**
     * 通过角色ID分配权限
     * @param roleId
     * @return
     */
    int insertOrUpdateMenuId(@Param("roleId") Long roleId, @Param("menuIds") Long[] menuIds);

    /**
     * 分配权限
     * @param roleId
     * @param menuIds
     * @return
     */
    int changeMenuIds(@Param("roleId") Long roleId, @Param("menuIds") Long[] menuIds);

}
