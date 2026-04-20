package com.pet.hospital.service;

import com.pet.hospital.model.entity.SysDept;
import java.util.List;

public interface SysDeptService {
    List<SysDept> treeDept();
    void addDept(SysDept dept);
    void updateDept(SysDept dept);
    void deleteDept(Long id);
}
