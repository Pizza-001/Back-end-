package com.pet.hospital.model.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class SysDictType implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String dictName;
    private String dictType;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;
}
