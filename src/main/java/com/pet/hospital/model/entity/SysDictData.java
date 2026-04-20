package com.pet.hospital.model.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysDictData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer sortNo;
    private String dictLabel;
    private String dictValue;
    private String dictType;
    private String listClass;
    private Integer isDefault;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;
}
