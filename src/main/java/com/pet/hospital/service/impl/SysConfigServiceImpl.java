package com.pet.hospital.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pet.hospital.common.PageResult;
import com.pet.hospital.mapper.SysConfigMapper;
import com.pet.hospital.model.entity.SysConfig;
import com.pet.hospital.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public PageResult<SysConfig> listConfig(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysConfig> list = sysConfigMapper.listConfig();
        PageInfo<SysConfig> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public String getConfigByKey(String configKey) {
        SysConfig config = sysConfigMapper.getConfigByKey(configKey);
        return config != null ? config.getConfigValue() : null;
    }

    @Override
    public void addConfig(SysConfig config) {
        sysConfigMapper.insert(config);
    }

    @Override
    public void updateConfig(SysConfig config) {
        sysConfigMapper.update(config);
    }

    @Override
    public void deleteConfig(Long id) {
        sysConfigMapper.deleteById(id);
    }
}
