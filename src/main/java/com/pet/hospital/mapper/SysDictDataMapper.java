package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysDictDataMapper {
    List<SysDictData> listDictData(@Param("dictType") String dictType);
    int insert(SysDictData dictData);
    int update(SysDictData dictData);
    int deleteById(@Param("id") Long id);
}
