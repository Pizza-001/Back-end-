package com.pet.hospital.service;

import com.pet.hospital.model.entity.SysMenu;
import java.util.List;

public interface SysMenuService {
    List<SysMenu> treeMenu();
    void addMenu(SysMenu menu);
    void updateMenu(SysMenu menu);
    void deleteMenu(Long id);
}
