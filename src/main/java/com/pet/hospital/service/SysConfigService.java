package com.pet.hospital.service;

import com.pet.hospital.common.PageResult;
import com.pet.hospital.model.entity.SysConfig;

public interface SysConfigService {
    PageResult<SysConfig> listConfig(Integer pageNum, Integer pageSize);
    String getConfigByKey(String configKey);
    void addConfig(SysConfig config);
    void updateConfig(SysConfig config);
    void deleteConfig(Long id);
}
