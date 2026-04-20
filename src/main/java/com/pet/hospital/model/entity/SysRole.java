package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

@Data
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String roleName;
    private String roleCode;
    private String description;
    private Date createTime;
    private Date updateTime;
}
