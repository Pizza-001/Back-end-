package com.pet.hospital.model.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysDept implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String deptName;
    private Integer sortNo;
    private String leader;
    private String phone;
    private String email;
    private Integer status; // 0正常 1停用
    private Date createTime;
    private Date updateTime;
    
    // 子部门，用于返回树形结构
    private List<SysDept> children;
}
