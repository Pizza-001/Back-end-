package com.pet.hospital.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.pet.hospital.model.entity.BizVaccineRecord;
import java.util.List;

@Mapper
public interface BizVaccineRecordMapper {
    List<BizVaccineRecord> findAll();
    int insert(BizVaccineRecord record);
    int update(BizVaccineRecord record);
    int deleteById(Long id);
    BizVaccineRecord findById(Long id);
}
