package com.pet.hospital.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pet.hospital.common.PageResult;
import com.pet.hospital.mapper.SysDictDataMapper;
import com.pet.hospital.model.entity.SysDictData;
import com.pet.hospital.service.SysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public PageResult<SysDictData> listDictData(Integer pageNum, Integer pageSize, String dictType) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysDictData> list = sysDictDataMapper.listDictData(dictType);
        PageInfo<SysDictData> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<SysDictData> getDictsByDictType(String dictType) {
        return sysDictDataMapper.listDictData(dictType);
    }

    @Override
    public void addDictData(SysDictData dictData) {
        sysDictDataMapper.insert(dictData);
    }

    @Override
    public void updateDictData(SysDictData dictData) {
        sysDictDataMapper.update(dictData);
    }

    @Override
    public void deleteDictData(Long id) {
        sysDictDataMapper.deleteById(id);
    }
}
