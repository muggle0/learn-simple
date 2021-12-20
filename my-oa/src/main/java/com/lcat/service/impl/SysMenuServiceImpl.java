package com.lcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lcat.entity.SysMenu;
import com.lcat.entity.SysUser;
import com.lcat.entity.dto.SysMenuDTO;
import com.lcat.entity.vo.SysUserVO;
import com.lcat.mapper.SysMenuMapper;
import com.lcat.mapper.SysRoleMapper;
import com.lcat.mapper.SysUserMapper;
import com.lcat.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired(required = false)
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired(required = false)
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysUserVO getUserMenu(String username) {
        List<SysMenu> userMenuList = sysMenuMapper.getUserMenu(username);
        //获取用户权限
        /*List<String> userPerms = sysMenuMapper.getUserPerms(username);
        List<String> authoritys =new ArrayList<>();
        for (String perms : userPerms) {
            authoritys.add(perms);
        }*/
        List<String> userPerms = sysMenuMapper.getUserPerms(username);
        List<String> authoritys = getRoleCode(username);
        for (String perms : userPerms) {
            authoritys.add(perms);
        }

        log.info("用户权限：{}", authoritys);
        //获取菜单树
        List<SysMenuDTO> parent = getParent(userMenuList);
        for (int i = 0; i < parent.size(); i++) {
            parent.get(i).setChildren(getChildren(parent.get(i).getId(), userMenuList));
        }
        log.info("菜单列表：{}", parent);
        SysUser userInfo = sysUserMapper.getUserInfo(username);
        SysUserVO userVO = new SysUserVO();
        userVO.setAuthoritys(authoritys);
        userVO.setNav(parent);
        userVO.setId(userInfo.getId());
        userVO.setUsername(username);
        userVO.setAvatar(userInfo.getAvatar());
        userVO.setCreated(LocalDateTime.now());
        return  userVO;
    }

    /**
     * 菜单管理--获取菜单树
     * @param username
     * @return
     */
    @Override
    public List<SysMenu> getMenuTree(String username) {
        List<SysMenu> menuTree = sysMenuMapper.getMenuTree(username);
        //获取菜单树
        List<SysMenu> parentMenuList = getParentMenu(menuTree);
        for (int i = 0; i < parentMenuList.size(); i++) {
            parentMenuList.get(i).setChildren(getChildrenMenu(parentMenuList.get(i).getId(), menuTree));
        }
        log.info("菜单树：{}", parentMenuList);
        return parentMenuList;
    }

    @Override
    public int insertMenu(SysMenu sysMenu) {
        sysMenu.setCreated(LocalDateTime.now());
        sysMenu.setUpdated(LocalDateTime.now());
        int row = sysMenuMapper.insertMenu(sysMenu);
        if (row < 0) {
            log.error("新增失败");
        }
        log.info("新增成功");
        return row;
    }

    @Override
    public SysMenu getMenuById(Long id) {
        SysMenu menuById = sysMenuMapper.getMenuById(id);
        if (menuById == null) {
            log.error("菜单信息为空");
        }
        return menuById;
    }

    @Override
    public int updateMenu(SysMenu sysMenu) {
        sysMenu.setCreated(LocalDateTime.now());
        sysMenu.setUpdated(LocalDateTime.now());
        int row = sysMenuMapper.updateMenu(sysMenu);
        if (row < 0) {
            log.error("修改菜单信息失败");
        }
        return row;
    }

    private List<String> getRoleCode(String username) {
        List<String> roleList = sysRoleMapper.getRoleCode(username);
        List<String> list = roleList.stream().map(r -> "ROLE_" + r).collect(Collectors.toList());
        return list;
    }

    /**
     * 获取父级菜单--用户管理
     * @param menus
     * @return
     */
    private List<SysMenuDTO> getParent(List<SysMenu> menus) {
        List<SysMenu> rootMenuList = getParentMenu(menus);
        List<SysMenuDTO> rootMenuDTOList = rootMenuList.stream().map(rootMenu -> {
            SysMenuDTO sysMenuDTO = new SysMenuDTO();
            BeanUtils.copyProperties(rootMenu, sysMenuDTO);
            sysMenuDTO.setTitle(rootMenu.getName());
            sysMenuDTO.setName(rootMenu.getPerms());
            return sysMenuDTO;
        }).collect(Collectors.toList());
        return rootMenuDTOList;
    }

    /**
     * 获取父级菜单--菜单管理
     * @param menus
     * @return
     */
    private List<SysMenu> getParentMenu(List<SysMenu> menus) {
        List<SysMenu> rootMenu = new ArrayList<>();
        for (SysMenu sysMenu : menus) {
            if (sysMenu.getParentId() == 0) {
                rootMenu.add(sysMenu);
            }
        }
        return rootMenu;
    }

    /**
     * 获取子菜单--用户管理
     * @param id
     * @param rootMenu
     * @return
     */
    private List<SysMenuDTO> getChildren(Long id, List<SysMenu> rootMenu) {
        List<SysMenu> childList = getChildrenMenu(id, rootMenu);
        List<SysMenuDTO> menuDTOList = childList.stream().map(child -> {
            SysMenuDTO sysMenuDTO = new SysMenuDTO();
            BeanUtils.copyProperties(child, sysMenuDTO);
            sysMenuDTO.setTitle(child.getName());
            sysMenuDTO.setName(child.getPerms());
            return sysMenuDTO;
        }).collect(Collectors.toList());
        return menuDTOList;
    }

    /**
     * 菜单管理--获取子菜单
     * @param id
     * @param rootMenu
     * @return
     */
    private List<SysMenu> getChildrenMenu(Long id, List<SysMenu> rootMenu) {
        List<SysMenu> childList = new ArrayList<>();
        for (SysMenu menu : rootMenu) {
            //遍历获取子菜单
            if (StringUtils.isNotBlank(menu.getParentId().toString())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                    menu.setChildren(getChildrenMenu(menu.getId(), rootMenu));
                }
            }
        }
        List<SysMenu> sysMenuList = new ArrayList<>();
        //获取子菜单的子菜单
        for (SysMenu menu : childList) {
            if (StringUtils.isBlank(menu.getPath())) {
                menu.setChildren(getChildrenMenu(menu.getId(), rootMenu));
            }
        }
        sysMenuList.addAll(childList);
        return sysMenuList;
    }
}
