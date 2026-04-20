package com.pet.hospital.service.impl;

import com.pet.hospital.mapper.SysDeptMapper;
import com.pet.hospital.model.entity.SysDept;
import com.pet.hospital.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> treeDept() {
        List<SysDept> allDepts = sysDeptMapper.listAllDepts();
        return buildTree(allDepts, 0L);
    }

    @Override
    public void addDept(SysDept dept) {
        sysDeptMapper.insert(dept);
    }

    @Override
    public void updateDept(SysDept dept) {
        sysDeptMapper.update(dept);
    }

    @Override
    public void deleteDept(Long id) {
        sysDeptMapper.deleteById(id);
    }

    private List<SysDept> buildTree(List<SysDept> allDepts, Long parentId) {
        List<SysDept> result = new ArrayList<>();
        for (SysDept dept : allDepts) {
            Long deptParentId = dept.getParentId() == null ? 0L : dept.getParentId();
            if (deptParentId.equals(parentId)) {
                dept.setChildren(buildTree(allDepts, dept.getId()));
                result.add(dept);
            }
        }
        return result;
    }
}
