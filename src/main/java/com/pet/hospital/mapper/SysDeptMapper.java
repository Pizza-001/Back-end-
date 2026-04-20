package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysDeptMapper {
    List<SysDept> listAllDepts();
    int insert(SysDept dept);
    int update(SysDept dept);
    int deleteById(@Param("id") Long id);
}
