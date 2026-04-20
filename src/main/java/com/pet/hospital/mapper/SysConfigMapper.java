package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysConfigMapper {
    List<SysConfig> listConfig();
    SysConfig getConfigByKey(@Param("configKey") String configKey);
    int insert(SysConfig config);
    int update(SysConfig config);
    int deleteById(@Param("id") Long id);
}
