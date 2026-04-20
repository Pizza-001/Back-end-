package com.pet.hospital.service;

import com.pet.hospital.common.PageResult;
import com.pet.hospital.model.entity.SysDictData;
import java.util.List;

public interface SysDictDataService {
    PageResult<SysDictData> listDictData(Integer pageNum, Integer pageSize, String dictType);
    List<SysDictData> getDictsByDictType(String dictType);
    void addDictData(SysDictData dictData);
    void updateDictData(SysDictData dictData);
    void deleteDictData(Long id);
}
