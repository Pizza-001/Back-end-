package com.pet.hospital.service;

import com.pet.hospital.model.entity.BizVaccineRecord;
import java.util.List;

public interface BizVaccineRecordService {
    com.github.pagehelper.PageInfo<BizVaccineRecord> getPage(int pageNum, int pageSize);
    BizVaccineRecord getById(Long id);
    void addRecord(BizVaccineRecord record);
    void updateRecord(BizVaccineRecord record);
    void deleteRecord(Long id);
}
