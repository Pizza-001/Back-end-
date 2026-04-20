package com.pet.hospital.service;

import com.pet.hospital.common.PageResult;
import com.pet.hospital.model.entity.SysRole;

public interface SysRoleService {
    PageResult<SysRole> listRoles(Integer pageNum, Integer pageSize, String roleName);
    void addRole(SysRole role);
    void updateRole(SysRole role);
    void deleteRole(Long id);
}
