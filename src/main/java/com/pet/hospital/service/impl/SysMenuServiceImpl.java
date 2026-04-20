package com.pet.hospital.service.impl;

import com.pet.hospital.mapper.SysMenuMapper;
import com.pet.hospital.model.entity.SysMenu;
import com.pet.hospital.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> treeMenu() {
        List<SysMenu> allMenus = sysMenuMapper.listAllMenus();
        return buildTree(allMenus, 0L);
    }

    @Override
    public void addMenu(SysMenu menu) {
        sysMenuMapper.insert(menu);
    }

    @Override
    public void updateMenu(SysMenu menu) {
        sysMenuMapper.update(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        sysMenuMapper.deleteById(id);
    }

    private List<SysMenu> buildTree(List<SysMenu> allMenus, Long parentId) {
        List<SysMenu> result = new ArrayList<>();
        for (SysMenu menu : allMenus) {
            Long menuParentId = menu.getParentId() == null ? 0L : menu.getParentId();
            if (menuParentId.equals(parentId)) {
                menu.setChildren(buildTree(allMenus, menu.getId()));
                result.add(menu);
            }
        }
        return result;
    }
}
