package com.pet.hospital.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pet.hospital.common.PageResult;
import com.pet.hospital.mapper.SysDictTypeMapper;
import com.pet.hospital.model.entity.SysDictType;
import com.pet.hospital.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Override
    public PageResult<SysDictType> listDictTypes(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysDictType> list = sysDictTypeMapper.listDictTypes();
        PageInfo<SysDictType> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void addDictType(SysDictType dictType) {
        sysDictTypeMapper.insert(dictType);
    }

    @Override
    public void updateDictType(SysDictType dictType) {
        sysDictTypeMapper.update(dictType);
    }

    @Override
    public void deleteDictType(Long id) {
        sysDictTypeMapper.deleteById(id);
    }
}
