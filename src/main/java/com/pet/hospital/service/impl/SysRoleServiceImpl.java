package com.pet.hospital.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pet.hospital.common.PageResult;
import com.pet.hospital.mapper.SysRoleMapper;
import com.pet.hospital.model.entity.SysRole;
import com.pet.hospital.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageResult<SysRole> listRoles(Integer pageNum, Integer pageSize, String roleName) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> list = sysRoleMapper.listRoles(roleName);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void addRole(SysRole role) {
        sysRoleMapper.insert(role);
    }

    @Override
    public void updateRole(SysRole role) {
        sysRoleMapper.update(role);
    }

    @Override
    public void deleteRole(Long id) {
        sysRoleMapper.deleteById(id);
    }
}
