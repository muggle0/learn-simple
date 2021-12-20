package com.lcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcat.entity.SysUserRole;
import com.lcat.mapper.SysUserRoleMapper;
import com.lcat.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Autowired(required = false)
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int updateRoleByUserId(Long userId, Long[] roleIds) {
        int row = 0;
        for (Long roleId : roleIds) {
            int i = sysUserRoleMapper.updateRoleByUserId(userId, roleId);
            row += i;
        }
        return row;
    }
}
