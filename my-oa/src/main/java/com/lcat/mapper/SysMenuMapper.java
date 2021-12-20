package com.lcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lcat.entity.SysMenu;

import java.util.List;


public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取用户菜单列表
     * @param username
     * @return
     */
    List<SysMenu> getUserMenu(String username);

    /**
     * 获取菜单编码
     * @param username
     * @return
     */
    List<String> getUserPerms(String username);

    /**
     * 获取菜单树
     * @param username
     * @return
     */
    List<SysMenu> getMenuTree(String username);

    /**
     * 新增菜单
     * @param sysMenu
     * @return
     */
    int insertMenu(SysMenu sysMenu);

    /**
     * 根据ID获取菜单
     * @param id
     * @return
     */
    SysMenu getMenuById(Long id);

    /**
     * 更新菜单
     * @param sysMenu
     * @return
     */
    int updateMenu(SysMenu sysMenu);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    int deleteMenu(Long id);

}
