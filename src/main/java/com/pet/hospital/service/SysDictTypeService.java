package com.pet.hospital.service;

import com.pet.hospital.common.PageResult;
import com.pet.hospital.model.entity.SysDictType;

public interface SysDictTypeService {
    PageResult<SysDictType> listDictTypes(Integer pageNum, Integer pageSize);
    void addDictType(SysDictType dictType);
    void updateDictType(SysDictType dictType);
    void deleteDictType(Long id);
}
