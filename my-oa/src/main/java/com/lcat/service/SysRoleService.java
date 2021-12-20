package com.lcat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcat.entity.SysRole;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    /**
     * 获取所有角色信息
     * @param current
     * @param size
     * @return
     */
    List<SysRole> getAllRole(@Param("current") Long current, @Param("size") Long size);
    /**
     * 根据角色id 获取角色
     * @param id
     * @return
     */
    SysRole getRoleById(Long id);

    /**
     * 根据角色名获取角色信息
     * @param name
     * @param current
     * @param size
     * @return
     */
    List<SysRole> getRoleByName(@Param("name") String name, @Param("current") Long current, @Param("size") Long size);

    /**
     * 新增角色
     * @param sysRole
     * @return
     */
    int insertRole(@Param("sysRole") SysRole sysRole);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    int deleteRoleByIds(Long[] ids);
    /**
     * 修改角色
     * @param sysRole
     * @return
     */
    int updateRoleById(SysRole sysRole);

}
