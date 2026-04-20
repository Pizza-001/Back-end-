package com.pet.hospital.service.impl;

import com.pet.hospital.mapper.BizVaccineRecordMapper;
import com.pet.hospital.model.entity.BizVaccineRecord;
import com.pet.hospital.service.BizVaccineRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BizVaccineRecordServiceImpl implements BizVaccineRecordService {

    @Autowired
    private BizVaccineRecordMapper vaccineRecordMapper;

    @Override
    public com.github.pagehelper.PageInfo<BizVaccineRecord> getPage(int pageNum, int pageSize) {
        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
        List<BizVaccineRecord> list = vaccineRecordMapper.findAll();
        return new com.github.pagehelper.PageInfo<>(list);
    }

    @Override
    public BizVaccineRecord getById(Long id) {
        return vaccineRecordMapper.findById(id);
    }

    @Override
    public void addRecord(BizVaccineRecord record) {
        vaccineRecordMapper.insert(record);
    }

    @Override
    public void updateRecord(BizVaccineRecord record) {
        vaccineRecordMapper.update(record);
    }

    @Override
    public void deleteRecord(Long id) {
        vaccineRecordMapper.deleteById(id);
    }
}
