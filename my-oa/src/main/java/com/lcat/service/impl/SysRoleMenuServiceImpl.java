package com.lcat.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcat.entity.SysRoleMenu;
import com.lcat.mapper.SysRoleMenuMapper;
import com.lcat.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired(required = false)
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int insertOrUpdateMenuId(Long roleId, Long[] menuIds) {
        int row = 0;
        for (Long menuId : menuIds) {
            int i = sysRoleMenuMapper.insertOrUpdateMenuId(roleId, menuId);
            if (i > 0) {
                row++;
            }
        }
        if (row < 0) {
            log.error("分配权限失败");
        }
        return row;
    }

    @Override
    public int changeMenuIds(Long roleId, Long[] menuIds) {
        int row = sysRoleMenuMapper.changeMenuIds(roleId, menuIds);
        if (row < 0) {
            log.error("分配权限失败");
        }
        return row;
    }
}
