package com.lcat.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcat.common.exception.BusinesException;
import com.lcat.common.lang.ErrorCode;
import com.lcat.entity.SysRole;
import com.lcat.entity.SysUser;
import com.lcat.entity.dto.PassDTO;
import com.lcat.mapper.SysMenuMapper;
import com.lcat.mapper.SysRoleMapper;
import com.lcat.mapper.SysRoleMenuMapper;
import com.lcat.mapper.SysUserMapper;
import com.lcat.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService, UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired(required = false)
    private SysRoleMapper sysRoleMapper;
    @Autowired(required = false)
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired(required = false)
    private SysMenuMapper sysMenuMapper;
    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserMapper baseMapper = this.baseMapper;
        SysUser sysUser=baseMapper.loadUserByUsername(username);
        final String content = getAuthoritys(username);
        log.info("{}登录, 权限为：{}", username, content);
        sysUser.setRoleCodes(content);
        return sysUser;
    }

    @Override
    public String getAuthoritys(String username) {
        List<String> userPerms = sysMenuMapper.getUserPerms(username);
        List<String> authoritys =new ArrayList<>();
        for (String perms : userPerms) {
            authoritys.add(perms);
        }
        final List<String> roleCode = sysRoleMapper.getRoleCode(username);
        final String content = String.join(",", authoritys).concat(",").concat(roleCode.stream()
            .map(r -> "ROLE_".concat(r)).collect(Collectors.joining(",")));
        return content;
    }

    @Override
    public SysUser getUserInfo(String username) {
        if (username == null || username == "") {
            log.info("用户不存在，登录失效");
        }
        SysUser userInfo = sysUserMapper.getUserInfo(username);
        return userInfo;
    }

    @Override
    public List<SysUser> getUserInfoByName(String username, Long current, Long size) {
        current = (current == null || current <= 0) ? 1 : current;
        size = (size == null || size <= 0) ? 10 : size;
        Long start;
        start = (current-1) * size;
        Long end;
        end = current * size;
        List<SysUser> userInfoList = sysUserMapper.getUserInfoByName(username, start, end);
        for (SysUser user : userInfoList) {
            List<SysRole> roleInfoList = sysRoleMapper.getRoleInfo(user.getUsername());
            for (SysRole role : roleInfoList) {
                List<Long> menuIDs = sysRoleMenuMapper.getMenuIDs(role.getId());
                role.setMenuIds(menuIDs);
            }
            user.setSysRoles(roleInfoList);
        }
        log.info("用户信息: {}", userInfoList);
        return userInfoList;
    }

    @Override
    public List<SysUser> getAllUserInfo(Long current, Long size) {
        current = (current == null || current <= 0) ? 1 : current;
        size = (size == null || size <= 0) ? 10 : size;
        Long start;
        start = (current-1) * size;
        Long end;
        end = current * size;
        List<SysUser> userInfoList = sysUserMapper.getAllUserInfo(start, end);
        for (SysUser user : userInfoList) {
            List<SysRole> roleInfoList = sysRoleMapper.getRoleInfo(user.getUsername());
            for (SysRole role : roleInfoList) {
                List<Long> menuIDs = sysRoleMenuMapper.getMenuIDs(role.getId());
                role.setMenuIds(menuIDs);
            }
            user.setSysRoles(roleInfoList);
        }
        log.info("用户信息: {}", userInfoList);
        return userInfoList;
    }

    @Override
    public SysUser getUserInfoById(Long id) {
        SysUser userInfoById = sysUserMapper.getUserInfoById(id);
        List<SysRole> roleList = sysRoleMapper.getRoleByUserId(id);
        for (SysRole role : roleList) {
            List<Long> menuIDs = sysRoleMenuMapper.getMenuIDs(role.getId());
            role.setMenuIds(menuIDs);
        }
        userInfoById.setSysRoles(roleList);
        if (userInfoById == null) {
            log.error("用户信息为空");
        }
        log.info("需要分配角色的用户信息: {}", userInfoById);
        return userInfoById;
    }

    @Override
    public int updateUserInfoById(SysUser sysUser) {
        sysUser.setUpdated(LocalDateTime.now());
        int row = sysUserMapper.updateUserInfoById(sysUser);
        return row;
    }

    @Override
    public int insertUserInfo(SysUser sysUser) {
        sysUser.setCreated(LocalDateTime.now());
        sysUser.setUpdated(LocalDateTime.now());
        sysUser.setEnabled(true);
        sysUser.setPassword(passwordEncoder.encode("888888"));
        sysUser.setAvatar("https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg");
        int row = sysUserMapper.insertUserInfo(sysUser);
        if (row < 0) {
            throw new BusinesException("新增失败", ErrorCode.USER_ERR0R.getCode());
        }
        return row;
    }

    @Override
    public int deleteUserByIds(Long[] ids) {
        int row = sysUserMapper.deleteUserByIds(ids);
        return row;
    }

    @Override
    public int repass(Long userId) {
        String password = "888888";
        String currentPass = passwordEncoder.encode(password);
        log.info("重置密码为：{}",currentPass);
        int row = sysUserMapper.repass(currentPass, userId);
        return row;
    }

    //todo
    @Override
    public int updatePass(PassDTO passDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        SysUser sysUser = sysUserMapper.loadUserByUsername(username);
        if (sysUser.getUsername().isEmpty()) {
            log.error("用户登录失效");
        }
        log.info("用户密码:{}",sysUser.getPassword());
        String newPassEncode = passwordEncoder.encode(passDTO.getPassword());
        if (!passwordEncoder.matches(passDTO.getCurrentPass(), sysUser.getPassword())) {
            throw new BusinesException("旧密码与用户密码不一致",ErrorCode.USER_ERR0R.getCode());
        }
        int row = sysUserMapper.updatePass(sysUser, username, newPassEncode);
        if (row < 0) {
            throw new BusinesException("修改密码失败", ErrorCode.USER_ERR0R.getCode());
        }
        log.info("修改密码成功");
        return row;
    }

}
