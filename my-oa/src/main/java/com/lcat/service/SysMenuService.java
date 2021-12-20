package com.lcat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lcat.entity.SysMenu;
import com.lcat.entity.vo.SysUserVO;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户菜单列表
     * @param username
     * @return
     */
    SysUserVO getUserMenu(String username);
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
}
