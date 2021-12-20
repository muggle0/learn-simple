package com.lcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcat.entity.SysRoleMenu;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据role_id获取菜单id
     * @param roleId
     * @return
     */
    List<Long> getMenuIDs(Long roleId);

    /**
     * 通过角色ID分配权限
     * @param roleId
     * @return
     */
    int insertOrUpdateMenuId(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 分配权限
     * @param roleId
     * @param menuIds
     * @return
     */
    int changeMenuIds(@Param("roleId") Long roleId, @Param("menuIds") Long[] menuIds);

    /**
     * 删除菜单同时删除角色菜单表
     * @param menuId
     * @return
     */
    int deteleRoleMenuByMenuId(Long menuId);

    /**
     * 删除角色同时删除角色菜单表
     * @param roleId
     * @return
     */
    int deteleRoleMenuByRoleId(Long roleId);
}
