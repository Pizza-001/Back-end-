package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.BizDoctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface BizDoctorMapper {
    List<BizDoctor> findByDepartment(@Param("department") String department);
    BizDoctor findById(@Param("id") Long id);
    
    // ----------- 追加数据管理 (CRUD) 接口 -----------
    int insert(BizDoctor doctor);
    int update(BizDoctor doctor);
    int deleteById(@Param("id") Long id);
}
