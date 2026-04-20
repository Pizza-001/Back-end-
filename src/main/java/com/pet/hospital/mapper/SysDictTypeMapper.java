package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.SysDictType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysDictTypeMapper {
    List<SysDictType> listDictTypes();
    int insert(SysDictType dictType);
    int update(SysDictType dictType);
    int deleteById(@Param("id") Long id);
}
