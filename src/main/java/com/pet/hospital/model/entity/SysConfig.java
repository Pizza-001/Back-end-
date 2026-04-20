package com.pet.hospital.model.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String configName;
    private String configKey;
    private String configValue;
    private Integer configType;
    private String remark;
    private Date createTime;
    private Date updateTime;
}
