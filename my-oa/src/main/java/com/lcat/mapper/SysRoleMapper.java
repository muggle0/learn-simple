package com.lcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcat.entity.SysRole;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户名获取角色code
     * @param username
     * @return
     */
    List<String> getRoleCode(String username);
    /**
     * 根据用户名获取角色信息
     * @param username
     * @return
     */
    List<SysRole> getRoleInfo(String username);

    /**
     * 根据用户ID查询角色信息
     * @param id
     * @return
     */
    List<SysRole> getRoleByUserId(Long id);

    /**
     * 根据角色id 获取角色
     * @param id
     * @return
     */
    SysRole getRoleById(Long id);

    /**
     * 获取所有角色信息
     * @param start
     * @param end
     * @return
     */
    List<SysRole> getAllRole(@Param("start") Long start, @Param("end") Long end);

    /**
     * 根据角色名称获取角色信息
     * @param name
     * @param start
     * @param end
     * @return
     */
    List<SysRole> getRoleByName(@Param("name") String name, @Param("start") Long start, @Param("end") Long end);

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
