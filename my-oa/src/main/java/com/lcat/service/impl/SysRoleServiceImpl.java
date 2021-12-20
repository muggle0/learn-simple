package com.lcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcat.entity.SysRole;
import com.lcat.mapper.SysRoleMapper;
import com.lcat.mapper.SysRoleMenuMapper;
import com.lcat.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired(required = false)
    private SysRoleMapper sysRoleMapper;
    @Autowired(required = false)
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysRole> getAllRole(Long current, Long size) {
        current = (current == null || current <= 0) ? 1 : current;
        size = (size == null || size <= 0) ? 10 : size;
        Long start;
        start = (current-1) * size;
        Long end;
        end = current * size;
        List<SysRole> roleList = sysRoleMapper.getAllRole(start, end);
        for (SysRole role : roleList) {
            List<Long> menuIDs = sysRoleMenuMapper.getMenuIDs(role.getId());
            role.setMenuIds(menuIDs);
        }
        return roleList;
    }

    @Override
    public SysRole getRoleById(Long id) {
        SysRole roles = sysRoleMapper.getRoleById(id);
        List<Long> menuIDs = sysRoleMenuMapper.getMenuIDs(roles.getId());
        roles.setMenuIds(menuIDs);
        return roles;
    }

    @Override
    public List<SysRole> getRoleByName(String name, Long current, Long size) {
        current = (current == null || current <= 0) ? 1 : current;
        size = (size == null || size <= 0) ? 10 : size;
        Long start;
        start = (current-1) * size;
        Long end;
        end = current * size;
        List<SysRole> roleList = sysRoleMapper.getRoleByName(name, start, end);
        for (SysRole role : roleList) {
            List<Long> menuIDs = sysRoleMenuMapper.getMenuIDs(role.getId());
            role.setMenuIds(menuIDs);
        }
        return roleList;
    }

    @Override
    public int insertRole(SysRole sysRole) {
        sysRole.setCreated(LocalDateTime.now());
        sysRole.setUpdated(LocalDateTime.now());
        int row = sysRoleMapper.insertRole(sysRole);
        if (row < 0) {
            log.error("新增角色失败");
        }
        return row;
    }

    //todo
    //删除角色用户表和菜单表也要删掉相应数据
    @Override
    public int deleteRoleByIds(Long[] ids) {
        int row = sysRoleMapper.deleteRoleByIds(ids);
        if (row < 0) {
            log.error("批量删除角色失败");
        }
        return row;
    }

    //todo
    @Override
    public int updateRoleById(SysRole sysRole) {
        sysRole.setUpdated(LocalDateTime.now());
        int row = sysRoleMapper.updateRoleById(sysRole);
        return row;
    }
}
