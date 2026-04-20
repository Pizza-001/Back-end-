package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.BizSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 医生排班 Mapper
 */
@Mapper
public interface BizScheduleMapper {
    List<BizSchedule> findByDoctorId(@Param("doctorId") Long doctorId);
    BizSchedule findById(@Param("id") Long id);
    int decreaseRemainCapacity(@Param("id") Long id);
    
    // ----------- 补充：查档期接口 -----------
    List<BizSchedule> findAvailableByDoctorId(@Param("doctorId") Long doctorId);
}
